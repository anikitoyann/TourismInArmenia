package com.example.tourarmeniaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TermsPrivacyController {

    // Handler for HTTP GET requests to "/TermsPrivacy" endpoint
    @GetMapping("/TermsPrivacy")
    public String TermsPrivacyUsPage(){
        return "TermsPrivacy";
    }
}
