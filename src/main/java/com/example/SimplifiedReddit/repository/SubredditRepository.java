package com.example.SimplifiedReddit.repository;

import com.example.SimplifiedReddit.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
    Subreddit getById(Long id);

    Optional<Subreddit> findById(Long id);
}
