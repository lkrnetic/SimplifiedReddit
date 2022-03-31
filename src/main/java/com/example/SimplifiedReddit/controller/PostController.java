package com.example.SimplifiedReddit.controller;

import com.example.SimplifiedReddit.dto.PostDTO;
import com.example.SimplifiedReddit.exception.ConflictException;
import com.example.SimplifiedReddit.exception.NotFoundException;
import com.example.SimplifiedReddit.mapper.PostMapper;
import com.example.SimplifiedReddit.service.PostService;
import com.example.SimplifiedReddit.util.HeaderUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="api/posts")
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;

    public PostController(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

    @GetMapping
    public ResponseEntity<?> getAllPosts() {
        return new ResponseEntity<>(postService
                .findAll()
                .stream()
                .map(postMapper::postToPostDTO)
                .collect(Collectors.toList()),HttpStatus.OK);
    }

    @GetMapping(params = {"userId"})
    public ResponseEntity<?> getPostsByUserId(@RequestParam  Long userId) {
        return new ResponseEntity<>(postService
                .findAllByUserId(userId)
                .stream()
                .map(postMapper::postToPostDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(params = {"subredditId"})
    public ResponseEntity<?> getPostsBySubredditId(@RequestParam  Long subredditId) {
        return new ResponseEntity<>(postService
                .findAllBySubredditId(subredditId)
                .stream()
                .map(postMapper::postToPostDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(params = {"id"})
    public ResponseEntity<?> getPostById(@RequestParam  Long id) {
        return postService.findById(id).map(post -> new ResponseEntity<>(postMapper.postToPostDTO(post), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HeaderUtil.createError("Post with given id doesn't exist."), HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDTO postDTO) {
        try {
            return new ResponseEntity<>(postMapper.postToPostDTO(postService.createPost(postDTO)), HttpStatus.CREATED);
        } catch (ConflictException exception) {
            return new ResponseEntity<>(HeaderUtil.createError(exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(params = {"id"})
    public ResponseEntity<?> editPost(@Valid @RequestBody PostDTO postDTO, @RequestParam  Long id) {
        try {
            return new ResponseEntity<>(postMapper.postToPostDTO(postService.editPost(postDTO, id)), HttpStatus.OK);
        } catch (NotFoundException | ConflictException exception) {
            return new ResponseEntity<>(HeaderUtil.createError(exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(params = {"id"})
    public ResponseEntity<?> deletePost(@RequestParam  Long id) {
        try {
            postService.deletePost(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException exception) {
            return new ResponseEntity<>(HeaderUtil.createError(exception.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

}
