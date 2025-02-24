package com.mygb.api.dto.user;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class UserDTO {

    // @NotEmpty(message = "nickname is required")
    private String nickname;
    private String email;
    private String phone;
    private String password;
    private LocalDateTime createdAt;

    private List<String> roles;

}
