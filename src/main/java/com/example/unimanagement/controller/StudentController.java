package com.example.unimanagement.controller;

import com.example.unimanagement.dao.StudentDAOImpl;
import com.example.unimanagement.helper.Message;
import com.example.unimanagement.model.Student;
import com.example.unimanagement.model.User;
import com.example.unimanagement.model.UserRole;
import com.example.unimanagement.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/student")
public class StudentController {

    private StudentDAOImpl studentDAO;

    @Autowired
    UserService userService;

//    @Autowired
//    StudentService studentService;

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
            if ("STUDENT".equals(user.getRole().name())) {
                if (user.getPassword().equals(password)) {
                    return "redirect:/student/student_dashboard";
                }
            } else{
                model.addAttribute("error", "Invalid Username or Password!");
                return "loginError";
            }
        }

        // If user does not exist or incorrect password, show an error message
        model.addAttribute("error", "Invalid Username or Password!");
        return "loginError";
    }

//    Base Dashboard for Student
    @GetMapping("/dashboard")
    public ModelAndView dashboardLanding(HttpServletRequest request, Model model) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.STUDENT)) {

                model.addAttribute("title", "Student - MyNortheastern");
                ModelAndView mv = new ModelAndView("student/student_dashboard");
                mv.addObject("student", request.getSession().getAttribute("user"));
                System.out.println("Student " + request.getSession().getAttribute("user"));
                System.out.println("Student Controller - Get Mapping Dashboard");
                return mv;
            } else {
                System.out.println("Login As Student - Access Denied - to Student Dashboard");
                return new ModelAndView("redirect:/login");
            }
        }
        return new ModelAndView("redirect:/login");
    }


//    Profile related mappings
    @GetMapping("/profile")
    public ModelAndView profile(@ModelAttribute("student") Student student, HttpSession session, HttpServletRequest request) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.STUDENT)) {

                System.out.println("Inside Get Mapping - Student Profile");
                ModelAndView mv = new ModelAndView("student/profile");
                mv.addObject("student", request.getSession().getAttribute("user"));
                mv.addObject("title", "Student Profile Page");
                return mv;
            } else {
                System.out.println("Login As Student - Access Denied - to Student Profile");
                return new ModelAndView("redirect:/login");
            }
        }
        return new ModelAndView("redirect:/login");
    }


//    Student Profile Update

    @GetMapping("/profile/update/{userId}")
    public ModelAndView updateStudent(@ModelAttribute("student") Student student, @PathVariable("userId") Integer userId, HttpServletRequest request, HttpSession session) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.STUDENT)) {

                System.out.println("Inside Post Mapping - Update Student Details");
                ModelAndView mv = new ModelAndView("student/update_student_details");
                mv.addObject("title", "Update Student Details");
                student.setRole(UserRole.STUDENT);

                User user1 = userService.getUserById(userId);
                mv.addObject("student", request.getSession().getAttribute("user"));
                mv.addObject("student", user1);
                //        userService.updateUser(student);
                //        session.setAttribute("message", new Message("Student Updated Successfully!", "success"));
                return mv;
            } else {
                System.out.println("Login As Student - Access Denied - to Update Student Details");
                return new ModelAndView("redirect:/login");
            }
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(value = "/profile/update", method = RequestMethod.POST)
    public String updateStudentProfile(@ModelAttribute("student") Student student, HttpSession session, HttpServletRequest request) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.STUDENT)) {

                System.out.println("Inside Post Mapping after Clicking update Button - Update Student Details");
                try {
                    student.setRole(UserRole.STUDENT);
                    userService.updateUser(student);
                    session.setAttribute("message", new Message("Student Details Updated Successfully!", "success"));
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                return "redirect:/student/profile";
            } else {
                System.out.println("Login As Student - Access Denied - to Update Student Details");
                return "redirect:/login";
            }
        }
        return "redirect:/login";
    }

}


