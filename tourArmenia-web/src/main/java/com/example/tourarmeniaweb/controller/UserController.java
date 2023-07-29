package com.example.tourarmeniaweb.controller;

import com.example.tourarmeniacommon.entity.User;
import com.example.tourarmeniacommon.entity.UserType;
import com.example.tourarmeniacommon.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    // Handles the HTTP GET request for "/register" and returns the "register" view.
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }


    // Handles the HTTP POST request for "/register" with user registration data.
    // It receives the user registration data as a User object (using @ModelAttribute).
    @PostMapping("/register")
    public String register(@ModelAttribute @Valid User user) {
        Optional<User> userFromDB = userService.findByEmail(user.getEmail());
        if (userFromDB.isEmpty()) {
            String password = user.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
            user.setUserType(UserType.USER);
            userService.save(user);
            log.info("User registered successfully with email: {}", user.getEmail());
        }else {
            log.warn("User with email: {} already exists. Registration request rejected.", user.getEmail());
        }
        return "redirect:/";
    }
}
