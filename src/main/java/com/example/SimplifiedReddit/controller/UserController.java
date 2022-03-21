package com.example.SimplifiedReddit.controller;

import com.example.SimplifiedReddit.dto.UserDTO;
import com.example.SimplifiedReddit.model.User;
import com.example.SimplifiedReddit.repository.UserRepository;
import com.example.SimplifiedReddit.service.impl.UserServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path="api/users")
public class UserController {
    private final UserServiceImpl userServiceImpl;
    private final UserRepository userRepository;

    public UserController(UserServiceImpl userServiceImpl, UserRepository userRepository) {
        this.userServiceImpl = userServiceImpl;
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<User> optionalAppUser = userRepository.findById(id);

        if (!optionalAppUser.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-SimplifiedReddit-error", "User with given id doesn't exist.");
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }

        User user = optionalAppUser.get();
        UserDTO userDTO = new UserDTO(user.getId(), user.getEmail(), user.getUsername(), user.getPassword());
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editUser(@RequestBody UserDTO userDTO, @PathVariable Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        Map<String, Object> response = new HashMap<>();

        if (!optionalUser.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-SimplifiedReddit-error", "User with given id doesn't exist.");
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }

        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-SimplifiedReddit-error", "Password is empty or null.");
            return new ResponseEntity<>(headers, HttpStatus.CONFLICT);
        }

        User editedUser = userServiceImpl.editUser(userDTO, id);
        userDTO.setId(editedUser.getId());
        userDTO.setUsername(editedUser.getUsername());
        userDTO.setEmail(editedUser.getEmail());

        response.put("message", "User was edited successfully.");
        response.put("user", userDTO);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);


    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        Optional<User> optionalAppUser = userServiceImpl.findByEmail(userDTO.getEmail());
        Map<String, Object> response = new HashMap<>();

        if (optionalAppUser.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-SimplifiedReddit-error", "There is already user with email that was entered.");
            return new ResponseEntity<>(headers, HttpStatus.CONFLICT);
        }

        User savedUser = userServiceImpl.createUser(userDTO);
        userDTO.setId(savedUser.getId());
        userDTO.setUsername(savedUser.getUsername());
        userDTO.setEmail(savedUser.getEmail());

        response.put("message", "User was created successfully.");
        response.put("user", userDTO);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }
}
