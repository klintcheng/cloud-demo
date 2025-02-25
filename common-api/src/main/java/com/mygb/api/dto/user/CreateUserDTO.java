package com.mygb.api.dto.user;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserDTO implements Serializable {

    @NotBlank(message = "nickname is required")
    private String nickname;
    @NotBlank(message = "email is required")
    private String email;
    private String phone;
    private String password;

}