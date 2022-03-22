package com.example.SimplifiedReddit.repository;

import com.example.SimplifiedReddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);

    @Modifying
    @Query("UPDATE User a " +
            "SET a.password = ?2 WHERE a.email = ?1")
    void updatePassword(String email, String password);
}


