package com.example.SimplifiedReddit.service;

import com.example.SimplifiedReddit.model.Post;

import java.util.Optional;

public interface PostService {
    Optional<Post> findById(Long id);

    void deletePost(Long id);
}
