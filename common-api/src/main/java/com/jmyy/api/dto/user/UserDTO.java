package com.jmyy.api.dto.user;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserDTO {

    // @NotEmpty(message = "nickname is required")
    private String nickname;
    private String email;
    private String phone;
    private String password;
    private LocalDateTime createdAt;
}
