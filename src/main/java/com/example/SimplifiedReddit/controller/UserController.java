package com.example.SimplifiedReddit.controller;

import com.example.SimplifiedReddit.dto.UserDTO;
import com.example.SimplifiedReddit.mapper.UserMapper;
import com.example.SimplifiedReddit.model.User;
import com.example.SimplifiedReddit.service.UserService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/users")
public class UserController {
    public static String ERROR_MESSAGE_KEY = "X-SimplifiedReddit-error";
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {

        Optional<User> optionalUser = userService.findById(id);

        if (optionalUser.isEmpty()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(ERROR_MESSAGE_KEY, "User with given id doesn't exist.");
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userMapper.userToUserDTO(optionalUser.get()), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editUser(@RequestBody UserDTO userDTO, @PathVariable Long id) {
        Optional<User> optionalUser = userService.findById(id);
        HttpHeaders headers = new HttpHeaders();

        if (optionalUser.isEmpty()) {
            headers.add(ERROR_MESSAGE_KEY, "User with given id doesn't exist.");
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }

        User editedUser = userService.editUser(userDTO);
        return new ResponseEntity<>(userMapper.userToUserDTO(editedUser), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO) {
        Optional<User> optionalUser = userService.findByEmail(userDTO.getEmail());
        HttpHeaders headers = new HttpHeaders();

        if (optionalUser.isPresent()) {
            headers.add(ERROR_MESSAGE_KEY, "There is already user with email that was entered.");
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }

        User savedUser = userService.createUser(userDTO);
        return new ResponseEntity<>(userMapper.userToUserDTO(savedUser), HttpStatus.CREATED);
    }

}
