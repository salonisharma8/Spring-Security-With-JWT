package com.jwtTokenCommunication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.jwtTokenCommunication.entity.LogInUser;
import com.jwtTokenCommunication.entity.User;
import com.jwtTokenCommunication.entity.UserRole;
import com.jwtTokenCommunication.repository.UserRepository;
import com.jwtTokenCommunication.repository.UserRoleRepository;


@Service
public class LogInUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
	private UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User 404 Not Found");
		}
		
        UserRole userRole = userRoleRepository.findByuser(user);
        return new LogInUser(user, userRole);
    }
}
