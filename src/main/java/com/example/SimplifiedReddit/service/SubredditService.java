package com.example.SimplifiedReddit.service;

import com.example.SimplifiedReddit.dto.SubredditDTO;
import com.example.SimplifiedReddit.dto.SubredditFollowerDTO;
import com.example.SimplifiedReddit.exception.ConflictException;
import com.example.SimplifiedReddit.model.Subreddit;

import java.util.List;
import java.util.Optional;

public interface SubredditService {
    Optional<Subreddit> findById(Long id);

    Subreddit getById(Long id) throws ConflictException;

    Subreddit createSubreddit(SubredditDTO subredditDTO) throws ConflictException;
    void deleteSubreddit(Long id) throws  ConflictException;

    List<Subreddit> findAll();

    void createSubredditFollower(SubredditFollowerDTO subredditFollowerDTO) throws ConflictException;
}

