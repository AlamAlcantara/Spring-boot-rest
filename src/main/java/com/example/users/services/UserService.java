/**
 * 
 */
package com.example.users.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.users.entities.User;
import com.example.users.repositories.UserRepository;

/**
 * @author alamalcanta
 *
 */
@Service
public class UserService {
	
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	public Page<User> getUsers(int page, int size) {
		return userRepository.findAll(PageRequest.of(page, size));
	}
	
	public Page<String> getUsersUsernames (int page, int size) { 
		return userRepository.findUsernames(PageRequest.of(page, size));
	}
	
	public User getUserById(Integer userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND, 
						String.format("User id %s not found", userId)));
	}
	
	@CacheEvict("users")
	public void deleteUserByUsername(String username) {
		
		User user = this.getUserByUsername(username); 
		userRepository.delete(user);
	}
	
	@Cacheable("users")
	public User getUserByUsername(String username) {
		
		log.info("Getting user by username {}", username);
		
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND, 
						String.format("User %s not found", username)));
	}
	
	
	public User getUserByUsernameAndPassword(String username, String password) {
		return userRepository.findByUsernameAndPassword(username, password)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND, 
						String.format("User %s not found", username)));
	}

}
