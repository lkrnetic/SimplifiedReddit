package com.example.SimplifiedReddit.mapper;

import com.example.SimplifiedReddit.dto.SubredditDTO;
import com.example.SimplifiedReddit.model.Subreddit;
import com.example.SimplifiedReddit.model.User;
import com.example.SimplifiedReddit.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = { UserService.class})
public abstract class SubredditMapper {
    @Autowired
    private UserService userService;

    @Mapping(target = "userId", source = "user.id")
    public abstract SubredditDTO subredditToSubredditDTO(Subreddit subreddit);

    @Mapping(target = "user", source = "userId")
    public abstract Subreddit subredditDTOtoSubreddit(SubredditDTO subredditDTO);

    User findUserById(Long userId) {
        return userService.findById(userId).get();
    }
}
