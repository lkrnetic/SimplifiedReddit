package com.example.SimplifiedReddit.controller;

import com.example.SimplifiedReddit.dto.UserDTO;
import com.example.SimplifiedReddit.mapper.UserMapper;
import com.example.SimplifiedReddit.model.User;
import com.example.SimplifiedReddit.service.UserService;

import com.example.SimplifiedReddit.util.HeaderUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/users")
public class UserController {
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
            return new ResponseEntity<>(HeaderUtil.createError("User with given id doesn't exist."), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userMapper.userToUserDTO(optionalUser.get()), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editUser(@RequestBody UserDTO userDTO, @PathVariable Long id) {
        Optional<User> optionalUser = userService.findById(id);

        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(HeaderUtil.createError("User with given id doesn't exist."), HttpStatus.BAD_REQUEST);
        }

        User editedUser = userService.editUser(userDTO);
        return new ResponseEntity<>(userMapper.userToUserDTO(editedUser), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO) {
        Optional<User> optionalUser = userService.findByEmail(userDTO.getEmail());

        if (optionalUser.isPresent()) {
            return new ResponseEntity<>(HeaderUtil.createError("There is already user with email that was entered."), HttpStatus.BAD_REQUEST);
        }

        User savedUser = userService.createUser(userDTO);
        return new ResponseEntity<>(userMapper.userToUserDTO(savedUser), HttpStatus.CREATED);
    }

}
