package com.example.unimanagement.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
//@Table(name = "student")
public class Student extends User{

    @ManyToMany
    @JoinTable(
            name = "course_like",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "crn"))
    private Set<Course> myCourses;

    @Column(name = "grades")
    private int grades;

    public Set<Course> getMyCourses() {
        return myCourses;
    }

    public void setMyCourses(Set<Course> myCourses) {
        this.myCourses = myCourses;
    }

    public int getGrades() {
        return grades;
    }

    public void setGrades(int grades) {
        this.grades = grades;
    }

    @Override
    public String toString() {
        return "Student{" +
                "myCourses=" + myCourses +
                ", grades=" + grades +
                '}';
    }

    public Student(long userId, String fName, String lName, String email, String password, UserRole role, Set<Course> myCourses, int grades) {
        super(userId, fName, lName, email, password, role);
        this.myCourses = myCourses;
        this.grades = grades;
    }

    public Student(Set<Course> myCourses, int grades) {
        this.myCourses = myCourses;
        this.grades = grades;
    }

    public Student() {
    }
}
