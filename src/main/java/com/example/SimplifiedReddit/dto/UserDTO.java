package com.example.SimplifiedReddit.dto;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String username;
    private String password;
    private String role;
}
