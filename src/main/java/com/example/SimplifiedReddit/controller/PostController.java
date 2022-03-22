package com.example.SimplifiedReddit.controller;

import com.example.SimplifiedReddit.dto.PostDTO;
import com.example.SimplifiedReddit.mapper.PostMapper;
import com.example.SimplifiedReddit.service.impl.PostServiceImpl;
import com.example.SimplifiedReddit.service.impl.UserServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="api/posts")
public class PostController {

    public static String ERROR_MESSAGE_KEY = "X-SimplifiedReddit-error";
    private final PostServiceImpl postServiceImpl;
    private final PostMapper postMapper;
    private final UserServiceImpl userServiceImpl;

    public PostController(PostServiceImpl postServiceImpl, PostMapper postMapper, UserServiceImpl userServiceImpl) {
        this.postServiceImpl = postServiceImpl;
        this.postMapper = postMapper;
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        return postServiceImpl.findById(id).map(postPost -> new ResponseEntity<>(postMapper.postToPostDTO(postPost), HttpStatus.OK))
                .orElseGet(() -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.add(ERROR_MESSAGE_KEY, "User with given id doesn't exist.");
                    return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
                });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO) {

        if (postDTO.getTitle().isEmpty()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(ERROR_MESSAGE_KEY, "Title of the post can't be empty.");
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }

        return userServiceImpl.findById(postDTO.getUserId()).
                map(x -> new ResponseEntity<>(postMapper.postToPostDTO(postServiceImpl.createPost(postDTO)), HttpStatus.OK))
                .orElseGet(() -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.add(ERROR_MESSAGE_KEY, "User with given id doesn't exist.");
                    return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
                });
    }

}
