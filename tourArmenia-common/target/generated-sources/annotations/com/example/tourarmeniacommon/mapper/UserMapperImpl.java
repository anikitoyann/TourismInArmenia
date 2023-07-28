package com.example.tourarmeniacommon.mapper;

import com.example.tourarmeniacommon.dto.CreateUserRequestDto;
import com.example.tourarmeniacommon.dto.UserDto;
import com.example.tourarmeniacommon.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-28T17:00:50+0400",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.2 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User map(CreateUserRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setName( dto.getName() );
        user.setSurname( dto.getSurname() );
        user.setEmail( dto.getEmail() );
        user.setAddress( dto.getAddress() );
        user.setPassword( dto.getPassword() );
        user.setPhoneNumber( dto.getPhoneNumber() );

        return user;
    }

    @Override
    public UserDto mapToDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( entity.getId() );
        userDto.setName( entity.getName() );
        userDto.setSurname( entity.getSurname() );
        userDto.setEmail( entity.getEmail() );
        userDto.setAddress( entity.getAddress() );
        userDto.setUserType( entity.getUserType() );
        userDto.setPhoneNumber( entity.getPhoneNumber() );

        return userDto;
    }
}
