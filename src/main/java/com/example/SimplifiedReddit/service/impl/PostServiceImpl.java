package com.example.SimplifiedReddit.service.impl;

import com.example.SimplifiedReddit.dto.PostDTO;
import com.example.SimplifiedReddit.model.Post;
import com.example.SimplifiedReddit.model.User;
import com.example.SimplifiedReddit.repository.PostRepository;
import com.example.SimplifiedReddit.repository.UserRepository;
import com.example.SimplifiedReddit.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Transactional
    public Post createPost(PostDTO postDTO) {
        User user = userRepository.findById(postDTO.getUserId()).get();
        Post post = new Post(postDTO.getTitle(), postDTO.getText(), user);
        Post savedPost = postRepository.save(post);
        return savedPost;
    }

    @Transactional
    @Override
    public void deletePost(Long id) {
        Post post = postRepository.getById(id);
        postRepository.delete(post);
    }

}
