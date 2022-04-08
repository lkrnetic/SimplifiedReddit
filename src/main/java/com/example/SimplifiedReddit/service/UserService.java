package com.example.SimplifiedReddit.service;

import com.example.SimplifiedReddit.dto.UserDTO;
import com.example.SimplifiedReddit.exception.ConflictException;
import com.example.SimplifiedReddit.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);

    User getById(Long id) throws ConflictException;
    User getByEmail(String email) throws ConflictException;
    User getByUsername(String username) throws ConflictException;

    User createUser(UserDTO userDTO);
    User editUser(UserDTO userDTO, Long id) throws ConflictException;
}
