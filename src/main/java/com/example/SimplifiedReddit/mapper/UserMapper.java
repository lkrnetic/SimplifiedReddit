package com.example.SimplifiedReddit.mapper;

import com.example.SimplifiedReddit.dto.UserDTO;

import com.example.SimplifiedReddit.exception.ConflictException;
import com.example.SimplifiedReddit.model.Subreddit;
import com.example.SimplifiedReddit.model.SubredditFollower;
import com.example.SimplifiedReddit.model.User;
import com.example.SimplifiedReddit.service.SubredditFollowerService;
import com.example.SimplifiedReddit.service.SubredditService;
import com.example.SimplifiedReddit.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {SubredditFollowerService.class })
public abstract class UserMapper {
    @Autowired
    private SubredditFollowerService subredditFollowerService;

    public abstract UserDTO userToUserDTO(User user);

    @Mapping(target="followedSubreddits", source="id", qualifiedByName = "getFollowedSubredditsByUserById")
    public abstract User userDTOtoUser(UserDTO userDTO);

    @Named("getFollowedSubredditsByUserById")
    List<Subreddit> getFollowedSubredditsByUserById(Long userId) throws ConflictException {
        List<Subreddit> followedSubreddits = subredditFollowerService.findAllByUserId(userId)
                .stream()
                .map(followedSubreddit -> followedSubreddit.getSubreddit())
                .collect(Collectors.toList());

        return followedSubreddits;
    }

}
