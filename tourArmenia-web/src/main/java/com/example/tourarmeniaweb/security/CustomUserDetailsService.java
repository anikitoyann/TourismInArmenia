package com.example.tourarmeniaweb.security;

import com.example.tourarmeniacommon.entity.User;
import com.example.tourarmeniacommon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    // This method is implemented from the UserDetailsService interface.
    // It loads user details based on the provided username (email) during the authentication process.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byEmail = userRepository.findByEmail(username);
        if(byEmail.isEmpty()){
            throw  new UsernameNotFoundException("User does not exist");
        }
        return  new CurrentUser(byEmail.get());
    }
}
