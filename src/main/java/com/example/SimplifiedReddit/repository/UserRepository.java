package com.example.SimplifiedReddit.repository;

import com.example.SimplifiedReddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getByUsername(String username);
    User getById(Long id);
    User getByEmail(String email);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);
}


