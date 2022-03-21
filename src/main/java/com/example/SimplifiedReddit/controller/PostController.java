package com.example.SimplifiedReddit.controller;

import com.example.SimplifiedReddit.dto.PostDTO;
import com.example.SimplifiedReddit.dto.UserDTO;
import com.example.SimplifiedReddit.model.Post;
import com.example.SimplifiedReddit.model.User;
import com.example.SimplifiedReddit.repository.PostRepository;
import com.example.SimplifiedReddit.repository.UserRepository;
import com.example.SimplifiedReddit.service.PostService;
import com.example.SimplifiedReddit.service.impl.PostServiceImpl;
import com.example.SimplifiedReddit.service.impl.UserServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path="api/posts")
public class PostController {
    private final PostServiceImpl postServiceImpl;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostController(PostServiceImpl postServiceImpl, PostRepository postRepository, UserRepository userRepository) {
        this.postServiceImpl = postServiceImpl;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<Post> optionalPost = postRepository.findById(id);

        if (!optionalPost.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-SimplifiedReddit-error", "Post with given id doesn't exist.");
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }

        Post post = optionalPost.get();
        PostDTO postDTO = new PostDTO(post.getId(), post.getTitle(), post.getText(), post.getUser().getId());
        return new ResponseEntity<PostDTO>(postDTO, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO) {
        Map<String, Object> response = new HashMap<>();
        Optional<User> optionalAppUser = userRepository.findById(postDTO.getUserId());

        if (!optionalAppUser.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-SimplifiedReddit-error", "User with given id doesn't exist.");
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
        if (postDTO.getTitle() == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-SimplifiedReddit-error", "Title can't be null!");
            return new ResponseEntity<>(headers, HttpStatus.CONFLICT);
        }

        Post savedPost = postServiceImpl.createPost(postDTO);
        postDTO.setId(savedPost.getId());

        response.put("message", "Post was created successfully.");
        response.put("post", postDTO);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

}
