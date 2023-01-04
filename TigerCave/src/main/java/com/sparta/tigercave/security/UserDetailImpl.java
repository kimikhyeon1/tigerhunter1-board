package com.sparta.tigercave.security;

import com.sparta.tigercave.entity.User;
import com.sparta.tigercave.entity.UserRoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailImpl implements UserDetails {

    private final User user;

    private final Long userId;
    private final String username;

    private final String password;

    public UserDetailImpl(User user, Long userId, String username, String password){
        this.user = user;
        this.userId = userId;
        this.username = username;
        this.password = password;
    }


    public User users(){
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        UserRoleEnum roleEnum = user.getRole();
        String authority = roleEnum.getAuthority();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    public Long getUserId() {
        return this.userId;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
