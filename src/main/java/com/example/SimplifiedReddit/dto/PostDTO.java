package com.example.SimplifiedReddit.dto;

import com.example.SimplifiedReddit.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private String title;
    private String text;
    private Long userId;
}
