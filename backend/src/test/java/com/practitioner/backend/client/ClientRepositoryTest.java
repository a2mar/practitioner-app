package com.practitioner.backend.client;

import com.practitioner.backend.AbstractIntegrationTest;
import com.practitioner.backend.user.Role;
import com.practitioner.backend.user.User;
import com.practitioner.backend.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class ClientRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveAndFindClient() {
        // given: a user
        User user = new User();
        user.setEmail("client@example.com");
        user.setPasswordHash("secret");
        user.setFirstName("Max");
        user.setLastName("Mustermann");
        user.setRole(Role.CLIENT);
        userRepository.save(user);

        // and: a client tied to that user
        Client client = new Client();
        client.setUser(user);
        client.setInsuranceNumber("ABC12345");
        client.setInsuranceCompany("Example Insurance AG");
        clientRepository.save(client);

        // when
        Client found = clientRepository.findById(client.getId()).orElseThrow();

        // then
        assertThat(found.getUser().getEmail()).isEqualTo("client@example.com");
        assertThat(found.getInsuranceCompany()).contains("Insurance");
    }
}
