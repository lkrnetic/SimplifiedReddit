package com.example.SimplifiedReddit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubredditFollowerDTO {
    private Long id;

    @NotNull(message = "User id must not be null.")
    @Positive(message = "User id shouldn't be negative.")
    private Long userId;

    @NotNull(message = "Subreddit id must not be null.")
    @Positive(message = "Subreddit id shouldn't be negative.")
    private Long subredditId;
}
