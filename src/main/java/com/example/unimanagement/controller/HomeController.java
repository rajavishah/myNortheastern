package com.example.unimanagement.controller;

import com.example.unimanagement.dao.UserDAOImpl;
import com.example.unimanagement.helper.Message;
import com.example.unimanagement.model.User;
import com.example.unimanagement.model.UserRole;
import com.example.unimanagement.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.awt.*;
import java.util.Objects;

@Controller
public class HomeController {

//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String home(Model model){
        model.addAttribute("title", "MyNortheastern");
        return "home";
    }

    @RequestMapping("/about")
    public String about(Model model){
        model.addAttribute("title", "About : Uni-Management");
        return "about";
    }

    @RequestMapping("/signup")
    public String signup(Model model){
        model.addAttribute("title", "Register");
        model.addAttribute("user", new User());
        return "signup";
    }

//    Handler For Registering User
    @RequestMapping(value = "/do_register", method = RequestMethod.POST)
//    @RequestParam(value = "role", defaultValue = "Student")
    public String registerUser(@ModelAttribute("user") User user, Model model, HttpSession session){

        try{
            user.setRole(UserRole.ADMIN);
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
            System.out.println("User " + user);
            System.out.println("User Id " + user.getUserId());

            System.out.println("User role name " + user.getRole().name());
//            model.addAttribute("user", new User());
//            session.setAttribute("message", new Message("Successfully Registered!", "alert-success"));
            userService.addUser(user);

        } catch(Exception e){
            model.addAttribute("user", user);
            session.setAttribute("message", new Message("Something went wrong " + e.getMessage(), "alert-danger"));
            e.printStackTrace();
        }
        return "signup";
    }

}
