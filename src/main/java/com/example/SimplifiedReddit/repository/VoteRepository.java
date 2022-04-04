package com.example.SimplifiedReddit.repository;

import com.example.SimplifiedReddit.model.Post;
import com.example.SimplifiedReddit.model.User;
import com.example.SimplifiedReddit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Vote getById(Long id);

    Optional<Vote> findById(Long id);

    Optional<Vote> findByUserAndPost(User user, Post post);
}
