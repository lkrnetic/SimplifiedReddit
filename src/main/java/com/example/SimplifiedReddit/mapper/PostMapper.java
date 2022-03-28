package com.example.SimplifiedReddit.mapper;

import com.example.SimplifiedReddit.dto.PostDTO;
import com.example.SimplifiedReddit.model.Post;
import com.example.SimplifiedReddit.model.Subreddit;
import com.example.SimplifiedReddit.model.User;
import com.example.SimplifiedReddit.repository.SubredditRepository;
import com.example.SimplifiedReddit.repository.UserRepository;
import com.example.SimplifiedReddit.service.SubredditService;
import com.example.SimplifiedReddit.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = { UserService.class, SubredditService.class })
public abstract class PostMapper {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubredditRepository subredditRepository;

    @Mapping(target = "subredditId", source = "subreddit.id")
    @Mapping(target = "userId", source = "user.id")
    public abstract PostDTO postToPostDTO(Post post);

    @Mapping(target = "user", source = "userId")
    @Mapping(target = "subreddit", source = "subredditId")
    public abstract Post postDTOtoPost(PostDTO postDTO);

    User findUserById(Long userId) {
        return userRepository.findById(userId).get();
    }
    Subreddit findSubredditById(Long subredditId) {
        return subredditRepository.findById(subredditId).get();
    }
}
