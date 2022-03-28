package com.example.SimplifiedReddit.controller;

import com.example.SimplifiedReddit.dto.PostDTO;
import com.example.SimplifiedReddit.exception.ConflictException;
import com.example.SimplifiedReddit.exception.NotFoundException;
import com.example.SimplifiedReddit.mapper.PostMapper;
import com.example.SimplifiedReddit.service.PostService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="api/posts")
public class PostController {
    public static String ERROR_MESSAGE_KEY = "X-SimplifiedReddit-error";
    private final PostService postService;
    private final PostMapper postMapper;

    public PostController(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        return postService.findById(id).map(post -> new ResponseEntity<>(postMapper.postToPostDTO(post), HttpStatus.OK))
                .orElseGet(() -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.add(ERROR_MESSAGE_KEY, "Post with given id doesn't exist.");
                    return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
                });
    }

    @GetMapping
    public ResponseEntity<?> getPosts() {
        return new ResponseEntity<>(postService
                .findAll()
                .stream()
                .map(postMapper::postToPostDTO)
                .collect(Collectors.toList()),HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDTO postDTO) {
        try {
            return new ResponseEntity<>(postMapper.postToPostDTO(postService.createPost(postDTO)), HttpStatus.OK);
        } catch (ConflictException exception) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(ERROR_MESSAGE_KEY, exception.getMessage());
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editPost(@Valid @RequestBody PostDTO postDTO, @PathVariable Long id) {
        try {
            return new ResponseEntity<>(postMapper.postToPostDTO(postService.editPost(postDTO, id)), HttpStatus.OK);
        } catch (NotFoundException | ConflictException exception) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(ERROR_MESSAGE_KEY, exception.getMessage());
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException exception) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(ERROR_MESSAGE_KEY, exception.getMessage());
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

}
