package com.example.SimplifiedReddit.controller;

import com.example.SimplifiedReddit.dto.UserDTO;
import com.example.SimplifiedReddit.mapper.UserMapper;
import com.example.SimplifiedReddit.model.User;
import com.example.SimplifiedReddit.model.enums.UserRole;
import com.example.SimplifiedReddit.service.impl.UserServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="api/users")
public class UserController {
    private final UserServiceImpl userServiceImpl;
    private final UserMapper userMapper;

    public UserController(UserServiceImpl userServiceImpl, UserMapper userMapper) {
        this.userServiceImpl = userServiceImpl;
        this.userMapper = userMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        Optional<User> optionalAppUser = userServiceImpl.findById(id);

        if (!optionalAppUser.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-SimplifiedReddit-error", "User with given id doesn't exist.");
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(userMapper.userToUserDTO(optionalAppUser.get()), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editUser(@RequestBody UserDTO userDTO, @PathVariable Long id) {
        Optional<User> optionalUser = userServiceImpl.findById(id);

        if (!optionalUser.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-SimplifiedReddit-error", "User with given id doesn't exist.");

            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }

        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-SimplifiedReddit-error", "Password is empty or null.");

            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }

        UserRole userRole = UserRole.USER;
        User editedUser = userServiceImpl.editUser(userMapper.userDTOtoUser(userDTO, userRole));

        return new ResponseEntity(userMapper.userToUserDTO(editedUser), HttpStatus.OK);

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        Optional<User> optionalAppUser = userServiceImpl.findByEmail(userDTO.getEmail());

        if (optionalAppUser.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-SimplifiedReddit-error", "There is already user with email that was entered.");

            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }

        UserRole userRole = UserRole.USER;
        User savedUser = userServiceImpl.createUser(userMapper.userDTOtoUser(userDTO, userRole));

        return new ResponseEntity(userMapper.userToUserDTO(savedUser), HttpStatus.CREATED);

    }
}
