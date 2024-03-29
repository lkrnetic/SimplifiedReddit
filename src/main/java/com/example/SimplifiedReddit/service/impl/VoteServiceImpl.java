package com.example.SimplifiedReddit.service.impl;

import com.example.SimplifiedReddit.dto.VoteDTO;
import com.example.SimplifiedReddit.exception.ConflictException;
import com.example.SimplifiedReddit.mapper.VoteMapper;
import com.example.SimplifiedReddit.model.*;
import com.example.SimplifiedReddit.model.enums.VoteType;
import com.example.SimplifiedReddit.repository.PostRepository;
import com.example.SimplifiedReddit.repository.UserRepository;
import com.example.SimplifiedReddit.repository.VoteRepository;
import com.example.SimplifiedReddit.service.VoteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private final VoteMapper voteMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public VoteServiceImpl(VoteRepository voteRepository, VoteMapper voteMapper, PostRepository postRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.voteMapper = voteMapper;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Vote> findById(Long id)  {
        return voteRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Vote getById(Long id) throws ConflictException {
        return voteRepository.findById(id).orElseThrow(() -> new ConflictException("Vote with given id doesn't exist."));
    }

    @Transactional
    @Override
    public Vote createVote(VoteDTO voteDTO) throws ConflictException {

        if ((voteDTO.getVoteType() != VoteType.UPVOTE) && (voteDTO.getVoteType() != VoteType.DOWNVOTE)) {
            throw new ConflictException("Vote type doesn't have appropriate value.");
        }

        User user = userRepository.getById(voteDTO.getUserId());
        Post post = postRepository.getById(voteDTO.getPostId());

        Optional<Vote> optionalVote = voteRepository.findByUserAndPost(user, post);

        if (optionalVote.isPresent()) {
            throw new ConflictException("Vote type already exist in database");
        }

        Vote savedVote = voteRepository.save(voteMapper.voteDTOtoVote(voteDTO));

        post.setVoteCount(post.updateVoteCount(post, savedVote));
        postRepository.save(post);

        return savedVote;
    }

    @Transactional
    @Override
    public Vote editVote(VoteDTO voteDTO, Long id) throws ConflictException {
        Vote vote = voteRepository.getById(id);

        if (vote.getVoteType() == voteDTO.getVoteType()) {
            throw new ConflictException("Vote can't be updated with same type.");
        }

        userRepository.getById(voteDTO.getUserId());

        if (!Objects.equals(vote.getUser().getId(), voteDTO.getUserId())) {
            throw new ConflictException("User with given id isn't owner of the vote.");
        }

        Post post = postRepository.getById(voteDTO.getPostId());

        if (!Objects.equals(vote.getPost().getId(), voteDTO.getPostId())) {
            throw new ConflictException("Given post doesn't doesn't contain given vote.");
        }

        vote.setEdited(true);
        vote.setVoteType(voteDTO.getVoteType());
        voteRepository.save(vote);

        post.setVoteCount(post.updateVoteCount(post, vote));
        postRepository.save(post);

        return vote;
    }

    @Transactional
    @Override
    public void deleteVote(Long id) {
        Vote vote = voteRepository.getById(id);

        Post post = vote.getPost();

        if (!vote.isEdited()) {
            if (vote.getVoteType() == VoteType.UPVOTE) {
                post.setVoteCount(post.getVoteCount() - 1);
            }
            else  { post.setVoteCount(post.getVoteCount() + 1); }
        }

        voteRepository.delete(vote);
        postRepository.save(post);
    }

}
