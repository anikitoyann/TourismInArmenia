package com.example.tourarmeniaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutUsController {
    @GetMapping("/aboutUs")
    public String aboutUsPage(){
        return "AboutUs";
    }
}
