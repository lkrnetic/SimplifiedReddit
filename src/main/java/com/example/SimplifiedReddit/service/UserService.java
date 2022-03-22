package com.example.SimplifiedReddit.service;

import com.example.SimplifiedReddit.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    User createUser(User user);
    User editUser(User user);


}
