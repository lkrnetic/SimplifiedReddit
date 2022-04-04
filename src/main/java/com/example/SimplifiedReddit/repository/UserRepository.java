package com.example.SimplifiedReddit.repository;

import com.example.SimplifiedReddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getById(Long id);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);
}


