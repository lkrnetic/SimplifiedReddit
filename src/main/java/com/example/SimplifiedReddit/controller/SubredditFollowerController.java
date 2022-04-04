package com.example.SimplifiedReddit.controller;

import com.example.SimplifiedReddit.dto.PostDTO;
import com.example.SimplifiedReddit.dto.SubredditFollowerDTO;
import com.example.SimplifiedReddit.exception.ConflictException;
import com.example.SimplifiedReddit.mapper.SubredditFollowerMapper;
import com.example.SimplifiedReddit.service.SubredditFollowerService;
import com.example.SimplifiedReddit.util.HeaderUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="api/subreddit_followers")
public class SubredditFollowerController {
    private SubredditFollowerService subredditFollowerService;
    private SubredditFollowerMapper subredditFollowerMapper;

    public SubredditFollowerController(SubredditFollowerService subredditFollowerService, SubredditFollowerMapper subredditFollowerMapper) {
        this.subredditFollowerService = subredditFollowerService;
        this.subredditFollowerMapper = subredditFollowerMapper;
    }

    @PostMapping
    public ResponseEntity<?> createSubredditFollower(@Valid @RequestBody SubredditFollowerDTO subredditFollowerDTO) {
        try {
            return new ResponseEntity<>(subredditFollowerMapper.subredditFollowerToSubredditFollowerDTO(subredditFollowerService.createSubredditFollower(subredditFollowerDTO)), HttpStatus.CREATED);
        } catch (ConflictException exception) {
            return new ResponseEntity<>(HeaderUtil.createError(exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(params = {"userId"})
    public ResponseEntity<?> getSubredditFollowerByUserId(@RequestParam Long userId) {
       return new ResponseEntity<>(subredditFollowerService
                .findAllByUserId(userId)
                .stream()
                .map(subredditFollowerMapper::subredditFollowerToSubredditFollowerDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @DeleteMapping(params = {"id"})
    public ResponseEntity<?> deleteSubredditFollower(@RequestParam  Long id) {
        try {
            subredditFollowerService.deleteSubredditFollower(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ConflictException exception) {
            return new ResponseEntity<>(HeaderUtil.createError(exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
