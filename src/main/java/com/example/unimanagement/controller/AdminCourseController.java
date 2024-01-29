package com.example.unimanagement.controller;


import com.example.unimanagement.helper.Message;
import com.example.unimanagement.model.Course;
import com.example.unimanagement.model.Faculty;
import com.example.unimanagement.model.User;
import com.example.unimanagement.model.UserRole;
import com.example.unimanagement.service.CourseService;
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
@RequestMapping("/admin/manage/course")
public class AdminCourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @GetMapping("/add")
    public ModelAndView openAddCourseForm(HttpServletRequest request, Model model) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.ADMIN)) {

                System.out.println("Inside Get Mapping - Add Course Form");
                model.addAttribute("title", "Add New Course");
                ModelAndView mv = new ModelAndView("admin/add_course_form");
                mv.addObject("admin", request.getSession().getAttribute("user"));
                List<User> facultyList = userService.getAllFaculties();
                request.setAttribute("facultyList", facultyList);
                mv.addObject("course", new Course());
                return mv;
            } else {
                System.out.println("Login as Admin - Access Denied - to Add Course Form");
                return new ModelAndView("redirect:/login");
            }
        }
        return new ModelAndView("redirect:/login");
    }


    @PostMapping("/add")
    public String addCourse(HttpServletRequest request, @ModelAttribute("course") Course course, Model model, HttpSession session, @RequestParam("facultyId") Integer facultyId) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.ADMIN)) {

                try {
                    System.out.println("Inside Post Mapping - Add Course Form");
                    // Save the Course to the database
                    System.out.println(course);
                    System.out.println(facultyId);
                    Faculty fac = (Faculty) userService.getUserById(facultyId);
                    course.setFaculty(fac);

                    courseService.addCourse(course);
                    // Message Success
                    session.setAttribute("message", new Message("Course Added Successfully!", "success"));

                } catch (Exception e) {
                    System.out.println("Error " + e.getMessage());
                    e.printStackTrace();
                    // Message Error
                    session.setAttribute("message", new Message("Something went wrong, Try Again!", "danger"));

                }

                // Redirect to the Course add page
                return "redirect:/admin/manage/course/add";
            } else {
                System.out.println("Login as Admin - Access Denied - to Add Course Form");
                return "redirect:/login";
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/viewall")
    public ModelAndView viewAllCourses(HttpServletRequest request, Model model) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.ADMIN)) {
                System.out.println("Inside Get Mapping - View All Courses");
                model.addAttribute("title", "View All Courses");
                ModelAndView mv = new ModelAndView("admin/view_all_courses");
                mv.addObject("admin", request.getSession().getAttribute("user"));
                System.out.println("Before calling userService.getAllCourses()");
                List<Course> courses = courseService.getAllCourses();

                System.out.println("After calling userService.getAllCourses()");
                mv.addObject("course", courses);
                return mv;
            } else {
                System.out.println("Login as Admin - Access Denied - to View All Courses");
                return new ModelAndView("redirect:/login");
            }
        }
        return new ModelAndView("redirect:/login");
    }

//    Show Specific Course
    @GetMapping("/{crn}")
    public ModelAndView showCourseDetail(@PathVariable("crn") Integer crn, HttpServletRequest request, Model model) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.ADMIN)) {

                System.out.println("Inside Get Mapping - Specific Course Details");
                model.addAttribute("title", "Specific Course Detail");
                Course course = courseService.getCourseById(crn);
                ModelAndView mv = new ModelAndView("admin/specific_course_detail");
                mv.addObject("admin", request.getSession().getAttribute("user"));
                mv.addObject("course", course);
                return mv;
            } else {
                System.out.println("Login as Admin - Access Denied - to Specific Course Details");
                return new ModelAndView("redirect:/login");
            }
        }
        return new ModelAndView("redirect:/login");
    }

    //    Delete Course Handler
    @GetMapping("/delete/{crn}")
    public ModelAndView deleteCourse(@PathVariable("crn") Integer crn, Model model, HttpServletRequest request, HttpSession session) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.ADMIN)) {

                System.out.println("Inside Get Mapping - Delete Specific Course");
                model.addAttribute("title", "Delete Course");
                courseService.deleteCourseById(crn);
                ModelAndView mv = new ModelAndView("admin/view_all_courses");
                List<Course> courses = courseService.getAllCourses();
                mv.addObject("admin", request.getSession().getAttribute("user"));
                mv.addObject("course", courses);
                session.setAttribute("message", new Message("Course Deleted Successfully!", "success"));
                return mv;
            } else {
                System.out.println("Login as Admin - Access Denied - to Delete Specific Course");
                return new ModelAndView("redirect:/login");
            }
        }
        return new ModelAndView("redirect:/login");
    }

    //    Update Course Handler
    @GetMapping("/update/{crn}")
    public ModelAndView updateCourse(@ModelAttribute("course") Course course, @PathVariable("crn") Integer crn, HttpServletRequest request, HttpSession session) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.ADMIN)) {

                System.out.println("Inside Post Mapping - Update Specific Course Details");
                ModelAndView mv = new ModelAndView("admin/update_course_details");
                mv.addObject("title", "Update Course");
                //        faculty.setRole(UserRole.FACULTY);
                List<User> facultyList = userService.getAllFaculties();
                request.setAttribute("facultyList", facultyList);
                Course courses = courseService.getCourseById(crn);
                mv.addObject("admin", request.getSession().getAttribute("user"));
                mv.addObject("course", courses);
                //        userService.updateUser(faculty);
                //        session.setAttribute("message", new Message("Faculty Updated Successfully!", "success"));
                return mv;
            } else {
                System.out.println("Login as Admin - Access Denied - to Update Specific Course Details");
                return new ModelAndView("redirect:/login");
            }
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateFacultyForm(@ModelAttribute("course") Course course, HttpSession session, HttpServletRequest request, @RequestParam("facultyId") Integer facultyId) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.ADMIN)) {

                System.out.println("Inside Post Mapping after Clicking update Button - Update Specific Course Details");
                try {
                    Faculty fac = (Faculty) userService.getUserById(facultyId);
                    course.setFaculty(fac);

                    //            course.setRegisteredStudents();
                    courseService.updateCourse(course);
                    session.setAttribute("message", new Message("Course Details Updated Successfully!", "success"));
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                return "redirect:/admin/dashboard";
            } else {
                System.out.println("Login as Admin - Access Denied - to Update Specific Course Details");
                return "redirect:/login";
            }
        }
        return "redirect:/login";
    }


}
