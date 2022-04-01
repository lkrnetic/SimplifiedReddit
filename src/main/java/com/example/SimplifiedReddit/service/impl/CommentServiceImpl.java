package com.example.SimplifiedReddit.service.impl;

import com.example.SimplifiedReddit.dto.CommentDTO;
import com.example.SimplifiedReddit.exception.ConflictException;
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

    @Transactional(readOnly = true)
    @Override
    public Comment getById(Long id) throws ConflictException {
        return commentRepository.findById(id).orElseThrow(() -> new ConflictException("Comment with given id doesn't exist."));
    }

    @Transactional
    @Override
    public Comment createComment(CommentDTO commentDTO) {
        userRepository.getById(commentDTO.getUserId());

        postRepository.getById(commentDTO.getPostId());

        return commentRepository.save(commentMapper.commentDTOtoComment(commentDTO));
    }

    @Transactional
    @Override
    public Comment editComment(CommentDTO commentDTO, Long id) throws ConflictException {
        Comment comment = commentRepository.getById(id);

        userRepository.getById(commentDTO.getUserId());

        postRepository.getById(commentDTO.getPostId());

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
    public void deleteComment(Long id) {
        Comment comment = commentRepository.getById(id);

        commentRepository.delete(comment);
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
