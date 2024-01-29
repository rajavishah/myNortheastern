package com.example.unimanagement.controller;


import com.example.unimanagement.helper.Message;
import com.example.unimanagement.model.Faculty;
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

import java.util.List;

@Controller
@RequestMapping("/admin/manage/faculty")
public class AdminFacultyController {

    @Autowired
    private UserService userService;

    @GetMapping("/viewall")
    public ModelAndView viewAllFaculties(HttpServletRequest request, Model model) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.ADMIN)) {

                System.out.println("Inside Get Mapping - View All Faculties");
                model.addAttribute("title", "View All Faculties");
                ModelAndView mv = new ModelAndView("admin/view_all_faculties");

                mv.addObject("admin", request.getSession().getAttribute("user"));
                System.out.println("Before calling userService.getAllFaculties()");
                List<User> faculties = userService.getAllFaculties();
                System.out.println("After calling userService.getAllFaculties()");
                mv.addObject("faculty", faculties);

                return mv;
            } else {
                System.out.println("Login As Admin - Access Denied - to View All Faculties");
                return new ModelAndView("redirect:/login");
            }
        }
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/add")
    public ModelAndView openAddFacultyForm(HttpServletRequest request, Model model) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.ADMIN)) {

                System.out.println("Inside Get Mapping - Add Faculty Form");
                model.addAttribute("title", "Add New Faculty");
                ModelAndView mv = new ModelAndView("admin/add_faculty_form");
                mv.addObject("admin", request.getSession().getAttribute("user"));
                mv.addObject("faculty", new Faculty());
                return mv;
            } else {
                System.out.println("Login As Admin - Access Denied - to Add Faculty Form");
                return new ModelAndView("redirect:/login");
            }
        }
        return new ModelAndView("redirect:/login");
    }


    @PostMapping("/add")
    public String addFaculty(HttpServletRequest request, @ModelAttribute("faculty") Faculty faculty, Model model, HttpSession session) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.ADMIN)) {

                try {
                    System.out.println("Inside Post Mapping - Add Faculty Form");
                    faculty.setRole(UserRole.FACULTY);
                    // Save the faculty to the database
                    userService.addUser(faculty);
                    // Message Success
                    session.setAttribute("message", new Message("Faculty Added Successfully!", "success"));

                } catch (Exception e) {
                    System.out.println("Error " + e.getMessage());
                    e.printStackTrace();
                    // Message Error
                    session.setAttribute("message", new Message("Something went wrong, Try Again!", "danger"));

                }

                // Redirect to the faculty add page
                return "redirect:/admin/manage/faculty/add";

            } else {
                System.out.println("Login As Admin - Access Denied - to Add Faculty Form");
                return "redirect:/login";
            }
        }
        return "redirect:/login";
    }

    //    Showing One Faculty Details
    @GetMapping("/{userId}")
    public ModelAndView showFacultyDetail(@PathVariable("userId") Integer userId, HttpServletRequest request, Model model) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.ADMIN)) {

                System.out.println("Inside Get Mapping - Specific Faculty Details");
                model.addAttribute("title", "Specific Faculty Detail");
                User faculty = userService.getUserById(userId);
                ModelAndView mv = new ModelAndView("admin/specific_faculty_detail");
                mv.addObject("admin", request.getSession().getAttribute("user"));
                mv.addObject("faculty", faculty);
                return mv;
            } else {
                System.out.println("Login As Admin - Access Denied - to Specific Faculty Details");
                return new ModelAndView("redirect:/login");
            }
        }
        return new ModelAndView("redirect:/login");
    }

    //    Delete Faculty Handler
    @GetMapping("/delete/{userId}")
    public ModelAndView deleteFacultyProfile(@PathVariable("userId") Integer userId, Model model, HttpServletRequest request, HttpSession session) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.ADMIN)) {

                System.out.println("Inside Get Mapping - Delete Specific Faculty");
                model.addAttribute("title", "Delete Faculty");
                userService.deleteFacultyById(userId);
                ModelAndView mv = new ModelAndView("admin/view_all_faculties");
                List<User> faculties = userService.getAllFaculties();
                mv.addObject("admin", request.getSession().getAttribute("user"));
                mv.addObject("faculty", faculties);
                session.setAttribute("message", new Message("Faculty Deleted Successfully!", "success"));
                return mv;
            } else {
                System.out.println("Login As Admin - Access Denied - to Delete Specific Faculty");
                return new ModelAndView("redirect:/login");
            }
        }
        return new ModelAndView("redirect:/login");
    }


    //    Update Faculty Handler
    @GetMapping("/update/{userId}")
    public ModelAndView updateFaculty(@ModelAttribute("faculty") Faculty faculty, @PathVariable("userId") Integer userId, HttpServletRequest request, HttpSession session) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.ADMIN)) {

                System.out.println("Inside Post Mapping - Update Specific Faculty Details");
                ModelAndView mv = new ModelAndView("admin/update_faculty_details");
                mv.addObject("title", "Update Faculty");
                faculty.setRole(UserRole.FACULTY);

                User user1 = userService.getUserById(userId);
                mv.addObject("admin", request.getSession().getAttribute("user"));
                mv.addObject("faculty", user1);
                //        userService.updateUser(faculty);
                //        session.setAttribute("message", new Message("Faculty Updated Successfully!", "success"));
                return mv;
            } else {
                System.out.println("Login As Admin - Access Denied - to Update Specific Faculty Details");
                return new ModelAndView("redirect:/login");
            }
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateFacultyForm(@ModelAttribute("faculty") Faculty faculty, HttpSession session, HttpServletRequest request) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.ADMIN)) {

                System.out.println("Inside Post Mapping after Clicking update Button - Update Specific Faculty Details");
                try {
                    faculty.setRole(UserRole.FACULTY);
                    userService.updateUser(faculty);
                    session.setAttribute("message", new Message("Faculty Details Updated Successfully!", "success"));
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                return "redirect:/admin/dashboard";
            } else {
                System.out.println("Login As Admin - Access Denied - to Update Specific Faculty Details");
                return "redirect:/login";
            }
        }
        return "redirect:/login";
    }

}
