package com.example.unimanagement.controller;

import com.example.unimanagement.helper.Message;
import com.example.unimanagement.model.Course;
import com.example.unimanagement.model.Student;
import com.example.unimanagement.model.User;
import com.example.unimanagement.model.UserRole;
import com.example.unimanagement.service.CourseService;
import com.example.unimanagement.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/student/manage/course")
public class StudentCourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    StudentService studentService;

    @GetMapping("/viewall")
    public ModelAndView viewAllCourses(HttpServletRequest request, Model model) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.STUDENT)) {

                System.out.println("Inside Student Get Mapping - View All Courses");
                model.addAttribute("title", "View All Courses");
                ModelAndView mv = new ModelAndView("student/view_all_courses");
                mv.addObject("student", request.getSession().getAttribute("user"));
                System.out.println("Before calling courseService.getAllCourses()");
                List<Course> courses = courseService.getAllCourses();
                System.out.println("After calling courseService.getAllCourses()");
                mv.addObject("course", courses);
                return mv;
            } else {
                System.out.println("Login As Student - Access Denied - to View All Courses");
                return new ModelAndView("redirect:/login");
            }
        }
        return new ModelAndView("redirect:/login");
    }

//    Specific Course Details
    @GetMapping("/{crn}")
    public ModelAndView showCourseDetail(@PathVariable("crn") Integer crn, HttpServletRequest request, Model model) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.STUDENT)) {

                System.out.println("Inside Get Mapping - Specific Course Details");
                model.addAttribute("title", "Specific Course Detail");
                Course course = courseService.getCourseById(crn);
                ModelAndView mv = new ModelAndView("student/specific_course_detail");
                mv.addObject("student", request.getSession().getAttribute("user"));
                mv.addObject("course", course);
                return mv;
            } else {
                System.out.println("Login As Student - Access Denied - to Specific Course Details");
                return new ModelAndView("redirect:/login");
            }
        }
        return new ModelAndView("redirect:/login");
    }



    @GetMapping("/register/{crn}")
    public ModelAndView registerCourse(@PathVariable Integer crn, HttpServletRequest request, Model model) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.STUDENT)) {

                System.out.println("Inside Student Post Mapping - Register Course");

                // Retrieve the student from the session
                Student student = (Student) request.getSession().getAttribute("user");

                System.out.println("Student: " + student);

                // Check if the student is logged in
                if (student == null) {
                    // Redirect to the login page or handle the case where the user is not logged in
                    return new ModelAndView("redirect:/login");
                }

                // Retrieve the course by ID
                Course course = courseService.getCourseById(crn);


                // Check if the student is already registered for the course
                if (student.getMyCourses().contains(course)) {
                    // Case where the student is already registered for the course
                    System.out.println("Student already registered for the course");
                    return new ModelAndView("redirect:/error");
                }

                studentService.registerCourse(student.getUserId(), course.getCrn());
                System.out.println("Student: " + student);
                // Redirect
                return new ModelAndView("redirect:/student/manage/course/viewall");
            } else {
                System.out.println("Login As Student - Access Denied - to Register Course");
                return new ModelAndView("redirect:/login");
            }
        }
        return new ModelAndView("redirect:/login");
    }

    // GET mapping to display registered courses
    @GetMapping("/update/{userId}")
    public ModelAndView showRegisteredCourses(@PathVariable int userId, HttpServletRequest request, HttpSession session) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.STUDENT)) {

                // Fetch student by userId
                Student student = studentService.getStudentById(userId);

                ModelAndView modelAndView = new ModelAndView("student/update_courses");
                modelAndView.addObject("student", request.getSession().getAttribute("user"));
                // Fetch all courses for the student
                Set<Course> registeredCourses = student.getMyCourses();

                modelAndView.addObject("student", student);
                modelAndView.addObject("registeredCourses", registeredCourses);
                session.setAttribute("message", new Message("Course Registered Successfully!", "success"));

                return modelAndView;
            } else {
                System.out.println("Login As Student - Access Denied - to Update Courses");
                return new ModelAndView("redirect:/login");
            }
        }
        return new ModelAndView("redirect:/login");
    }

    // POST mapping to handle course dropping
    @GetMapping("/drop/{userId}/{crn}")
    public ModelAndView dropCourseGet(@PathVariable int crn, @PathVariable int userId, HttpServletRequest request, HttpSession session) {
        Object userObject = request.getSession().getAttribute("user");

        if (userObject instanceof User) {
            User user = (User) userObject;
            System.out.println("User whole object: " + user);
            if (user.getRole().equals(UserRole.STUDENT)) {

                System.out.println("Inside Get Mapping - Drop Specific Course");
                studentService.dropCourse(userId, crn);
                List<Course> courses = courseService.getAllCourses();
                ModelAndView mv = new ModelAndView("student/profile");

                mv.addObject("student", request.getSession().getAttribute("user"));

                mv.addObject("course", courses);
                mv.addObject("title", "Drop Course");
                session.setAttribute("message", new Message("Course Dropped Successfully!", "success"));
                return mv;
            } else {
                System.out.println("Login As Student - Access Denied - to Drop Specific Course");
                return new ModelAndView("redirect:/login");
            }
        }
        return new ModelAndView("redirect:/login");
    }


}
