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
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserEndpoint {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtTokenUtil tokenUtil;

// Endpoint for user registration.
// Registers a new user by creating a new User entity with the provided user details (from createUserRequestDto).
// Checks if a user with the same email already exists in the system, and if so, returns a bad request response indicating the registration failure.
// If the email is unique, a new user is created, and the user details are saved to the database.
// The password is hashed using passwordEncoder before saving.
// The userType is set to UserType.USER to indicate that this is a regular user registration.
// Returns a ResponseEntity containing the UserDto representation of the newly registered user if successful.
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody CreateUserRequestDto createUserRequestDto){
        Optional<User> byEmail = userService.findByEmail(createUserRequestDto.getEmail());
        if(byEmail.isPresent()){
            log.warn("Registration failed. User with email: {} already exists.", createUserRequestDto.getEmail());
            return ResponseEntity.badRequest().build();
        }
        User user = userMapper.map(createUserRequestDto);
        user.setPassword(passwordEncoder.encode(createUserRequestDto.getPassword()));
        user.setUserType(UserType.USER);
        userService.save(user);
        log.info("User registered successfully with email: {}", createUserRequestDto.getEmail());
        return ResponseEntity.ok(userMapper.mapToDto(user));
}

    /**
     * Endpoint for user authentication.
     * Authenticates a user by checking the provided user credentials (from userAuthRequestDto) against the database.
     * Checks if a user with the provided email exists in the system. If not, returns an UNAUTHORIZED response indicating authentication failure.
     * If the user is found, it verifies the provided password against the hashed password in the database.
     * If the password matches, a JWT token is generated for the user's email using tokenUtil.generateToken() and returned in the response.
     * The token can be used for subsequent authorized requests.
     * If the password doesn't match, returns an UNAUTHORIZED response indicating authentication failure.
     *
     * @param userAuthRequestDto The UserAuthRequestDto containing the user email and password for authentication.
     * @return ResponseEntity<UserAuthResponseDto> containing the JWT token in the UserAuthResponseDto if authentication is successful.
     */
    @PostMapping("/auth")
    public ResponseEntity<UserAuthResponseDto> auth(@RequestBody UserAuthRequestDto userAuthRequestDto) {
        Optional<User> byEmail = userService.findByEmail(userAuthRequestDto.getEmail());
        if (byEmail.isEmpty()) {
            log.warn("Authentication failed. User with email: {} not found.", userAuthRequestDto.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = byEmail.get();
        if (!passwordEncoder.matches(userAuthRequestDto.getPassword(), user.getPassword())) {
            log.warn("Authentication failed. Incorrect password for user with email: {}", userAuthRequestDto.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = tokenUtil.generateToken(userAuthRequestDto.getEmail());
        log.info("Authentication successful for user with email: {}", userAuthRequestDto.getEmail());
        return ResponseEntity.ok(new UserAuthResponseDto(token));
    }
}














