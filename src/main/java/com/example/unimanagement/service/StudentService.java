package com.example.unimanagement.service;

import com.example.unimanagement.model.Student;

public interface StudentService {

    public void saveStudent(Student student);

    public void registerCourse(int studentId, int courseId);

    public Student getStudentById(int userId);

    public void dropCourse(int userId, int courseId);
}
