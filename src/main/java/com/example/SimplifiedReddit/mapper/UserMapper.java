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
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    Boolean enabled = true;

    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    public abstract UserDTO userToUserDTO(User user);

    @Mapping(target = "id", source = "userDTO.id")
    @Mapping(target = "email", source = "userDTO.email")
    @Mapping(target = "username", source = "userDTO.username")
    /*
    @Mapping(target = "userRole", expression = "java(returnUserRole())")
    @Mapping(target = "enabled", expression = "java(returnTrue())")
    @Mapping(target = "userRole", source = "userRole.USER")
    */
    @ValueMapping(target = "userRole", source = "userRole.USER")
    //@Mapping(target = "userRole", expression = "java(returnUserRole())")
    @Mapping(target = "enabled", constant  = "true")
    @Mapping(target = "password", expression = "java(encodePassword(userDTO))")
    public abstract User userDTOtoUser(UserDTO userDTO, UserRole userRole);

    String encodePassword(UserDTO userDTO) {
        return bCryptPasswordEncoder.encode(userDTO.getPassword());
    }

    Boolean returnTrue () {
        return true;
    }

    UserRole returnUserRole() {
        return UserRole.USER;
    }

}
