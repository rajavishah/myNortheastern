package com.example.unimanagement.model;

public enum UserRole {
    ADMIN,
    STUDENT,
    FACULTY;

    UserRole() {

    }

    @Override
    public String toString() {
        return "UserRole{}";
    }
}
