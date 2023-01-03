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
<<<<<<< HEAD

    private final String username;
    private final String password;

    public UserDetailImpl(Users users, String username, String password){
        this.users = users;
        this.username = username;
        this.password = password;
    }

    public Users users(){
=======
    private final String username;

    public UserDetailImpl(Users users, String username) {
        this.users = users;
        this.username = username;
    }

    public Users getUser() {
>>>>>>> origin/develop
        return users;
    }

    @Override
<<<<<<< HEAD
    public Collection<? extends GrantedAuthority> getAuthorities(){
        UsersRoleEnum roleEnum = users.getRole();
        String authority = roleEnum.getAuthority();
=======
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UsersRoleEnum role = users.getRole();
        String authority = role.getAuthority();
>>>>>>> origin/develop

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
<<<<<<< HEAD
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
=======
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
>>>>>>> origin/develop
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
