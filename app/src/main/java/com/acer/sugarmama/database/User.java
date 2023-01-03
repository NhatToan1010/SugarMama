package com.acer.sugarmama.database;

public class User {
    String fullName, email, password, phoneNo, address;

    public User(){}

    public User(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public User(String fullName, String email, String password, String phoneNo) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;
    }

    public User(String fullName, String email, String password, String phoneNo, String address) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
