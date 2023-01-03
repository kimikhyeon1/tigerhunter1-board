package com.sparta.tigercave.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.id.IdentifierGenerationException;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.SQLIntegrityConstraintViolationException;

<<<<<<< Updated upstream:TigerCave/src/main/java/com/sparta/tigercave/entity/User.java
@Entity(name="USERS")
@NoArgsConstructor
@Getter
public class User{
=======
@Entity(name = "users")
@NoArgsConstructor
@Getter

public class User extends Timestamped{
>>>>>>> Stashed changes:TigerCave/src/main/java/com/sparta/tigercave/entity/Users.java

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger user_id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

<<<<<<< Updated upstream:TigerCave/src/main/java/com/sparta/tigercave/entity/User.java
    public User(String username, String password, UserRoleEnum role){
=======
    public User(String username, String password, UsersRoleEnum role){
>>>>>>> Stashed changes:TigerCave/src/main/java/com/sparta/tigercave/entity/Users.java
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
