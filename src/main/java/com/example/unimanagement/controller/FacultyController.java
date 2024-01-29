package com.example.unimanagement.controller;

import com.example.unimanagement.model.User;
import com.example.unimanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/faculty")
public class FacultyController {
    @Autowired
    UserService userService;
    @GetMapping("/login")
    public String customLogin(Model model){
        model.addAttribute("title", "Student - MyNortheastern");
        System.out.println("Student Controller");
        return "/login";
    }

    @PostMapping("/signin")
    public String signIn(@RequestParam String username,
                         @RequestParam String password,
                         Model model) {

        System.out.println("login");

        User user = userService.getUserByEmail(username);

        System.out.println(user);

        if (user != null) { // Check if user with the provided email exists
            if ("FACULTY".equals(user.getRole().name())) {
                if (user.getPassword().equals(password)) {
                    return "redirect:/faculty/faculty_dashboard";
                }
            }
        }

        // If user does not exist or incorrect password, show an error message
        model.addAttribute("error", "Invalid Username or Password!");
        return "loginError";
    }


}
