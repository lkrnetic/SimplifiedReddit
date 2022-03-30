package com.example.SimplifiedReddit.service;

import com.example.SimplifiedReddit.dto.CommentDTO;
import com.example.SimplifiedReddit.dto.PostDTO;
import com.example.SimplifiedReddit.exception.ConflictException;
import com.example.SimplifiedReddit.exception.NotFoundException;
import com.example.SimplifiedReddit.model.Comment;
import com.example.SimplifiedReddit.model.Post;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<Comment> findById(Long id);

    Comment createComment(CommentDTO commentDTO) throws ConflictException;
    Comment editComment(CommentDTO commentDTO, Long id) throws NotFoundException, ConflictException;
    void deleteComment(Long id) throws NotFoundException;

    List<Comment> findAllByUserId(Long userId);
    List<Comment> findAllByPostId(Long userId);
}
