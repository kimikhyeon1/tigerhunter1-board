package com.sparta.tigercave.dto;

import com.sparta.tigercave.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class AuthenticatedUserInfoDto {
    private UserRoleEnum usersRoleEnum;
    private String username;

    public AuthenticatedUserInfoDto(UserRoleEnum usersRoleEnum, String username) {
        this.usersRoleEnum = usersRoleEnum;
        this.username = username;
    }
}
