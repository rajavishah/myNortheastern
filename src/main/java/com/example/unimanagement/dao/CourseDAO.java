package com.example.unimanagement.dao;

import com.example.unimanagement.model.Course;
import com.example.unimanagement.model.User;

import java.util.List;

public interface CourseDAO {

    public void save(Course course);

    public List<Course> getAllCourses();

    public Course getCourseById(int id);

    public void deleteById(int crn);

    public void updateCourse(Course course);
}
