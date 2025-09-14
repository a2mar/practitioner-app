package com.practitioner.backend.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.practitioner.backend.AbstractIntegrationTest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Validator validator;

    private static final String EMAIL = "test@example.com";
    private static final String UNIQUE_EMAIL = "unique@example.com";
    private static final String FIRST_NAME = "John";

    @Test
    void saveAndFindByEmail() {
    // given
    User user = new User();
    user.setEmail(EMAIL);
    user.setPasswordHash("hashedPassword");
    user.setFirstName(FIRST_NAME);
    user.setLastName("Doe");
    user.setRole(Role.CLIENT);

    userRepository.save(user);

    // when
    Optional<User> found = userRepository.findByEmail(EMAIL);

    // then
    assertThat(found).isPresent();
    assertThat(found.get().getFirstName()).isEqualTo(FIRST_NAME);

    // round-trip through the whole entity:
    assertThat(found.get())
    .usingRecursiveComparison()
    .ignoringFields("id")
    .isEqualTo(user);
    }

    @Test
    void savingTwoUsersWithSameEmailFails() {
        User user1 = new User();
        user1.setEmail(UNIQUE_EMAIL);
        user1.setPasswordHash("hash1");
        user1.setFirstName("Alice");
        user1.setLastName("Smith");
        user1.setRole(Role.CLIENT);

        User user2 = new User();
        user2.setEmail(UNIQUE_EMAIL); // same email
        user2.setPasswordHash("hash2");
        user2.setFirstName("Bob");
        user2.setLastName("Brown");
        user2.setRole(Role.CLIENT);

        userRepository.saveAndFlush(user1);

        assertThatThrownBy(() -> userRepository.saveAndFlush(user2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void blankFirstNameShouldFailValidation() {
        User user = new User();
        user.setEmail("blank@example.com");
        user.setPasswordHash("hash");
        user.setFirstName(""); // blank!
        user.setLastName("Doe");
        user.setRole(Role.CLIENT);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations)
                .extracting(v -> v.getPropertyPath().toString())
                .contains("firstName");
    }

    @Test
    void roleShouldBePersistedAndRetrieved() {
        User user = new User();
        user.setEmail("rolecheck@example.com");
        user.setPasswordHash("hash");
        user.setFirstName("Max");
        user.setLastName("Mustermann");
        user.setRole(Role.ADMIN);

        userRepository.saveAndFlush(user);

        User found = userRepository.findByEmail("rolecheck@example.com").orElseThrow();
        assertThat(found.getRole()).isEqualTo(Role.ADMIN);
    }

    @Test
    void invalidEmailShouldFailValidation() {
        User user = new User();
        user.setEmail("not-an-email");
        user.setPasswordHash("hash");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setRole(Role.CLIENT);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations)
                .extracting(ConstraintViolation::getPropertyPath)
                .extracting(Object::toString)
                .contains("email");
    }

}
