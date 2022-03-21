package com.example.SimplifiedReddit.service;

import com.example.SimplifiedReddit.dto.AppUserDTO;
import com.example.SimplifiedReddit.model.AppUser;
import com.example.SimplifiedReddit.model.AppUserRole;
import com.example.SimplifiedReddit.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service @AllArgsConstructor
public class AppUserServiceImpl implements UserDetailsService, AppUserService {
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Optional<AppUser> findByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, email)));
    }

    public AppUser createUser(AppUserDTO appUserDTO) {
        String encodedPassword = bCryptPasswordEncoder.encode(appUserDTO.getPassword());
        AppUser appUser = new AppUser(appUserDTO.getEmail(), appUserDTO.getUsername(), encodedPassword, AppUserRole.USER);
        appUser.setEnabled(true);
        AppUser savedAppUser = appUserRepository.save(appUser);
        return savedAppUser;
    }

    public AppUser editUser(AppUserDTO appUserDTO, Long id) {
        //Optional<AppUser> foundAppUser = appUserRepository.findById(id);
        String encodedPassword = bCryptPasswordEncoder.encode(appUserDTO.getPassword());
        AppUser updatedAppUser = new AppUser(appUserDTO.getEmail(), appUserDTO.getUsername(), encodedPassword, AppUserRole.USER);
        updatedAppUser.setId(id);
        appUserRepository.updatePassword(updatedAppUser.getEmail(), updatedAppUser.getPassword());
        return updatedAppUser;
    }

}