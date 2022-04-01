package com.example.SimplifiedReddit.controller;

import com.example.SimplifiedReddit.dto.SubredditDTO;
import com.example.SimplifiedReddit.exception.ConflictException;
import com.example.SimplifiedReddit.exception.NotFoundException;
import com.example.SimplifiedReddit.mapper.SubredditMapper;
import com.example.SimplifiedReddit.service.SubredditService;
import com.example.SimplifiedReddit.util.HeaderUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="api/subreddits")
public class SubredditController {
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
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(params = {"id"})
    public ResponseEntity<?> getSubredditById(@RequestParam Long id) {
        return subredditService.findById(id).map(subreddit -> new ResponseEntity<>(subredditMapper.subredditToSubredditDTO(subreddit), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HeaderUtil.createError("Subreddit with given id doesn't exist."), HttpStatus.NOT_FOUND));

    }

    @PostMapping
    public ResponseEntity<?> createSubreddit(@Valid @RequestBody SubredditDTO subredditDTO) {
        try {
            return new ResponseEntity<>(subredditMapper.subredditToSubredditDTO(subredditService.createSubreddit(subredditDTO)), HttpStatus.CREATED);
        } catch (ConflictException exception) {
            return new ResponseEntity<>(HeaderUtil.createError(exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(params = {"id"})
    public ResponseEntity<?> deleteSubreddit(@RequestParam  Long id) {
        try {
            subredditService.deleteSubreddit(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ConflictException exception) {
            return new ResponseEntity<>(HeaderUtil.createError(exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
