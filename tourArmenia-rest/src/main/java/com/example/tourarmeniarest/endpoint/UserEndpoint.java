package com.example.tourarmeniarest.endpoint;

import com.example.tourarmeniacommon.dto.CreateUserRequestDto;
import com.example.tourarmeniacommon.dto.UserDto;
import com.example.tourarmeniacommon.entity.User;
import com.example.tourarmeniacommon.entity.UserType;
import com.example.tourarmeniacommon.mapper.UserMapper;
import com.example.tourarmeniacommon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserEndpoint {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody CreateUserRequestDto createUserRequestDto){
        Optional<User> byEmail = userService.findByEmail(createUserRequestDto.getEmail());
        if(byEmail.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        User user = userMapper.map(createUserRequestDto);
        user.setPassword(passwordEncoder.encode(createUserRequestDto.getPassword()));
        user.setUserType(UserType.USER);
        userService.save(user);
        return ResponseEntity.ok(userMapper.mapToDto(user));
}
}














