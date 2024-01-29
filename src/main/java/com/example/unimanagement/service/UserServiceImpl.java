package com.example.unimanagement.service;

import com.example.unimanagement.dao.UserDAO;
import com.example.unimanagement.model.Faculty;
import com.example.unimanagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserDAO userDAO;

    @Override
    public void addUser(User user) { userDAO.save(user); }

    @Override
    public User getUserByEmail(String email) {
        return userDAO.getByEmail(email);
    }

    @Override
    public User getUserByUserName(String email) {
        return userDAO.getByEmail(email);
    }

    @Override
    public List<User> getAllStudents() {
        return userDAO.getAllStudents();
    }

    @Override
    public User getUserById(int id) {
        return userDAO.getById(id);
    }

    @Override
    public void deleteUserById(int id) {
        userDAO.deleteById(id);
    }

    @Override
    public void deleteFacultyById(int id) {
        userDAO.deleteFacultyById(id);
    }

    @Override
    public void updateUser(User user){
        userDAO.update(user);
    }

    @Override
    public List<User> getAllFaculties() {
        return userDAO.getAllFaculties();
    }

    @Override
    public Faculty getFacultyById(int id) {
        return userDAO.getFacultyById(id);
    }

}
