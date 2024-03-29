package com.example.SimplifiedReddit.repository;

import com.example.SimplifiedReddit.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post getById(Long id);

    Optional<Post> findById(Long id);

    List<Post> findAllByUserId(Long userId);

    List<Post> findAllBySubredditId(Long subredditId);
}
