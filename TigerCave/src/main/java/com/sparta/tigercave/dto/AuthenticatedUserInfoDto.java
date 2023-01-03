package com.sparta.tigercave.dto;

import com.sparta.tigercave.entity.UsersRoleEnum;
import lombok.Getter;

@Getter
public class AuthenticatedUserInfoDto {
    private UsersRoleEnum usersRoleEnum;
    private String username;

    public AuthenticatedUserInfoDto(UsersRoleEnum usersRoleEnum, String username) {
        this.usersRoleEnum = usersRoleEnum;
        this.username = username;
    }
}
