package com.example.SimplifiedReddit.controller;

import com.example.SimplifiedReddit.dto.CommentDTO;
import com.example.SimplifiedReddit.exception.ConflictException;
import com.example.SimplifiedReddit.exception.NotFoundException;
import com.example.SimplifiedReddit.mapper.CommentMapper;
import com.example.SimplifiedReddit.service.CommentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="api/comments")
public class CommentController {
    public static String ERROR_MESSAGE_KEY = "X-SimplifiedReddit-error";
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    public CommentController(CommentService commentService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @GetMapping(params = {"userId"})
    public ResponseEntity<?> getCommentsByUserId(@RequestParam Long userId) {
        System.out.println("blabla1");
        System.out.println(userId);
        return new ResponseEntity<>(commentService
                .findAllByUserId(userId)
                .stream()
                .map(commentMapper::commentToCommentDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(params = {"postId"})
    public ResponseEntity<?> getCommentsByPostId(@RequestParam  Long postId) {
        System.out.println(postId);
        System.out.println("blabla2");
        return new ResponseEntity<>(commentService
                .findAllByPostId(postId)
                .stream()
                .map(commentMapper::commentToCommentDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(params = {"id"})
    public ResponseEntity<?> getCommentById(@RequestParam  Long id) {
        System.out.println("blabla3");
        return commentService.findById(id).map(comment -> new ResponseEntity<>(commentMapper.commentToCommentDTO(comment), HttpStatus.OK))
                .orElseGet(() -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.add(ERROR_MESSAGE_KEY, "Post with given id doesn't exist.");
                    return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
                });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createComment(@Valid @RequestBody CommentDTO commentDTO) {
        try {
            return new ResponseEntity<>(commentMapper.commentToCommentDTO(commentService.createComment(commentDTO)), HttpStatus.OK);

        } catch (ConflictException exception) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(ERROR_MESSAGE_KEY, exception.getMessage());
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(params = {"id"})
    public ResponseEntity<?> editComment(@Valid @RequestBody CommentDTO commentDTO, @RequestParam  Long id) {
        try {
            return new ResponseEntity<>(commentMapper.commentToCommentDTO(commentService.editComment(commentDTO, id)), HttpStatus.OK);

        } catch (NotFoundException | ConflictException exception) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(ERROR_MESSAGE_KEY, exception.getMessage());
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(params = {"id"})
    public ResponseEntity<?> deleteComment(@RequestParam  Long id) {
        try {
            commentService.deleteComment(id);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (NotFoundException exception) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(ERROR_MESSAGE_KEY, exception.getMessage());
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

}