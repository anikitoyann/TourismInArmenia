package com.example.tourarmeniaweb.security;

import com.example.tourarmeniacommon.entity.User;
import org.springframework.security.core.authority.AuthorityUtils;
public class CurrentUser extends org.springframework.security.core.userdetails.User{
private User user;


    // Constructor for creating a CurrentUser object using the User object from the application domain.
    public CurrentUser(User user) {
        // Call the super constructor of org.springframework.security.core.userdetails.User
        // to initialize the user's authentication details.
        super(user.getEmail(),
                user.getPassword(),
                AuthorityUtils.createAuthorityList(user.getUserType().name()));
        this.user=user;
    }

    public User getUser(){
        return user;
    }
}
