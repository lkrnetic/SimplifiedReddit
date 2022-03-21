package com.example.SimplifiedReddit.service.impl;

import com.example.SimplifiedReddit.dto.UserDTO;
import com.example.SimplifiedReddit.model.User;
import com.example.SimplifiedReddit.model.enums.UserRole;
import com.example.SimplifiedReddit.repository.UserRepository;
import com.example.SimplifiedReddit.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, email)));
    }

    @Transactional
    public User createUser(UserDTO userDTO) {
        String encodedPassword = bCryptPasswordEncoder.encode(userDTO.getPassword());
        User user = new User(userDTO.getEmail(), userDTO.getUsername(), encodedPassword, UserRole.USER);
        user.setEnabled(true);
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @Transactional
    public User editUser(UserDTO userDTO, Long id) {
        String encodedPassword = bCryptPasswordEncoder.encode(userDTO.getPassword());
        User updatedUser = new User(userDTO.getEmail(), userDTO.getUsername(), encodedPassword, UserRole.USER);
        updatedUser.setId(id);
        userRepository.updatePassword(updatedUser.getEmail(), updatedUser.getPassword());
        return updatedUser;
    }

}