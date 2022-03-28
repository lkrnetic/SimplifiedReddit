package com.example.SimplifiedReddit.controller;

import com.example.SimplifiedReddit.dto.SubredditDTO;
import com.example.SimplifiedReddit.exception.ConflictException;
import com.example.SimplifiedReddit.exception.NotFoundException;
import com.example.SimplifiedReddit.mapper.SubredditMapper;
import com.example.SimplifiedReddit.service.SubredditService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping(path="api/subreddits")
public class SubredditController {
    public static String ERROR_MESSAGE_KEY = "X-SimplifiedReddit-error";
    private final SubredditService subredditService;
    private final SubredditMapper subredditMapper;

    public SubredditController(SubredditService subredditService, SubredditMapper subredditMapper) {
        this.subredditService = subredditService;
        this.subredditMapper = subredditMapper;
    }

    @GetMapping
    public ResponseEntity<?> getAllSubreddits() {
        return new ResponseEntity<>(subredditService
                .findAll()
                .stream()
                .map(subredditMapper::subredditToSubredditDTO)
                .collect(Collectors.toList()),HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createSubreddit(@RequestBody SubredditDTO subredditDTO) {
        try {
            return new ResponseEntity<>(subredditMapper.subredditToSubredditDTO(subredditService.createSubreddit(subredditDTO)), HttpStatus.OK);
        } catch (ConflictException exception) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(ERROR_MESSAGE_KEY, exception.getMessage());
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(params = {"id"})
    public ResponseEntity<?> deleteSubreddit(@RequestParam  Long id) {
        try {
            subredditService.deleteSubreddit(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException exception) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(ERROR_MESSAGE_KEY, exception.getMessage());
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

}
