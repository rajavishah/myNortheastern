package com.example.unimanagement.service;

import com.example.unimanagement.dao.CourseDAO;
import com.example.unimanagement.dao.UserDAO;
import com.example.unimanagement.model.Course;
import com.example.unimanagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    CourseDAO courseDAO;

    @Override
    public void addCourse(Course course) { courseDAO.save(course); }

    @Override
    public List<Course> getAllCourses() {
        return courseDAO.getAllCourses();
    }

    @Override
    public void deleteCourseById(Integer crn) {
        courseDAO.deleteById(crn);
    }

    @Override
    public void updateCourse(Course course) {
        courseDAO.updateCourse(course);
    }

    @Override
    public Course getCourseById(Integer id) {
        return courseDAO.getCourseById(id);
    }


}
