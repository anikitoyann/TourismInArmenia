package com.example.tourarmeniacommon.mapper;

import com.example.tourarmeniacommon.dto.CreateUserRequestDto;
import com.example.tourarmeniacommon.dto.UserDto;
import com.example.tourarmeniacommon.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User map(CreateUserRequestDto dto);

    UserDto mapToDto(User entity);

}