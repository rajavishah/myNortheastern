package com.example.unimanagement.service;

import com.example.unimanagement.model.Faculty;
import com.example.unimanagement.model.User;

import java.util.List;

public interface UserService {


    public void addUser(User user);

    public User getUserByEmail(String email);

    public User getUserByUserName(String email);

    public User getUserById(int id);

    public void deleteUserById(int id);

    public void deleteFacultyById(int id);

    public void updateUser(User user);

    //    Students Services
    public List<User> getAllStudents();

    public List<User> getAllFaculties();

    public Faculty getFacultyById(int id);



}