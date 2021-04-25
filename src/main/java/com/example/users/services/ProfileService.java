/**
 * 
 */
package com.example.users.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.users.entities.Profile;
import com.example.users.entities.User;
import com.example.users.repositories.ProfileReporistory;
import com.example.users.repositories.UserRepository;

/**
 * @author alamalcanta
 *
 */
@Service
public class ProfileService {
	
	@Autowired
	private ProfileReporistory profileReporistory;
	
	@Autowired
	private UserRepository userRepository;
	
	public Profile createProfile(Integer userId, Profile profile) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND, 
						String.format("User with Id %s not found", userId)));
		
		profile.setUser(user);
		
		return profileReporistory.save(profile);
	}
	
	
	public Profile getProfileById(Integer userId, Integer profileId) {
		
		return profileReporistory.findByUserIdAndProfileId(userId, profileId)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND, 
						String.format("Profile not found for user with Id %s", userId)));
	}
	
}
