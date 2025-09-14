package com.practitioner.backend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // duery derivation: this method gets implemented at runtime using the 'findBy' keyword and 'Email'
    Optional<User> findByEmail(String email); 
}
