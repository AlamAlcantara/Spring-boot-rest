/**
 * 
 */
package com.example.users.services;

import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.users.entities.Role;
import com.example.users.entities.User;
import com.example.users.models.AuditDetails;
import com.example.users.models.SecurityRule;
import com.example.users.repositories.RoleRepository;
import com.example.users.repositories.UserInRoleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author alamalcanta
 *
 */

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserInRoleRepository userInRoleRepository;
	
	@Autowired
	private KafkaTemplate<Integer, String> kafkaTemplate;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	
	private static final Logger log = LoggerFactory.getLogger(RoleService.class);
	
//	@Secured({"ROLE_ADMIN"})
//	@RolesAllowed({"ROLE_ADMIN"})
	@SecurityRule
	public List<Role> getRoles() {
		log.info("Getting roles by name");
		return roleRepository.findAll();
	}
	
	public Role createRole(Role role) {
		
		Role createdRole = roleRepository.save(role);
		
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
				
		try {
			kafkaTemplate.send("devs4j-topic", mapper.writeValueAsString(new AuditDetails(userName, createdRole.getName())));
		} catch (JsonProcessingException e) {
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error parsing the message");
		}
		
		return createdRole;
	}
	
	public Role updateRole(Integer roleId, Role role) {
		Optional<Role> result = roleRepository.findById(roleId);
		
		if(result.isPresent()) {
			result.get().setName(role.getName());
			
			return roleRepository.save(result.get());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, (String.format("Role id %s not found", roleId)));
		}
	}

	
	public void deleteRole(Integer roleId) {
		
		Optional<Role> result = roleRepository.findById(roleId);
		
		if(result.isPresent()) {	
			roleRepository.deleteById(roleId);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, (String.format("Role id %s not found", roleId)));
		}
	}
	
	
	public List<User> getUsersByRoleName(String roleName) {
		return userInRoleRepository.findUsersByRoleName(roleName);
	}
	
}
