package com.example.SimplifiedReddit.service.impl;

import com.example.SimplifiedReddit.dto.UserDTO;
import com.example.SimplifiedReddit.mapper.UserMapper;
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
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
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
    @Override
    public User createUser(UserDTO userDTO) {
        UserRole role = UserRole.USER;
        User user = userMapper.userDTOtoUser(userDTO);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setRole(role);
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User editUser(UserDTO userDTO) {
        User user = userMapper.userDTOtoUser(userDTO);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.updatePassword(user.getEmail(), user.getPassword());
        return user;
    }

}