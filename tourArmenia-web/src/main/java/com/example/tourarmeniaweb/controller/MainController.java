package com.example.tourarmeniaweb.controller;

import com.example.tourarmeniacommon.entity.User;
import com.example.tourarmeniacommon.entity.UserType;
import com.example.tourarmeniaweb.security.CurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
@Slf4j
@Controller
public class MainController {
    @Value("${upload.image.path}")
    private String imageUploadPath;

    // Handles the root URL "/" and returns the view named "index".
    @GetMapping("/")
    public String main(){
        return "index";
    }

    // Handles the URL "/customLogin" and returns the view named "customLoginPage".
    @GetMapping("/customLogin")
    public String customLogin() {
        return "customLoginPage";
    }


    // Handles the URL "/customSuccessLogin" and takes the currently authenticated user as a parameter.
    // Based on the user's type (ADMIN or USER), redirects to the main page ("/").
    // If the user is not authenticated, it also redirects to the main page.
    @GetMapping("/customSuccessLogin")
    public String customSuccessLogin(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null) {
            User user = currentUser.getUser();
            if(user.getUserType() == UserType.ADMIN){
                log.info("Redirecting ADMIN user to the main page.");
                return "redirect:/";
            }else if(user.getUserType() == UserType.USER){
                log.info("Redirecting USER user to the main page.");
                return "redirect:/";
            }
        }
        log.info("Redirecting unknown user to the main page.");
        return "redirect:/";
    }


    // Handles the URL "/getImage" and returns an image in JPEG format as a byte array.
    // The image name is provided as a request parameter.
    @GetMapping(value = "/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("imageName") String imageName) throws IOException {
        File file = new File(imageUploadPath + imageName);
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            log.info("Image {} retrieved successfully.", imageName);
            return IOUtils.toByteArray(fis);
        }
        log.warn("Image file not found: {}", imageName);
        return null;
    }
}
