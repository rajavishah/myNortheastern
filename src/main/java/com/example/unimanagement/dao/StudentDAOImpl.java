package com.example.unimanagement.dao;

import com.example.unimanagement.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class StudentDAO {

    private SessionFactory sessionFactory;
    public void saveStudent(Student student){
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(student);
    }
}
