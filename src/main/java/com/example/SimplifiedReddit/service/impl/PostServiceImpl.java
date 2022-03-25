package com.example.SimplifiedReddit.service.impl;

import com.example.SimplifiedReddit.dto.PostDTO;
import com.example.SimplifiedReddit.exception.ConflictException;
import com.example.SimplifiedReddit.exception.NotFoundException;
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
    public Post createPost(PostDTO postDTO) throws ConflictException  {
        Optional<User> optionalUser = userRepository.findById(postDTO.getUserId());

        if (optionalUser.isEmpty()) {
            throw new ConflictException("User with given id doesn't exist.");
        }

        Post post = postMapper.postDTOtoPost(postDTO);
        post.setUser(optionalUser.get());
        return postRepository.save(post);
    }

    @Transactional
    @Override
    public Post editPost(PostDTO postDTO, Long id) throws NotFoundException {

        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            throw new NotFoundException("Post with given id doesn't exist.");
        }

        Optional<User> optionalUser = userRepository.findById(postDTO.getUserId());

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User with given id doesn't exist.");
        }

        Post post = postMapper.postDTOtoPost(postDTO);
        post.setId(id);
        post.setUser(optionalUser.get());

        return postRepository.save(post);
    }

    @Transactional
    @Override
    public void deletePost(Long id) throws NotFoundException {

        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            throw new NotFoundException("Post with given id doesn't exist.");
        }

        postRepository.deleteById(id);
    }

}
