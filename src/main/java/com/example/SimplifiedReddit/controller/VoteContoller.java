package com.example.SimplifiedReddit.controller;

import com.example.SimplifiedReddit.dto.VoteDTO;
import com.example.SimplifiedReddit.exception.ConflictException;
import com.example.SimplifiedReddit.mapper.VoteMapper;
import com.example.SimplifiedReddit.service.VoteService;
import com.example.SimplifiedReddit.util.HeaderUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/votes")
public class VoteContoller {
    private final VoteService voteService;
    private final VoteMapper voteMapper;

    public VoteContoller(VoteService voteService, VoteMapper voteMapper) {
        this.voteService = voteService;
        this.voteMapper = voteMapper;
    }

    @PostMapping
    public ResponseEntity<?> createVote(@Valid @RequestBody VoteDTO voteDTO) {
        try {
            return new ResponseEntity<>(voteMapper.voteToVoteDTO(voteService.createVote(voteDTO)), HttpStatus.CREATED);
        } catch (ConflictException exception) {
            return new ResponseEntity<>(HeaderUtil.createError(exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(params = {"id"})
    public ResponseEntity<?> editVote(@Valid @RequestBody VoteDTO voteDTO, @RequestParam Long id) {
        try {
            return new ResponseEntity<>(voteMapper.voteToVoteDTO(voteService.editVote(voteDTO, id)), HttpStatus.OK);
        } catch (ConflictException exception) {
            return new ResponseEntity<>(HeaderUtil.createError(exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(params = {"id"})
    public ResponseEntity<?> deleteVote(@RequestParam  Long id) {
        try {
            voteService.deleteVote(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ConflictException exception) {
            return new ResponseEntity<>(HeaderUtil.createError(exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
