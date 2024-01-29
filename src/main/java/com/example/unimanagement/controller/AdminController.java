package com.example.unimanagement.controller;

import com.example.unimanagement.helper.Message;
import com.example.unimanagement.model.Admin;
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

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @RequestMapping("/dashboard")
    public String dashboard(HttpServletRequest request, Model model, Principal principal) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.ADMIN)) {
                userService.getUserByUserName("shah.raja@northeastern.edu");
                System.out.println("In admin Dashboard PostMapping");
                return "admin/dashboard";
            }
            else{
                System.out.println("Inside else - Login as Admin - to view Admin Dashboard!");
                return "/login";
            }
        }
        return "/login";
    }

    @GetMapping("/login")
    public String customLogin(HttpServletRequest request, Model model) {

        model.addAttribute("title", "Admin - MyNortheastern");
        System.out.println("Admin Controller");
        return "/login";
    }

    @PostMapping("/signin")
    public String signIn(@RequestParam String username,
                         @RequestParam String password,
                         Model model) {

        System.out.println("Admin Login");

        User user = userService.getUserByEmail(username);

        System.out.println(user);

        if (user != null) { // Check if user with the provided email exists
            if ("ADMIN".equals(user.getRole().name())) {
                if (user.getPassword().equals(password)) {
                    return "redirect:/admin/dashboard";
                }
            }
        }

        // If user does not exist or incorrect password, show an error message
        model.addAttribute("error", "Invalid Username or Password!");
        return "loginError";
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboardLanding(HttpServletRequest request, Model model) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.ADMIN)) {

                model.addAttribute("title", "Admin - MyNortheastern");
                ModelAndView mv = new ModelAndView("admin/admin_dashboard");
                mv.addObject("admin", request.getSession().getAttribute("user"));
                System.out.println("Admin " + request.getSession().getAttribute("user"));
                System.out.println("Admin Controller - Get Mapping Dashboard");
                return mv;

            } else {
                System.out.println("Inside else - Login as Admin - to view AdminDashboard!");
                return new ModelAndView("/login");
            }
        }
        return new ModelAndView("/login");
    }

    @PostMapping("/dashboard")
    public String handleDashboardPost(HttpServletRequest request, Model model) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.ADMIN)) {
                return "redirect:/admin/dashboard";
            } else{
                System.out.println("Inside else - Login as Admin - to view AdminDashboard!");
                return "redirect:/login";
            }
        }
        return "redirect:/login";

    }



    @GetMapping("/manage/student/add")
    public ModelAndView openAddStudentForm(HttpServletRequest request, Model model) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.ADMIN)) {
                System.out.println("Inside Get Mapping - Add Student Form");
                model.addAttribute("title", "Add New Student");
                ModelAndView mv = new ModelAndView("admin/add_student_form");
                mv.addObject("admin", request.getSession().getAttribute("user"));
                mv.addObject("student", new Student());
                return mv;
            } else {
                System.out.println("Inside else - Login as Admin - to add student!");
                return new ModelAndView("/login");
            }
        }
        return new ModelAndView("/login");
    }


    @PostMapping("/manage/student/add")
    public String addStudent(HttpServletRequest request, @ModelAttribute("student") Student student, Model model, HttpSession session) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.ADMIN)) {
                try {
                    System.out.println("Inside Post Mapping - Add Student Form");
                    student.setRole(UserRole.STUDENT);
                    // Save the student to the database
                    userService.addUser(student);
                    // Message Success
                    session.setAttribute("message", new Message("Student Added Successfully!", "success"));

                } catch (Exception e) {
                    System.out.println("Error " + e.getMessage());
                    e.printStackTrace();
                    // Message Error
                    session.setAttribute("message", new Message("Something went wrong, Try Again!", "danger"));

                }
            }
            else{
                System.out.println("Inside else - Login as Admin - to add student!");
                return "redirect:/login";
            }
        }

        // Redirect to the student list page or another appropriate page
        return "redirect:/admin/manage/student/add";
    }

