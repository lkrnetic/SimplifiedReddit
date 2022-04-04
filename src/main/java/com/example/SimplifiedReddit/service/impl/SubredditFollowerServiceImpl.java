package com.example.SimplifiedReddit.service.impl;

import com.example.SimplifiedReddit.dto.SubredditFollowerDTO;
import com.example.SimplifiedReddit.exception.ConflictException;
import com.example.SimplifiedReddit.mapper.SubredditFollowerMapper;
import com.example.SimplifiedReddit.model.Subreddit;
import com.example.SimplifiedReddit.model.SubredditFollower;
import com.example.SimplifiedReddit.model.User;
import com.example.SimplifiedReddit.model.Vote;
import com.example.SimplifiedReddit.repository.SubredditFollowerRepository;
import com.example.SimplifiedReddit.repository.SubredditRepository;
import com.example.SimplifiedReddit.repository.UserRepository;
import com.example.SimplifiedReddit.service.SubredditFollowerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SubredditFollowerServiceImpl implements SubredditFollowerService {
    private final SubredditFollowerRepository subredditFollowerRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final SubredditFollowerMapper subredditFollowerMapper;

    public SubredditFollowerServiceImpl(SubredditFollowerRepository subredditFollowerRepository, SubredditRepository subredditRepository, UserRepository userRepository, SubredditFollowerMapper subredditFollowerMapper) {
        this.subredditFollowerRepository = subredditFollowerRepository;
        this.subredditRepository = subredditRepository;
        this.userRepository = userRepository;
        this.subredditFollowerMapper = subredditFollowerMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<SubredditFollower> findById(Long id) {
        return subredditFollowerRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public SubredditFollower getById(Long id) throws ConflictException {
        return subredditFollowerRepository.findById(id).orElseThrow(() -> new ConflictException("SubredditFollower with given id doesn't exist."));
    }

    @Transactional
    @Override
    public SubredditFollower createSubredditFollower(SubredditFollowerDTO subredditFollowerDTO) throws ConflictException {
        User user = userRepository.getById(subredditFollowerDTO.getUserId());
        Subreddit subreddit = subredditRepository.getById(subredditFollowerDTO.getSubredditId());

        Optional<SubredditFollower> optionalSubredditFollower = subredditFollowerRepository.findByUserAndSubreddit(user, subreddit);

        if (optionalSubredditFollower.isPresent()) {
            throw new ConflictException("SubredditFollower with given user id and subreddit id already exist in database");
        }

        return subredditFollowerRepository.save(subredditFollowerMapper.subredditFollowerDTOtoSubredditFollower(subredditFollowerDTO));
    }

    @Transactional
    @Override
    public void deleteSubredditFollower(Long id) throws ConflictException {
        subredditFollowerRepository.getById(id);

        subredditFollowerRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<SubredditFollower> findAllByUserId(Long userId) {
        return subredditFollowerRepository.findAllByUserId(userId);
    }

}
