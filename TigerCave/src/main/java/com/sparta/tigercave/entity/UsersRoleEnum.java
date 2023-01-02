package com.sparta.tigercave.entity;
public enum UsersRoleEnum {
    USER(Authority.USER), ADMIN(Authority.ADMIN);

    private final String authority;

    UsersRoleEnum(String authority){this.authority = authority;}

    public String getAuthority(){
        return this.authority;
    }

    public static class Authority{
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
    }
}
