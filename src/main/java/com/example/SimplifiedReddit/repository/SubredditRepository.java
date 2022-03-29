package com.example.SimplifiedReddit.repository;

import com.example.SimplifiedReddit.model.Subreddit;
import com.example.SimplifiedReddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
    Optional<Subreddit> findById(Long id);
}
