package com.example.SimplifiedReddit.mapper;

import com.example.SimplifiedReddit.dto.PostDTO;
import com.example.SimplifiedReddit.model.Post;
import com.example.SimplifiedReddit.model.User;
import com.example.SimplifiedReddit.repository.UserRepository;
import com.example.SimplifiedReddit.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = { UserService.class })
public abstract class PostMapper {
    @Autowired
    private UserRepository userRepository;

    @Mapping(target = "userId", source = "user.id")
    public abstract PostDTO postToPostDTO(Post post);

    @Mapping(target = "user", source = "userId")
    public abstract Post postDTOtoPost(PostDTO postDTO);

    User findById(Long userId) {
        return userRepository.findById(userId).get();
    }

}
