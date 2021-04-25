/**
 * 
 */
package com.example.users.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.users.entities.User;
import com.example.users.entities.UserInRole;
import com.example.users.repositories.UserInRoleRepository;
import com.example.users.repositories.UserRepository;

/**
 * @author alamalcanta
 *
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserInRoleRepository userInRoleRepository;
	
	@Autowired
	public PasswordEncoder passwordEncoder;
	
	
	private static final Logger log = LoggerFactory.getLogger(AppUserDetailsService.class);
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> result = userRepository.findByUsername(username);
		
		if(result.isPresent()) {
			User user = result.get();
			
			List<UserInRole> userInRoleList = userInRoleRepository.findByUser(user);
			
			String[] roles = userInRoleList.stream()
					.map(r ->  r.getRole().getName())
					.toArray(String[]::new);
			
			log.info("trying with Username: {} password: {}", user.getUsername(), user.getPassword());
			
			return org.springframework.security.core.userdetails.User
					.withUsername(user.getUsername())
					.password(passwordEncoder.encode(user.getPassword()))
					.roles(roles)
					.build();
					
					
		} else {
			throw new UsernameNotFoundException("Username " + username + " Not Found");
		}
	}

}
