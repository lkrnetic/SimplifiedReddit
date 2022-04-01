package com.example.SimplifiedReddit.mapper;

import com.example.SimplifiedReddit.dto.CommentDTO;
import com.example.SimplifiedReddit.dto.VoteDTO;
import com.example.SimplifiedReddit.exception.ConflictException;
import com.example.SimplifiedReddit.model.Comment;
import com.example.SimplifiedReddit.model.Post;
import com.example.SimplifiedReddit.model.User;
import com.example.SimplifiedReddit.model.Vote;
import com.example.SimplifiedReddit.service.PostService;
import com.example.SimplifiedReddit.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
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

    @Mapping(target = "user", source = "userId", qualifiedByName = "getUserById")
    @Mapping(target = "post", source = "postId", qualifiedByName = "getPostById")
    public abstract Vote voteDTOtoVote(VoteDTO voteDTO);

    @Named("getUserById")
    User getUserById(Long userId) throws ConflictException {
        return userService.getById(userId);
    }

    @Named("getPostById")
    Post getPostById(Long postId) throws ConflictException {
        return postService.getById(postId);
    }

}