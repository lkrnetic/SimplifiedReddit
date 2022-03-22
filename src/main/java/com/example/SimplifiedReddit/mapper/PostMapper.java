package com.example.SimplifiedReddit.mapper;

import com.example.SimplifiedReddit.dto.PostDTO;
import com.example.SimplifiedReddit.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
    @Mapping(target = "userId", source = "id")
    public abstract PostDTO postToPostDTO(Post post);

    public abstract Post postDTOtoPost(PostDTO postDTO);

}
