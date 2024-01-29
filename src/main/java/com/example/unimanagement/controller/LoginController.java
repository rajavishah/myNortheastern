package com.example.unimanagement.controller;

import com.example.unimanagement.model.Student;
import com.example.unimanagement.model.User;
import com.example.unimanagement.model.UserRole;
import com.example.unimanagement.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;


@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String customLogin(Model model){
        model.addAttribute("title", "MyNortheastern");
        System.out.println("Login Controller - Get Mapping");
        return "login";
    }

    @PostMapping("/signin")
    public String signIn(@RequestParam String username,
                         @RequestParam String password,
                         Model model, HttpServletRequest request) {

        System.out.println("General login");

        User user = userService.getUserByEmail(username);

        System.out.println(user);

        if (user != null) { // Check if user with the provided email exists
            if ("ADMIN".equals(user.getRole().name())) {
                if (user.getPassword().equals(password)) {
                    request.getSession().setAttribute("user", user);
                    System.out.println("User coming - in Admin!");
                    return "redirect:/admin/dashboard";
                }
            } else if ("STUDENT".equals(user.getRole().name())) {
                if (user.getPassword().equals(password)) {
                    request.getSession().setAttribute("user", user);
                    System.out.println("User coming - in Student!");
                    return "redirect:/student/dashboard";
                }
            } else if ("FACULTY".equals(user.getRole().name())) {
                if (user.getPassword().equals(password)) {
                    System.out.println("User coming - in Faculty!");
                    return "redirect:/faculty/dashboard";
                }
            }
        } else{
            System.out.println("User not coming!");
        }

        // If user does not exist or incorrect password, show an error message
        model.addAttribute("error", "Invalid Username or Password!");
        return "loginError";
    }


}
