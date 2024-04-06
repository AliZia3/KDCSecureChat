//https://www.youtube.com/watch?v=M8sKwoVjqU0
package com.example.chatapp.Frontend;

import androidx.annotation.NonNull;

public class Users {
    String userId, name, password, email, position, department;

    public Users(){}

    public Users(String userId, String name, String email, String password, String position, String department){
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.position = position;
        this.department = department;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "id='" + userId + '\'' +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", position='" + position + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
