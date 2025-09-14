package com.practitioner.backend.practitioner;

import com.practitioner.backend.user.Role;
import com.practitioner.backend.user.User;
import com.practitioner.backend.user.UserRepository;
import com.practitioner.backend.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class PractitionerRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private PractitionerRepository practitionerRepository;

    @Autowired
    private UserRepository userRepository;
    private static final String EMAIL = "doc@example.com";

    @Test
    void saveAndFindPractitioner() {
        // given: a user
        User user = new User();
        user.setEmail(EMAIL);
        user.setPasswordHash("secret");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setRole(Role.PRACTITIONER);
        userRepository.save(user);

        // and: a practitioner tied to that user
        Practitioner practitioner = new Practitioner();
        practitioner.setUser(user);
        practitioner.setBio("Experienced TCM specialist");
        practitioner.setSpecialties("Acupuncture");
        practitionerRepository.save(practitioner);

        // when
        Practitioner found = practitionerRepository.findById(practitioner.getId()).orElseThrow();

        // then
        assertThat(found.getUser().getEmail()).isEqualTo(EMAIL);
        assertThat(found.getBio()).contains("TCM");

        // round-trip through the whole entity:
        assertThat(found)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(practitioner);
    }
}
