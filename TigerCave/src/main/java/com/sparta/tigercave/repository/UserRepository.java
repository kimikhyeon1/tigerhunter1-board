package com.sparta.tigercave.repository;

import com.sparta.tigercave.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, BigInteger> {

    Optional<User> findByUsername(String username);
}
