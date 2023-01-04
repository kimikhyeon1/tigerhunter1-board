package com.sparta.tigercave.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.id.IdentifierGenerationException;

import javax.persistence.*;

import java.sql.SQLIntegrityConstraintViolationException;

@Entity(name="USERS")
@NoArgsConstructor
@Getter
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_id;
    /* 데이터 타입이 BigInteger이라 DB에 id가 실수로 저장 됩니다. Long 타입으로 바꾸면 정수로 저장됩니다.-우시은
       컨트롤 + 쉬프트 + R로 사용되는 모든 타입을 한 번에 바꿀 수 있습니다.*/
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    public User(String username, String password, UserRoleEnum role){
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
