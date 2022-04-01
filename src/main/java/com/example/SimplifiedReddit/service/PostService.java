package com.example.SimplifiedReddit.service;

import com.example.SimplifiedReddit.dto.PostDTO;
import com.example.SimplifiedReddit.exception.ConflictException;
import com.example.SimplifiedReddit.exception.NotFoundException;
import com.example.SimplifiedReddit.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post getById(Long id) throws ConflictException;

    Optional<Post> findById(Long id);

    Post createPost(PostDTO postDTO) throws ConflictException;
    Post editPost(PostDTO postDTO, Long id) throws ConflictException;
    void deletePost(Long id) throws ConflictException;

    List<Post> findAll();
    List<Post> findAllByUserId(Long userId);
    List<Post> findAllBySubredditId(Long subredditId);
}
