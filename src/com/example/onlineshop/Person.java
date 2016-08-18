package com.example.onlineshop;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class Person implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * 
     */

    @Pattern(regexp = "[A-Za-z]*", message = "First name can be letters only")
    private String firstName;
    @Pattern(regexp = "[A-Za-z]*", message = "Last name can be letters only")
    private String lastName;
    @Email(message = "Enter valid email")
    private String emailId;
    @Size(min = 5, max = 12, message = "PhoneNo length should be between 5 and 12")
    @Pattern(regexp = "[0-9]*", message = "Phone number can be numbers only")
    private String phoneNo;
    @Size(min = 5, max = 500, message = "Address length should be between 5 and 500")
    private String address;
    @Size(min = 5, max = 10, message = "Username size should be between 5 and 10")
    private String userName;
    @Size(min = 5, max = 10, message = "Password length should be between 5 and 10")
    private String password;
    private String confirmPassword;

    public Person() {
        this.userName = "";
        this.password = "";
        this.confirmPassword = "";
        this.firstName = "";
        this.lastName = "";

        this.emailId = "";
        this.phoneNo = "";
        this.address = "";

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
