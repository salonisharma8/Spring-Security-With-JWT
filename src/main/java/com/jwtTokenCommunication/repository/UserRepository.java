package com.jwtTokenCommunication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jwtTokenCommunication.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
