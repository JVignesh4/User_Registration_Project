package com.bridgelabz.userregistration.dto;

import lombok.Data;

@Data
public class ChangePasswordDTO {

    private String email;
    private String newPassword;
    private String confirmNewPassword;
    private String token;
}
