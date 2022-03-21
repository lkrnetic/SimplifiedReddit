package com.example.SimplifiedReddit.dto;

import lombok.*;


//@EqualsAndHashCode
//@ToString
@Data
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String username;
    private String password;
}
