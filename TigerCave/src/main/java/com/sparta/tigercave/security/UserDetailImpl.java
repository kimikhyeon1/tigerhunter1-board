package com.sparta.tigercave.security;

import com.sparta.tigercave.entity.Users;
import com.sparta.tigercave.entity.UsersRoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailImpl implements UserDetails {

    private final Users users;

    private final String username;
    private final String password;

    public UserDetailImpl(Users users, String username, String password){
        this.users = users;
        this.username = username;
        this.password = password;
    }

    public Users users(){
        return users;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        UsersRoleEnum roleEnum = users.getRole();
        String authority = roleEnum.getAuthority();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
