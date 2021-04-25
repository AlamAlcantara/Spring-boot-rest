/**
 * 
 */
package com.example.users.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.users.entities.Address;
import com.example.users.entities.Profile;
import com.example.users.repositories.AddressRepository;
import com.example.users.repositories.ProfileReporistory;

/**
 * @author alamalcanta
 *
 */
@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private ProfileReporistory profileReporistory;
	
	
	public List<Address> findAddressesByProfileIdAndUserId( Integer userId, Integer profileId) {
		return addressRepository.findAddressesByProfileIdAndUserId(userId, profileId);
	}
	
	
	public Address createAddress(Address address, Integer userId, Integer profileId) {
		
		Profile profile = profileReporistory.findByUserIdAndProfileId(userId, profileId)
		.orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND, 
				String.format("Profile not found for user with Id %s", userId)));
		
		address.setProfile(profile);
		
		return addressRepository.save(address);
	}
}
