package com.example.SimplifiedReddit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private Long id;
    @NotBlank(message = "Title must not be blank or null.")
    private String title;
    private String text;
    @NotNull(message = "User id must not be null.")
    @Positive(message = "Username id shouldn't be negative.")
    private Long userId;
}
