package com.bridgelabz.userregistration.dto;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

@Data
public class UserRegistrationDto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;

    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message = "FirstName is Invalid")
    private String firstName;


    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message = "LastName is Invalid")
    private String lastName;

    private String email;


    private String password;
    private String confirmPassword;
    public UserRegistrationDto(int userId, String firstName, String lastName, String email, String password, String confirmPassword) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

}