package com.example.unimanagement.service;

import com.example.unimanagement.model.Course;
import com.example.unimanagement.model.User;

import java.util.List;

public interface CourseService {
    public void addCourse(Course course);

    public List<Course> getAllCourses();

    public Course getCourseById(Integer id);

    public void deleteCourseById(Integer crn);

    public void updateCourse(Course course);
}
