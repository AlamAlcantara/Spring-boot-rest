/**
 * 
 */
package com.example.users.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.users.entities.UserInRole;
import com.example.users.repositories.UserInRoleRepository;

/**
 * @author alamalcanta
 *
 */
@Service
public class UserInRoleService {

	@Autowired
	private UserInRoleRepository userInRoleRepository;
	
	
	public List<UserInRole> getUsersInRoles() {
		return userInRoleRepository.findAll();
	}
}
