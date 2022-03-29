package com.example.SimplifiedReddit.service;

import com.example.SimplifiedReddit.dto.SubredditDTO;
import com.example.SimplifiedReddit.exception.ConflictException;
import com.example.SimplifiedReddit.exception.NotFoundException;
import com.example.SimplifiedReddit.model.Subreddit;
import com.example.SimplifiedReddit.model.User;

import java.util.List;
import java.util.Optional;

public interface SubredditService {
    Optional<Subreddit> findById(Long id);

    Subreddit createSubreddit(SubredditDTO subredditDTO) throws ConflictException;
    void deleteSubreddit(Long id) throws NotFoundException;

    List<Subreddit> findAll();

}

