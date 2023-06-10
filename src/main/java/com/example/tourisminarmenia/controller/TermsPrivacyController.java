package com.example.tourisminarmenia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TermsPrivacyController {
    @GetMapping("/TermsPrivacy")
    public String TermsPrivacyUsPage(){
        return "TermsPrivacy";
    }
}
