package com.example.SimplifiedReddit.controller;

import com.example.SimplifiedReddit.dto.CommentDTO;
import com.example.SimplifiedReddit.exception.ConflictException;
import com.example.SimplifiedReddit.mapper.CommentMapper;
import com.example.SimplifiedReddit.service.CommentService;
import com.example.SimplifiedReddit.util.HeaderUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="api/comments")
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    public CommentController(CommentService commentService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @GetMapping(params = {"userId"})
    public ResponseEntity<?> getCommentsByUserId(@RequestParam Long userId) {
        return new ResponseEntity<>(commentService
                .findAllByUserId(userId)
                .stream()
                .map(commentMapper::commentToCommentDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(params = {"postId"})
    public ResponseEntity<?> getCommentsByPostId(@RequestParam  Long postId) {
        return new ResponseEntity<>(commentService
                .findAllByPostId(postId)
                .stream()
                .map(commentMapper::commentToCommentDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(params = {"id"})
    public ResponseEntity<?> getCommentById(@RequestParam  Long id) {
        return commentService.findById(id).map(comment -> new ResponseEntity<>(commentMapper.commentToCommentDTO(comment), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HeaderUtil.createError("Comment with given id doesn't exist."), HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> createComment(@Valid @RequestBody CommentDTO commentDTO) {
        try {
            return new ResponseEntity<>(commentMapper.commentToCommentDTO(commentService.createComment(commentDTO)), HttpStatus.OK);
        } catch (ConflictException exception) {
            return new ResponseEntity<>(HeaderUtil.createError(exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(params = {"id"})
    public ResponseEntity<?> editComment(@Valid @RequestBody CommentDTO commentDTO, @RequestParam  Long id) {
        try {
            return new ResponseEntity<>(commentMapper.commentToCommentDTO(commentService.editComment(commentDTO, id)), HttpStatus.OK);
        } catch (ConflictException exception) {
            return new ResponseEntity<>(HeaderUtil.createError(exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(params = {"id"})
    public ResponseEntity<?> deleteComment(@RequestParam  Long id) {
        try {
            commentService.deleteComment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ConflictException exception) {
            return new ResponseEntity<>(HeaderUtil.createError(exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}