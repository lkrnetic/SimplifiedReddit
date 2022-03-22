package com.example.SimplifiedReddit.mapper;

import com.example.SimplifiedReddit.dto.UserDTO;
import com.example.SimplifiedReddit.model.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public abstract class UserMapper {
    public abstract UserDTO userToUserDTO(User user);

    public abstract User userDTOtoUser(UserDTO userDTO);
}
