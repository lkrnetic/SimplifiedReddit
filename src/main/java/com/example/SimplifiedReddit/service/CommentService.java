package com.example.SimplifiedReddit.service;

import com.example.SimplifiedReddit.dto.CommentDTO;
import com.example.SimplifiedReddit.exception.ConflictException;
import com.example.SimplifiedReddit.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<Comment> findById(Long id);

    Comment getById(Long id) throws ConflictException;

    Comment createComment(CommentDTO commentDTO) throws ConflictException;
    Comment editComment(CommentDTO commentDTO, Long id) throws ConflictException;
    void deleteComment(Long id) throws ConflictException;

    List<Comment> findAllByUserId(Long userId);
    List<Comment> findAllByPostId(Long userId);
}
