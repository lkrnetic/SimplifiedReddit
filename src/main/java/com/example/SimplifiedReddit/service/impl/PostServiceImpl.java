package com.example.SimplifiedReddit.service.impl;

import com.example.SimplifiedReddit.dto.PostDTO;
import com.example.SimplifiedReddit.mapper.PostMapper;
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
    private final PostMapper postMapper;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postMapper = postMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Transactional
    @Override
    public Post createPost(PostDTO postDTO) {
        User user = userRepository.findById(postDTO.getUserId()).get();
        Post post = postMapper.postDTOtoPost(postDTO);
        post.setUser(user);
        return postRepository.save(post);
    }

    @Transactional
    @Override
    public void deletePost(Long id) {
        Post post = postRepository.getById(id);
        postRepository.delete(post);
    }

}
