package com.example.SimplifiedReddit.mapper;

import com.example.SimplifiedReddit.dto.SubredditDTO;
import com.example.SimplifiedReddit.exception.ConflictException;
import com.example.SimplifiedReddit.model.Subreddit;
import com.example.SimplifiedReddit.model.User;
import com.example.SimplifiedReddit.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = { UserService.class})
public abstract class SubredditMapper {
    @Autowired
    private UserService userService;

    @Mapping(target = "userId", source = "user.id")
    public abstract SubredditDTO subredditToSubredditDTO(Subreddit subreddit);

    @Mapping(target = "user", source = "userId", qualifiedByName = "getUserById")
    public abstract Subreddit subredditDTOtoSubreddit(SubredditDTO subredditDTO);

    @Named("getUserById")
    User getUserById(Long userId) throws ConflictException {
        return userService.getById(userId);
    }
}
