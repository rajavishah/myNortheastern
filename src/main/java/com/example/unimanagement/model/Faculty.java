package com.example.unimanagement.model;

import java.util.Set;
import jakarta.persistence.*;
@Entity
//@Table(name = "faculty")
public class Faculty extends User{

    @OneToMany(mappedBy = "faculty", fetch = FetchType.EAGER)
    private Set<Course> coursesTeach;

    public Set<Course> getCoursesTeach() {
        return coursesTeach;
    }

    public void setCoursesTeach(Set<Course> coursesTeach) {
        this.coursesTeach = coursesTeach;
    }

    public Faculty(long userId, String fName, String lName, String email, String password, UserRole role, Set<Course> coursesTeach) {
        super(userId, fName, lName, email, password, role);
        this.coursesTeach = coursesTeach;
    }

    public Faculty(Set<Course> coursesTeach) {
        this.coursesTeach = coursesTeach;
    }

    public Faculty(long userId, String fName, String lName, String email, String password, UserRole role) {
        super(userId, fName, lName, email, password, role);
    }

    public Faculty() {

    }
}
