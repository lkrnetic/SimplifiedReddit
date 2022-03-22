package com.example.SimplifiedReddit.mapper;

import com.example.SimplifiedReddit.dto.UserDTO;
import com.example.SimplifiedReddit.model.User;
import com.example.SimplifiedReddit.model.enums.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    public abstract UserDTO userToUserDTO(User user);

    public abstract User userDTOtoUser(UserDTO userDTO);
}
