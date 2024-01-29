package com.example.unimanagement.model;

public enum UserRole {
    Admin,
    Student,
    Faculty;

    UserRole() {
    }

    @Override
    public String toString() {
        return "UserRole{}";
    }
}
