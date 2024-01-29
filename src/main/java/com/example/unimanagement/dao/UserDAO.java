package com.example.unimanagement.dao;

import com.example.unimanagement.model.Faculty;
import com.example.unimanagement.model.User;

import java.util.List;

public interface UserDAO {

    public void save(User user);

    public User getByEmail(String username);

    public List<User> getAllStudents();

    public User getById(int id);

    public void deleteById(int id);

    //    Delete faculty by id
    public void deleteFacultyById(int id);

    public void update(User user);

    public List<User> getAllFaculties();

    public Faculty getFacultyById(int id);
}
