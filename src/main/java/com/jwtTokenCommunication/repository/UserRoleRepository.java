package com.jwtTokenCommunication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jwtTokenCommunication.entity.User;
import com.jwtTokenCommunication.entity.UserRole;

public interface UserRoleRepository  extends JpaRepository<UserRole, Long> {
	UserRole findByuser(User user);
}
