package com.sparta.tigercave.dto;

import com.sparta.tigercave.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class AuthenticatedUserInfoDto {
    private UserRoleEnum userRoleEnum;
    private String username;

    public AuthenticatedUserInfoDto(UserRoleEnum userRoleEnum, String username) {
        this.userRoleEnum = userRoleEnum;
        this.username = username;
    }
}
