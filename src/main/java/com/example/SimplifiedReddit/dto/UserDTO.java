package com.example.SimplifiedReddit.dto;

import com.example.SimplifiedReddit.model.Subreddit;
import com.example.SimplifiedReddit.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @Email(message = "Email must be in appropriate format.")
    private String email;

    @NotBlank(message = "Username must not be blank or null.")
    private String username;

    @NotBlank(message = "Password must not be blank or null.")
    private String password;

    private UserRole role;

    private Set<Subreddit> followedSubreddits;

}
