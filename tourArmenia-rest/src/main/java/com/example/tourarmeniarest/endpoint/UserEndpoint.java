package com.example.tourarmeniarest.endpoint;

import com.example.tourarmeniacommon.dto.CreateUserRequestDto;
import com.example.tourarmeniacommon.dto.UserAuthRequestDto;
import com.example.tourarmeniacommon.dto.UserAuthResponseDto;
import com.example.tourarmeniacommon.dto.UserDto;
import com.example.tourarmeniacommon.entity.User;
import com.example.tourarmeniacommon.entity.UserType;
import com.example.tourarmeniacommon.mapper.UserMapper;
import com.example.tourarmeniacommon.service.UserService;
import com.example.tourarmeniarest.util.JwtTokenUtil;
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
    private final JwtTokenUtil tokenUtil;


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

    @PostMapping("/auth")
    public ResponseEntity<UserAuthResponseDto> auth(@RequestBody UserAuthRequestDto userAuthRequestDto) {
        Optional<User> byEmail = userService.findByEmail(userAuthRequestDto.getEmail());
        if (byEmail.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = byEmail.get();
        if (!passwordEncoder.matches(userAuthRequestDto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = tokenUtil.generateToken(userAuthRequestDto.getEmail());
        return ResponseEntity.ok(new UserAuthResponseDto(token));
    }
}














