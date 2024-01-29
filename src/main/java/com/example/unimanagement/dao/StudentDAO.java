package com.example.unimanagement.dao;

import com.example.unimanagement.model.Course;
import com.example.unimanagement.model.Student;

public interface StudentDAO {

    public void save(Student student);

    public Student getStudentById(int studentId);

    public void dropCourse(Student student, Course course);

    public void registerCourse(Student student, Course course);
}
