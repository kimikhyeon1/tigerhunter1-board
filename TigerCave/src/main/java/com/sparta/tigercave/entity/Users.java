package com.sparta.tigercave.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@NoArgsConstructor
@Getter
public class Users extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger user_id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UsersRoleEnum role;

    public Users(String username, String password, UsersRoleEnum role){
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public boolean checkUserName(String username){

        if(this.username.equals(username)){
            return true;
        }
        return false;
    }
}
