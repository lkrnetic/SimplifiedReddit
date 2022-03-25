package com.example.SimplifiedReddit.service;

import com.example.SimplifiedReddit.dto.UserDTO;
import com.example.SimplifiedReddit.model.User;
import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);

    User createUser(UserDTO userDTO);
    User editUser(UserDTO userDTO);
}
