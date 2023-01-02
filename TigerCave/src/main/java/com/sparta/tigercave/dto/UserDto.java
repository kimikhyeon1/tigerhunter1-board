package com.sparta.tigercave.dto;

import lombok.Getter;

import javax.validation.constraints.Pattern;

public class UserDto {
    @Getter
    public static class signUpDto{
        @Pattern(regexp = "[a-z0-9]{4,10}", message = "최소 4자 이상, 10자 이하 알파벳 소문자(a~z), 숫자(0~9)를 혼합하여 입력해주세요.")
        private String username;
        @Pattern(regexp = "[\\w0-9~!@#$%^&*()_+|<>?:{}]{8,15}", message = "최소 8자 이상, 15자 이하 알파벳 대소문자(a~z, A~Z), 숫자(0~9)를 혼합하여 입력해주세요.")
        private String password;
        private boolean admin = false;
        private String adminToken = "";

        public signUpDto(String username, String password){
            this.username = username;
            this.password = password;
        }
    }
}
