package com.example.SimplifiedReddit.service.impl;

import com.example.SimplifiedReddit.dto.UserDTO;
import com.example.SimplifiedReddit.exception.ConflictException;
import com.example.SimplifiedReddit.mapper.UserMapper;
import com.example.SimplifiedReddit.model.User;
import com.example.SimplifiedReddit.model.enums.UserRole;
import com.example.SimplifiedReddit.repository.UserRepository;
import com.example.SimplifiedReddit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
@Slf4j
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        /*
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, email)));

         */
        User user = userRepository.getByEmail(email);
        if(user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", email);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }
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
    public User getById(Long id) throws ConflictException {
        return userRepository.findById(id).orElseThrow(() -> new ConflictException("User with given id doesn't exist."));
    }

    @Transactional(readOnly = true)
    @Override
    public User getByEmail(String email) throws ConflictException {
        return userRepository.findByEmail(email).orElseThrow(() -> new ConflictException("User with given email doesn't exist."));
    }

    @Override
    public User getByUsername(String username) throws ConflictException {
        return userRepository.getByUsername(username);
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
    public User editUser(UserDTO userDTO, Long id) {
        User user = userRepository.getById(id);

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setId(id);

        return userRepository.save(user);
    }

}