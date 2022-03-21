package com.example.SimplifiedReddit.repository;

import com.example.SimplifiedReddit.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findById(Long id);
    Optional<AppUser> findByEmail(String email);
    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.password = ?2 WHERE a.email = ?1")
    int updatePassword(String email, String password);
}


