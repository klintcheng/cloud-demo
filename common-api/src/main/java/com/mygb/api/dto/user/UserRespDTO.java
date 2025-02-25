package com.mygb.api.dto.user;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class UserRespDTO implements Serializable {

    private String nickname;
    private String email;
    private String phone;
    private LocalDateTime createdAt;

    private List<String> roles;

}
