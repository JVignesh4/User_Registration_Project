package com.bridgelabz.userregistration.service;


import com.bridgelabz.userregistration.dto.ChangePasswordDTO;
import com.bridgelabz.userregistration.dto.LoginDto;
import com.bridgelabz.userregistration.dto.UserRegistrationDto;
import com.bridgelabz.userregistration.exception.UserRegistrationException;
import com.bridgelabz.userregistration.model.UserData;
import com.bridgelabz.userregistration.repository.UserRepository;
import com.bridgelabz.userregistration.util.EmailSenderService;
import com.bridgelabz.userregistration.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService implements IUserService{


    @Autowired
    private UserRepository userRepo;
    @Autowired
    EmailSenderService mailService;
    @Autowired
    TokenUtil util;

    public String registerUser(UserRegistrationDto userDto) {
        UserData newUser = new UserData(userDto);
        userRepo.save(newUser);
        String token = util.createToken(newUser.getUserId());
        mailService.sendEmail(userDto.getEmail(), "Account Sign-up successfully", "Hello" + newUser.getFirstName() + " Your Account has been created.Your token is " + token + " Keep this token safe to access your account in future ");
        return token;
    }

    public UserData userLogin(LoginDto loginDto) {
        Optional<UserData> newUser = userRepo.findByEmail(loginDto.getEmail());
        if (loginDto.getEmail().equals(newUser.get().getEmail()) && loginDto.getPassword().equals(newUser.get().getPassword())) {
            log.info("SuccessFully Logged In");
            return newUser.get();
        } else {

            throw new UserRegistrationException("User doesn't exists");

        }
    }

    @Override
    public Boolean userVerification(String token) {
        int id = util.decodeToken(token);
        Optional<UserData> userPresent = userRepo.findById(id);
        if (userPresent.isPresent()) {
            mailService.sendEmail(userPresent.get().getEmail(), "Verification Email", "Your Account was verified!!  "
                    + userPresent.get().getFirstName() +" use this token: "+ token);
            return true;
        } else {
            System.out.println("Exception ...Token Not Found!");
            return false;
        }
    }

    @Override
    public UserData updateUser(int id, UserRegistrationDto userDto) {
        Optional<UserData> user = userRepo.findById(id);
        if (user.isEmpty()) {
            throw new UserRegistrationException("User Record doesn't exists");
        } else {
            UserData newUser = new UserData(id, userDto);
            userRepo.save(newUser);
            log.info("User data updated successfully");
            return newUser;
        }
    }

    @Override
    public UserData changePassword(ChangePasswordDTO changePassword) {
        Optional<UserData> user = userRepo.findByEmail(changePassword.getEmail());
        String generatedToken = util.createToken(user.get().getUserId());
        mailService.sendEmail(user.get().getEmail(), "Welcome " + user.get().getFirstName(), generatedToken);
        if (user.isEmpty()) {
            throw new UserRegistrationException("User doesn't exists");
        } else {
            if (changePassword.getToken().equals(generatedToken)) {
                user.get().setPassword(changePassword.getNewPassword());
                userRepo.save(user.get());
                log.info("Password changes successfully");
                return user.get();
            } else {
                throw new UserRegistrationException("Invalid token");
            }
        }
    }
}
