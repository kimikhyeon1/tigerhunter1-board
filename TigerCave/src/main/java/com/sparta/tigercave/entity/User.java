package com.sparta.tigercave.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.id.IdentifierGenerationException;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.SQLIntegrityConstraintViolationException;

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
    /* public Users(String username, String password, UsersRoleEnum role) throws SQLIntegrityConstraintViolationException {
         this.username = username;
         this.password = password;
         this.role = role;
         if (username == null) {
             throw new IdentifierGenerationException("아이디를 입력하세요.");
         }
         if (password == null) {
             throw new SQLIntegrityConstraintViolationException("비밀번호를 입력하세요.");
         }
     }*/
    public boolean checkUserName(String username){

        if(this.username.equals(username)){
            return true;
        }
        return false;
    }
}
