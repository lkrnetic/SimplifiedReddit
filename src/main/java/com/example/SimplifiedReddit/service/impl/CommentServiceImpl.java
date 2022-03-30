package com.example.SimplifiedReddit.service.impl;

import com.example.SimplifiedReddit.dto.CommentDTO;
import com.example.SimplifiedReddit.exception.ConflictException;
import com.example.SimplifiedReddit.exception.NotFoundException;
import com.example.SimplifiedReddit.mapper.CommentMapper;
import com.example.SimplifiedReddit.model.Comment;
import com.example.SimplifiedReddit.repository.CommentRepository;
import com.example.SimplifiedReddit.repository.PostRepository;
import com.example.SimplifiedReddit.repository.UserRepository;
import com.example.SimplifiedReddit.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentMapper = commentMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> findById(Long id)  {
        return commentRepository.findById(id);
    }

    @Transactional
    @Override
    public Comment createComment(CommentDTO commentDTO) throws ConflictException {
        userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new ConflictException("User with given id doesn't exist."));

        postRepository.findById(commentDTO.getPostId())
                .orElseThrow(() -> new ConflictException("Post with given id doesn't exist."));

        Comment comment = commentMapper.commentDTOtoComment(commentDTO);

        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public Comment editComment(CommentDTO commentDTO, Long id) throws NotFoundException, ConflictException {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ConflictException("Comment with given id doesn't exist."));

        userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("User with given id doesn't exist."));

        postRepository.findById(commentDTO.getPostId())
                .orElseThrow(() -> new NotFoundException("Post with given id doesn't exist."));

        if (!Objects.equals(comment.getPost().getId(), commentDTO.getPostId())) {
            throw new ConflictException("Given id of post doesn't belong to post where comment was created.");
        }

        if (!Objects.equals(comment.getUser().getId(), commentDTO.getUserId())) {
            throw new ConflictException("Given id of comment doesn't belong to user who created comment with given id.");
        }

        commentDTO.setId(id);

        return commentRepository.save(commentMapper.commentDTOtoComment(commentDTO));
    }

    @Transactional
    @Override
    public void deleteComment(Long id) throws NotFoundException {
        commentRepository.delete(commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subreddit with given id doesn't exist.")));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findAllByUserId(Long userId)  {
        return commentRepository.findAllByUserId(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findAllByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId);
    }
}
