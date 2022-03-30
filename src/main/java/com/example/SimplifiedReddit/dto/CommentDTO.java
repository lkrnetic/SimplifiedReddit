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
public class CommentDTO {
    private Long id;

    @NotBlank(message = "Text must not be blank or null.")
    private String text;

    @NotNull(message = "User id must not be null.")
    @Positive(message = "Username id shouldn't be negative.")
    private Long userId;

    @NotNull(message = "Post id must not be null.")
    @Positive(message = "Post id shouldn't be negative.")
    private Long postId;
}