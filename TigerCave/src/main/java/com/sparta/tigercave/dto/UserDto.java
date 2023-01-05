package com.sparta.tigercave.dto;

import com.sparta.tigercave.entity.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Pattern;

public class UserDto {
    @Getter
    @NoArgsConstructor
    public static class signUpRequestDto{
        @Pattern(regexp = "[a-z0-9]{4,10}", message = "최소 4자 이상, 10자 이하 알파벳 소문자(a~z), 숫자(0~9)를 혼합하여 입력해주세요.")
        private String username;
        @Pattern(regexp = "[\\w0-9~!@#$%^&*()_+|<>?:{}]{8,15}", message = "최소 8자 이상, 15자 이하 알파벳 대소문자(a~z, A~Z), 숫자(0~9)를 혼합하여 입력해주세요.")
        private String password;
        private boolean admin = false;

        private String adminToken="";

        public signUpRequestDto(String username, String password){
            this.username = username;
            this.password = password;
        }
    }

    @Getter
    public static class loginResponseDto{
        private String username;

        private String password;

        private UserRoleEnum role;

        public loginResponseDto(String username, String password, UserRoleEnum role){
            this.username = username;
            this.password = password;
            this.role = role;
        }
    }
}
