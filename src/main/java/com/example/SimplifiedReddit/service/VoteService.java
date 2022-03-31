package com.example.SimplifiedReddit.service;

import com.example.SimplifiedReddit.dto.VoteDTO;
import com.example.SimplifiedReddit.exception.ConflictException;
import com.example.SimplifiedReddit.model.Vote;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface VoteService {
    Optional<Vote> findById(Long id);

    Vote createVote(VoteDTO voteDTO) throws ConflictException;
    Vote editVote(VoteDTO voteDTO, Long id) throws ConflictException;

    @Transactional
    void deleteVote(Long id) throws ConflictException;
}
