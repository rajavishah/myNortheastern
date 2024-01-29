package com.example.unimanagement.model;


import jakarta.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crn")
    private int crn;

    @Column(name = "course_number")
    private String courseNumber;

    @Column(name = "course_title")
    private String title;

    @Column(name = "course_term")
    private String term;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    private String timings;

    @ManyToMany(mappedBy = "myCourses", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Student> registeredStudents;

    public int getCrn() {
        return crn;
    }

    public void setCrn(int crn) {
        this.crn = crn;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public Set<Student> getRegisteredStudents() {
        return registeredStudents;
    }

    public void setRegisteredStudents(Set<Student> registeredStudents) {
        this.registeredStudents = registeredStudents;
    }

    @Override
    public String toString() {
        return "Course{" +
                "crn=" + crn +
                ", courseNumber='" + courseNumber + '\'' +
                ", title='" + title + '\'' +
                ", term='" + term + '\'' +
                ", faculty=" + faculty +
                ", timings='" + timings + '\'' +
                ", registeredStudents=" + getStudentIds() +
                '}';
    }

    private Set<Integer> getStudentIds() {
        if (registeredStudents != null) {
            return registeredStudents.stream()
                    .map(Student::getUserId)
                    .collect(Collectors.toSet());
        }
        return null;
    }

    public Course(int crn, String courseNumber, String title, String term, Faculty faculty, String timings, Set<Student> registeredStudents) {
        this.crn = crn;
        this.courseNumber = courseNumber;
        this.title = title;
        this.term = term;
        this.faculty = faculty;
        this.timings = timings;
        this.registeredStudents = registeredStudents;
    }

    public Course() {
    }


}
