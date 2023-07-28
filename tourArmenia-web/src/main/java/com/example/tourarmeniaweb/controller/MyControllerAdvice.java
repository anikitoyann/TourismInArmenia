package com.example.tourarmeniaweb.controller;
import com.example.tourarmeniacommon.entity.User;
import com.example.tourarmeniaweb.security.CurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
@Slf4j
@ControllerAdvice
public class MyControllerAdvice {

    // This method is annotated with @ModelAttribute.
    // It adds the current user (if authenticated) to the model with the attribute name "currentUser".
    @ModelAttribute("currentUser")
    public User currentUser(@AuthenticationPrincipal CurrentUser currentUser){
        if(currentUser!=null){
            //log.info("Found authenticated user");
           return currentUser.getUser();
        }
        log.info("No authenticated user found.");

        return null;
    }
}
