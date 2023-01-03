package com.sparta.tigercave.repository;

import com.sparta.tigercave.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, BigInteger> {

    Optional<Users> findByUsername(String username);
}
