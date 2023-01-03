package com.sparta.tigercave.security;

import com.sparta.tigercave.entity.User;
<<<<<<< Updated upstream
import com.sparta.tigercave.entity.UserRoleEnum;
=======
import com.sparta.tigercave.entity.UsersRoleEnum;
>>>>>>> Stashed changes
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

public class UserDetailImpl implements UserDetails {

    private final User user;

    private final BigInteger userId;
    private final String username;

    private final String password;

<<<<<<< Updated upstream
    public UserDetailImpl(User user, BigInteger userId, String username, String password){
        this.user = user;
        this.userId = userId;
=======
    public UserDetailImpl(User user, String username, String password){
        this.user = user;
>>>>>>> Stashed changes
        this.username = username;
        this.password = password;
    }

<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
    public User users(){
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
<<<<<<< Updated upstream
        UserRoleEnum roleEnum = user.getRole();
=======
        UsersRoleEnum roleEnum = user.getRole();
>>>>>>> Stashed changes
        String authority = roleEnum.getAuthority();


        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
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
