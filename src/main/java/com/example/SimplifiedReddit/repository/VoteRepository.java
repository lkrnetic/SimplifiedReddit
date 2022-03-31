package com.example.SimplifiedReddit.repository;

import com.example.SimplifiedReddit.model.Comment;
import com.example.SimplifiedReddit.model.Post;
import com.example.SimplifiedReddit.model.User;
import com.example.SimplifiedReddit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findById(Long id);

    Optional<Vote> findByUserAndPost(User user, Post post);
}
