package com.example.SimplifiedReddit.repository;

import com.example.SimplifiedReddit.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubredditFollowerRepository extends JpaRepository<SubredditFollower, Long> {
    Optional<SubredditFollower> findById(Long id);

    List<SubredditFollower> findAllByUserId(Long userId);

    Optional<SubredditFollower> findByUserAndSubreddit(User user, Subreddit subreddit);

    SubredditFollower getById(Long id);

}
