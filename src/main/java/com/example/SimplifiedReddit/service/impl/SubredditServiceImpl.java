package com.example.SimplifiedReddit.service.impl;

import com.example.SimplifiedReddit.dto.SubredditDTO;
import com.example.SimplifiedReddit.exception.ConflictException;
import com.example.SimplifiedReddit.mapper.SubredditMapper;
import com.example.SimplifiedReddit.model.Subreddit;
import com.example.SimplifiedReddit.model.User;
import com.example.SimplifiedReddit.repository.SubredditRepository;
import com.example.SimplifiedReddit.repository.UserRepository;
import com.example.SimplifiedReddit.service.SubredditService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SubredditServiceImpl implements SubredditService{
    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;
    private final UserRepository userRepository;

    public SubredditServiceImpl(SubredditRepository subredditRepository, SubredditMapper subredditMapper, UserRepository userRepository) {
        this.subredditRepository = subredditRepository;
        this.subredditMapper = subredditMapper;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Subreddit> findById(Long id) {
        return subredditRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Subreddit getById(Long id) throws ConflictException {
        return subredditRepository.findById(id).orElseThrow(() -> new ConflictException("Subreddit with given id doesn't exist."));
    }

    @Transactional
    @Override
    public Subreddit createSubreddit(SubredditDTO subredditDTO) {
        User user = userRepository.getById(subredditDTO.getUserId());

        Subreddit subreddit = subredditMapper.subredditDTOtoSubreddit(subredditDTO);
        subreddit.setUser(user);
        return subredditRepository.save(subreddit);
    }

    @Transactional
    @Override
    public void deleteSubreddit(Long id) {
        subredditRepository.getById(id);

        subredditRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Subreddit> findAll() {
        return subredditRepository.findAll();
    }

}
