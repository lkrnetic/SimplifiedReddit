package com.example.SimplifiedReddit.service;

import com.example.SimplifiedReddit.dto.UserDTO;
import com.example.SimplifiedReddit.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);

    @Transactional
    User createUser(UserDTO userDTO);

    @Transactional
    User editUser(UserDTO userDTO);
}
