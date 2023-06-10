package com.example.tourisminarmenia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactUsController {
    @GetMapping("/contactUs")
    public String contactUsPage(){
        return "contactUs";
    }
}
