package com.example.SimplifiedReddit.service;

import com.example.SimplifiedReddit.dto.PostDTO;
import com.example.SimplifiedReddit.dto.SubredditFollowerDTO;
import com.example.SimplifiedReddit.exception.ConflictException;
import com.example.SimplifiedReddit.model.SubredditFollower;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SubredditFollowerService {
    SubredditFollower getById(Long id) throws ConflictException;

    Optional<SubredditFollower> findById(Long id);

    SubredditFollower createSubredditFollower(SubredditFollowerDTO subredditFollowerDTO) throws ConflictException;

    void deleteSubredditFollower(Long id) throws ConflictException;

    List<SubredditFollower> findAllByUserId(Long userId);
}
