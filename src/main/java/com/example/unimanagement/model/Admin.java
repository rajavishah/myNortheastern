package com.example.unimanagement.model;

import jakarta.persistence.Entity;

@Entity
public class Admin extends User{
    public Admin(long userId, String fName, String lName, String email, String password, UserRole role) {
        super(userId, fName, lName, email, password, role);
    }

    public Admin() {
    }

    @Override
    public String toString() {
        return "Admin{}";
    }


}
