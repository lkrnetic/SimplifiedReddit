package com.example.SimplifiedReddit.service;

import com.example.SimplifiedReddit.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
}
