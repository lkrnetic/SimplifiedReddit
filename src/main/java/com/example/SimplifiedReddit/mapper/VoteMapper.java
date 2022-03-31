package com.example.SimplifiedReddit.mapper;

import com.example.SimplifiedReddit.dto.CommentDTO;
import com.example.SimplifiedReddit.dto.VoteDTO;
import com.example.SimplifiedReddit.model.Comment;
import com.example.SimplifiedReddit.model.Post;
import com.example.SimplifiedReddit.model.User;
import com.example.SimplifiedReddit.model.Vote;
import com.example.SimplifiedReddit.service.PostService;
import com.example.SimplifiedReddit.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = { UserService.class, PostService.class })
public abstract class VoteMapper {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @Mapping(target = "postId", source = "post.id")
    @Mapping(target = "userId", source = "user.id")
    public abstract VoteDTO voteToVoteDTO(Vote vote);

    @Mapping(target = "user", source = "voteDTO.userId")
    @Mapping(target = "post", source = "voteDTO.postId")
    public abstract Vote voteDTOtoVote(VoteDTO voteDTO);

    User findUserById(Long userId) {
        return userService.findById(userId).get();
    }
    Post findPostById(Long postId) {
        return postService.findById(postId).get();
    }

}