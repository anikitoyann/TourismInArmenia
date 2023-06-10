package com.example.tourisminarmenia.controller;

import com.example.tourisminarmenia.entity.User;
import com.example.tourisminarmenia.respository.RegionsRepository;
import com.example.tourisminarmenia.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/regions")
public class RegionsController {
private final RegionsRepository regionsRepository;
private  final UserRepository userRepository;

    @GetMapping
    public String regionPage(ModelMap modelMap) {
   List<User>list=userRepository.findAll();
   modelMap.addAttribute("users",list);
        modelMap.addAttribute("regions",regionsRepository.findAll());
        return "region";
    }

    @GetMapping("/remove")
    public String removeHotels(@RequestParam("id") int id) {
        regionsRepository.deleteById(id);
        return "redirect:/regions";
    }

}
