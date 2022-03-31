package com.example.SimplifiedReddit.dto;

import com.example.SimplifiedReddit.model.enums.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDTO {
    private Long id;

    @NotNull(message = "User id must not be null.")
    @Positive(message = "Username id shouldn't be negative.")
    private Long userId;

    @NotNull(message = "Post id must not be null.")
    @Positive(message = "Post id shouldn't be negative.")
    private Long postId;

    @NotNull(message = "Vote type must not be null.")
    private VoteType voteType;
}
