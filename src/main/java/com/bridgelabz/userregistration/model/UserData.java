package com.bridgelabz.userregistration.model;

import com.bridgelabz.userregistration.dto.UserRegistrationDto;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "User_Data")
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private String confirmPassword;

    public UserData(int id, UserRegistrationDto userDto) {
        this.userId = id;
        this.firstName = userDto.getFirstName();
        this.lastName = userDto.getLastName();
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
        this.confirmPassword = userDto.getConfirmPassword();

    }

    public UserData(UserRegistrationDto userDto) {
        this.firstName = userDto.getFirstName();
        this.lastName = userDto.getLastName();
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
        this.confirmPassword = userDto.getConfirmPassword();
    }
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("userId=").append(userId);
        sb.append(", name='").append(firstName).append('\'');
        sb.append(", dept='").append(lastName);
        sb.append('}');
        return sb.toString();
    }

    public UserData() {

    }

}


