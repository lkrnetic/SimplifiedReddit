package com.example.SimplifiedReddit.dto;

import lombok.*;


//@EqualsAndHashCode
//@ToString
@Getter
@Setter
@AllArgsConstructor
public class AppUserDTO {
    private String email;
    private String username;
    private String password;
}