//    View All Students
//    Per Page - 5[n]

    @GetMapping("/manage/student/viewall")
    public ModelAndView viewAllStudents(HttpServletRequest request, Model model) {
        System.out.println("Inside Get Mapping - View All Students");
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.ADMIN)) {
                model.addAttribute("title", "View All Students");
                ModelAndView mv = new ModelAndView("admin/view_all_students");

                mv.addObject("admin", request.getSession().getAttribute("user"));
                System.out.println("Before calling userService.getAllStudents()");
                List<User> students = userService.getAllStudents();
                System.out.println("After calling userService.getAllStudents()");
                mv.addObject("student", students);

                return mv;
            }
            else{
                System.out.println("Inside else - Login as Admin - to view all students!");
                return new ModelAndView("/login");
            }
        }
        return new ModelAndView("/login");
    }

    @PostMapping("/manage/student/viewall")
    public String viewAllStudents(HttpServletRequest request, @ModelAttribute("student") Student student, HttpSession session) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.ADMIN)) {
                try {
                    System.out.println("Inside Post Mapping - View All Students");
                } catch (Exception e) {
                    System.out.println("Error " + e.getMessage());
                    e.printStackTrace();

                }
                return "redirect:/admin/manage/student/view";
            } else {
                System.out.println("Inside else - Login as Admin - to view all students!");
                return "redirect:/login";
            }
        }
        return "redirect:/login";
    }

    //    Showing One Student Details
    @GetMapping("/manage/student/{userId}")
    public ModelAndView showStudentDetail(@PathVariable("userId") Integer userId, HttpServletRequest request, Model model) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.ADMIN)) {
                System.out.println("Inside Get Mapping - Specific Student Details");
                model.addAttribute("title", "Specific Student Detail");
                User student = userService.getUserById(userId);
                ModelAndView mv = new ModelAndView("admin/specific_student_detail");
                mv.addObject("admin", request.getSession().getAttribute("user"));
                mv.addObject("student", student);
                return mv;
            } else {    //    Login as Admin - to view Specific Student Details!
                System.out.println("Inside else - Login as Admin - to view Specific student Details!");
                return new ModelAndView("/login");
            }
        }
        return new ModelAndView("/login");
    }

    //    Delete Student Handler
    @GetMapping("/manage/student/delete/{userId}")
    public ModelAndView deleteStudentProfile(@PathVariable("userId") Integer userId, Model model, HttpServletRequest request, HttpSession session) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.ADMIN)) {
                System.out.println("Inside Get Mapping - Delete Specific Student");
                model.addAttribute("title", "Delete Student");
                userService.deleteUserById(userId);
                ModelAndView mv = new ModelAndView("admin/view_all_students");
                List<User> students = userService.getAllStudents();
                mv.addObject("admin", request.getSession().getAttribute("user"));
                mv.addObject("student", students);
                session.setAttribute("message", new Message("Student Deleted Successfully!", "success"));
                return mv;
            } else {
                System.out.println("Inside else - Login as Admin - to delete student!");
                return new ModelAndView("/login");
            }
        }
        return new ModelAndView("/login");
    }


    //    Update Student Handler
    @GetMapping("/manage/student/update/{userId}")
    public ModelAndView updateStudent(@ModelAttribute("student") Student student, @PathVariable("userId") Integer userId, HttpServletRequest request, HttpSession session) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.ADMIN)) {
                System.out.println("Inside Post Mapping - Update Specific Student Details");
                ModelAndView mv = new ModelAndView("admin/update_student_details");
                mv.addObject("title", "Update Student");
                student.setRole(UserRole.STUDENT);

                User user1 = userService.getUserById(userId);
                mv.addObject("admin", request.getSession().getAttribute("user"));
                mv.addObject("student", user1);
        //      userService.updateUser(student);
        //      session.setAttribute("message", new Message("Student Updated Successfully!", "success"));
                return mv;
            } else {
                System.out.println("Inside else - Login as Admin - to update student!");
                return new ModelAndView("/login");
            }
        }
        return new ModelAndView("/login");
    }

    @RequestMapping(value = "/manage/student/update", method = RequestMethod.POST)
    public String updateStudentForm(@ModelAttribute("student") Student student, HttpSession session, HttpServletRequest request) {
        System.out.println("Inside Post Mapping after Clicking update Button - Update Specific Student Details");
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            if (user.getRole().equals(UserRole.ADMIN)) {
                try {
                    student.setRole(UserRole.STUDENT);
                    userService.updateUser(student);
                    session.setAttribute("message", new Message("Student Updated Successfully!", "success"));
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            return "redirect:/admin/manage/student/viewall";
        } else{
//            session.setAttribute("message", new Message("Login as Admin - to update!", "danger"));
            System.out.println("Login as Admin - to update!");
            return "redirect:/login";
        }


    }

//    Admin Profile
    @GetMapping("/profile")
    public ModelAndView profile(@ModelAttribute("student") Student student, HttpSession session, HttpServletRequest request) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.ADMIN)) {
                ModelAndView mv = new ModelAndView("admin/profile");
                mv.addObject("admin", request.getSession().getAttribute("user"));
                mv.addObject("title", "Admin Profile Page");
                return mv;
            } else {
                System.out.println("Inside else - Login as Admin - to view Admin Profile!");
                return new ModelAndView("/login");
            }
        }
        return new ModelAndView("/login");
    }

//    Admin Profile Update

    @GetMapping("/profile/update/{userId}")
    public ModelAndView updateAdmin(@ModelAttribute("admin") Admin admin, @PathVariable("userId") Integer userId, HttpServletRequest request, HttpSession session) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.ADMIN)) {
                System.out.println("Inside Post Mapping - Update Admin");
                ModelAndView mv = new ModelAndView("admin/update_admin_details");
                mv.addObject("title", "Update Admin");
                admin.setRole(UserRole.ADMIN);

                User user1 = userService.getUserById(userId);
                mv.addObject("admin", request.getSession().getAttribute("user"));
                mv.addObject("student", user1);
//              userService.updateUser(student);
//              session.setAttribute("message", new Message("Student Updated Successfully!", "success"));
                return mv;
            } else {
                System.out.println("Inside else - Login as Admin - to update Admin!");
                return new ModelAndView("/login");
            }
        }
        return new ModelAndView("/login");

    }
    @PostMapping(value = "/profile/update")
    public String updateAdminProfile(@ModelAttribute("admin") Admin admin, HttpSession session, HttpServletRequest request) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.ADMIN)) {

                System.out.println("Inside Post Mapping after Clicking update Button - Update Admin Details");
                try {
                    admin.setRole(UserRole.ADMIN);
                    userService.updateUser(admin);
                    session.setAttribute("message", new Message("Admin Details Updated Successfully!", "success"));
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                return "redirect:/admin/profile";
                } else{
                    System.out.println("Inside else - Login as Admin - to update Admin Profile!");
                    return "redirect:/login";
                }
        }
        return "redirect:/login";

    }


}