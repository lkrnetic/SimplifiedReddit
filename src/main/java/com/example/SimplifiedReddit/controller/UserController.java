package com.example.SimplifiedReddit.controller;

import com.example.SimplifiedReddit.dto.UserDTO;
import com.example.SimplifiedReddit.mapper.UserMapper;
import com.example.SimplifiedReddit.model.User;
import com.example.SimplifiedReddit.service.impl.UserServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="api/users")
public class UserController {
    public static String ERROR_MESSAGE_KEY = "X-SimplifiedReddit-error";
    private final UserServiceImpl userServiceImpl;
    private final UserMapper userMapper;

    public UserController(UserServiceImpl userServiceImpl, UserMapper userMapper) {
        this.userServiceImpl = userServiceImpl;
        this.userMapper = userMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        Optional<User> optionalAppUser = userServiceImpl.findById(id);

        if (optionalAppUser.isEmpty()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(ERROR_MESSAGE_KEY, "User with given id doesn't exist.");
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userMapper.userToUserDTO(optionalAppUser.get()), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editUser(@RequestBody UserDTO userDTO, @PathVariable Long id) {
        Optional<User> optionalUser = userServiceImpl.findById(id);
        HttpHeaders headers = new HttpHeaders();

        if (optionalUser.isEmpty()) {
            headers.add(ERROR_MESSAGE_KEY, "User with given id doesn't exist.");
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }

        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            headers.add(ERROR_MESSAGE_KEY, "Password is empty or null.");
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }

        User editedUser = userServiceImpl.editUser(userDTO);
        return new ResponseEntity<>(userMapper.userToUserDTO(editedUser), HttpStatus.OK);

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        Optional<User> optionalAppUser = userServiceImpl.findByEmail(userDTO.getEmail());

        if (optionalAppUser.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(ERROR_MESSAGE_KEY, "There is already user with email that was entered.");
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }

        User savedUser = userServiceImpl.createUser(userDTO);
        return new ResponseEntity<>(userMapper.userToUserDTO(savedUser), HttpStatus.CREATED);

    }
}