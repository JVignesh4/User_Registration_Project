package com.bridgelabz.userregistration.service;

import com.bridgelabz.userregistration.dto.ChangePasswordDTO;
import com.bridgelabz.userregistration.dto.LoginDto;
import com.bridgelabz.userregistration.dto.UserRegistrationDto;
import com.bridgelabz.userregistration.model.UserData;

import java.util.List;

public interface IUserService {
     String registerUser(UserRegistrationDto userDto);

     UserData userLogin(LoginDto logindto);

     Boolean userVerification(String token);


     UserData updateUser(int id, UserRegistrationDto userDto);

     UserData changePassword(ChangePasswordDTO changePassword);


}
