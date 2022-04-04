package com.example.SimplifiedReddit.repository;

import com.example.SimplifiedReddit.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment getById(Long id);

    Optional<Comment> findById(Long id);

    List<Comment> findAllByUserId(Long userId);

    List<Comment> findAllByPostId(Long postId);
}
