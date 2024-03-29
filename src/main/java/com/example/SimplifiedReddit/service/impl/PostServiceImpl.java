package com.example.SimplifiedReddit.service.impl;

import com.example.SimplifiedReddit.dto.PostDTO;
import com.example.SimplifiedReddit.exception.ConflictException;
import com.example.SimplifiedReddit.mapper.PostMapper;
import com.example.SimplifiedReddit.model.Post;
import com.example.SimplifiedReddit.repository.PostRepository;
import com.example.SimplifiedReddit.repository.SubredditRepository;
import com.example.SimplifiedReddit.repository.UserRepository;
import com.example.SimplifiedReddit.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final SubredditRepository subredditRepository;
    private final PostMapper postMapper;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, SubredditRepository subredditRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.subredditRepository = subredditRepository;
        this.postMapper = postMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Post getById(Long id) throws ConflictException {
        return postRepository.findById(id).orElseThrow(() -> new ConflictException("Post with given id doesn't exist."));
    }

    @Transactional
    @Override
    public Post createPost(PostDTO postDTO) {
        userRepository.getById(postDTO.getUserId());

        subredditRepository.getById(postDTO.getSubredditId());

        postDTO.setVoteCount(0);

        return postRepository.save(postMapper.postDTOtoPost(postDTO));
    }

    @Transactional
    @Override
    public Post editPost(PostDTO postDTO, Long id) throws ConflictException {
        Post post = postRepository.getById(id);

        userRepository.getById(postDTO.getUserId());

        if (!Objects.equals(post.getUser().getId(), postDTO.getUserId())) {
            throw new ConflictException("User with given id isn't owner of the post.");
        }

        if (!Objects.equals(post.getSubreddit().getId(), postDTO.getSubredditId())) {
            throw new ConflictException("Given id of subreddit doesn't belong to subreddit where post was created.");
        }

        if (!Objects.equals(post.getVoteCount(), postDTO.getVoteCount())) {
            throw new ConflictException("Vote count can't be changed!");
        }

        postDTO.setId(id);

        return postRepository.save(postMapper.postDTOtoPost(postDTO));
    }

    @Transactional
    @Override
    public void deletePost(Long id){
       postRepository.getById(id);

       postRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Post> findAllByUserId(Long userId) {
        return postRepository.findAllByUserId(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Post> findAllBySubredditId(Long subredditId) {
        return postRepository.findAllBySubredditId(subredditId);
    }

}
