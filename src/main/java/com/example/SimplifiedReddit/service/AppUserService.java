package com.example.SimplifiedReddit.service;

import com.example.SimplifiedReddit.model.AppUser;

import java.util.Optional;

public interface AppUserService {
    Optional<AppUser> findByEmail(String email);
}
