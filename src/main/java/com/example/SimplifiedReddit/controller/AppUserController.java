package com.example.SimplifiedReddit.controller;

import com.example.SimplifiedReddit.dto.AppUserDTO;
import com.example.SimplifiedReddit.model.AppUser;
import com.example.SimplifiedReddit.repository.AppUserRepository;
import com.example.SimplifiedReddit.service.AppUserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path="api/users")
@AllArgsConstructor
public class AppUserController {
    private final AppUserServiceImpl appUserServiceImpl;
    private final AppUserRepository appUserRepository;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<AppUser> optionalAppUser = appUserRepository.findById(id);
        if (!optionalAppUser.isPresent()) {
            response.put("message", "User with given id doesn't exist.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
        }

        try {
            AppUser appUser = appUserRepository.getById(id);
            return new ResponseEntity<AppUser>(appUser, HttpStatus.OK);
        } catch (DataAccessException e)  {
            response.put("message", "Error with database query.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editUser(@RequestBody AppUserDTO request, @PathVariable Long id) {
        Optional<AppUser> appUser = appUserRepository.findById(id);
        Map<String, Object> response = new HashMap<>();
        if (!appUser.isPresent()) {
            response.put("message", "User with given id doesn't exist.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
        }

        try {
            if (request.getPassword() == null) {
                response.put("message", "Password can't be null.");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
            }
            AppUser editedAppUser = appUserServiceImpl.editUser(request, id);
            response.put("message", "User was edited successfully.");
            response.put("user", editedAppUser);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        }

        catch (DataAccessException e)  {
            response.put("message", "Error with database query.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //return appUserServiceImpl.editUser(request);

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createUser(@RequestBody AppUserDTO request) {
        Optional<AppUser> appUser = appUserServiceImpl.findByEmail(request.getEmail());
        //AppUser savedAppUser = null;
        Map<String, Object> response = new HashMap<>();
        if (appUser.isPresent()) {
            response.put("message", "There is already user with email that was entered.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
        }

        try {
            AppUser savedAppUser = appUserServiceImpl.createUser(request);
            response.put("message", "User was created successfully.");
            response.put("user", savedAppUser);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        }

        catch (DataAccessException e)  {
            response.put("message", "Error with database query.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
