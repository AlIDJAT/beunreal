package com.beunreal.repository;

import com.beunreal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    List<User> findByEmailContainingIgnoreCaseOrUsernameContainingIgnoreCase(String email, String username);
}
