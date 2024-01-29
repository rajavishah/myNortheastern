package com.example.unimanagement.service;

import com.example.unimanagement.dao.CourseDAO;
import com.example.unimanagement.dao.StudentDAO;
import com.example.unimanagement.model.Student;
import com.example.unimanagement.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    StudentDAO studentDAO;

    @Autowired
    CourseDAO courseDAO;

    @Override
    public void saveStudent(Student student) {
        studentDAO.save(student);
    }

    @Override
    public void registerCourse(int studentId, int courseId) {
        Student student = studentDAO.getStudentById(studentId);
        Course course = courseDAO.getCourseById(courseId);

        if (student != null && course != null) {
            student.getMyCourses().add(course);
            course.getRegisteredStudents().add(student);
//            studentDAO.registerCourse(student, course);
            studentDAO.save(student);
            courseDAO.save(course);
        } else {
            throw new IllegalArgumentException("Invalid student or course ID.");
        }


    }

    @Override
    public Student getStudentById(int userId) {
        return null;
    }

    @Override
    public void dropCourse(int userId, int courseId) {
        Student student = studentDAO.getStudentById(userId);
        Course course = courseDAO.getCourseById(courseId);

        System.out.println("Student: " + student);
        System.out.println("Course: " + course);

        if (student != null && course != null) {

            // Drop the course in the DAO layer
            studentDAO.dropCourse(student, course);

            System.out.println("After dropping course in DAO layer");
            System.out.println("----------------- Student's updated courses: " + student.getMyCourses());
            System.out.println("----- Course's updated registered students: " + course.getRegisteredStudents());

            // Fetch updated student and course from the database
            Student updatedStudent = studentDAO.getStudentById(userId);
            Course updatedCourse = courseDAO.getCourseById(courseId);

            System.out.println("__________________________After fetching updated student and course from the database");

            System.out.println("----------------- Student's updated courses: " + updatedStudent.getMyCourses());
            System.out.println("----- Course's updated registered students: " + updatedCourse.getRegisteredStudents());

        } else {
            throw new IllegalArgumentException("Invalid student or course ID.");
        }
    }
}
