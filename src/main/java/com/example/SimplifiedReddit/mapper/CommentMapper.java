package com.example.SimplifiedReddit.mapper;

import com.example.SimplifiedReddit.dto.CommentDTO;
import com.example.SimplifiedReddit.dto.PostDTO;
import com.example.SimplifiedReddit.model.Comment;
import com.example.SimplifiedReddit.model.Post;
import com.example.SimplifiedReddit.model.User;
import com.example.SimplifiedReddit.service.PostService;
import com.example.SimplifiedReddit.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = { UserService.class, PostService.class })
public abstract class CommentMapper {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @Mapping(target = "postId", source = "post.id")
    @Mapping(target = "userId", source = "user.id")
    public abstract CommentDTO commentToCommentDTO(Comment comment);

    @Mapping(target = "user", source = "commentDTO.userId")
    @Mapping(target = "post", source = "commentDTO.postId")
    public abstract Comment commentDTOtoComment(CommentDTO commentDTO);

    User findUserById(Long userId) {
        return userService.findById(userId).get();
    }
    Post findPostById(Long postId) {
        return postService.findById(postId).get();
    }

}
