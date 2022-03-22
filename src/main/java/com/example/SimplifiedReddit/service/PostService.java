package com.example.SimplifiedReddit.service;

import com.example.SimplifiedReddit.dto.PostDTO;
import com.example.SimplifiedReddit.model.Post;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PostService {
    Optional<Post> findById(Long id);

    @Transactional
    Post createPost(PostDTO postDTO);

    @Transactional
    void deletePost(Long id);
}
