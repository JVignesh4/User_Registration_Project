package com.bridgelabz.userregistration.controller;

import com.bridgelabz.userregistration.dto.ChangePasswordDTO;
import com.bridgelabz.userregistration.dto.LoginDto;
import com.bridgelabz.userregistration.dto.ResponseDto;
import com.bridgelabz.userregistration.dto.UserRegistrationDto;
import com.bridgelabz.userregistration.service.IUserService;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> registerUser(@Valid @RequestBody UserRegistrationDto userDto) {
        ResponseDto dto = new ResponseDto("User Record created successfully !", userService.registerUser(userDto));
        return new ResponseEntity(dto, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseDto> userLogin(@Valid @RequestBody LoginDto logindto) {
        ResponseDto dto = new ResponseDto("User logged in successfully !", userService.userLogin(logindto));
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @GetMapping("/login/{token}")
    public ResponseEntity<ResponseDto> verifyUser(@PathVariable String token) {
        ResponseDto dto = new ResponseDto("User Verified successfully !", userService.userVerification(token));
        return new ResponseEntity(dto, HttpStatus.OK);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDto> updateRecord(@PathVariable Integer id, @Valid @RequestBody UserRegistrationDto userDto) {
        ResponseDto dto = new ResponseDto("Record updated successfully !", userService.updateUser(id, userDto));
        return new ResponseEntity(dto, HttpStatus.ACCEPTED);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<ResponseDto> changePassword(@Valid @RequestBody ChangePasswordDTO changePassword) {
        ResponseDto dto = new ResponseDto("Password Changed successfully !", userService.changePassword(changePassword));
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @GetMapping("/load")
    public BatchStatus load() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Map<String, JobParameter> maps = new HashMap<>();
        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters parameters = new JobParameters(maps);
        JobExecution jobExecution = jobLauncher.run(job, parameters);

        System.out.println("JobExecution: " + jobExecution.getStatus());

        System.out.println("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }

        return jobExecution.getStatus();
    }

}