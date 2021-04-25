/**
 * 
 */
package com.example.users.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.users.entities.Role;
import com.example.users.entities.User;
import com.example.users.services.RoleService;

/**
 * @author alamalcanta
 *
 */
@RestController
@RequestMapping("/roles")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	private static final Logger log = LoggerFactory.getLogger(RoleController.class);

	
	@GetMapping()
	public ResponseEntity<List<Role>> getRoles() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		log.info("Name: {}", auth.getName());
		log.info("Principal: {}", auth.getPrincipal());
		log.info("Credential: {}", auth.getCredentials());
		log.info("Roles: {}", auth.getAuthorities().toString());
		
		return new ResponseEntity<>(roleService.getRoles(), HttpStatus.OK);
	}
	
	@GetMapping("/{roleName}/users")
	public ResponseEntity<List<User>> getUsersByRoleName(@PathVariable("roleName") String roleName) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		log.info("Name: {}", auth.getName());
		log.info("Principal: {}", auth.getPrincipal());
		log.info("Credential: {}", auth.getCredentials());
		log.info("Roles: {}", auth.getAuthorities().toString());
		
		return new ResponseEntity<>(roleService.getUsersByRoleName(roleName), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Role> createRole(@RequestBody() Role role) {
		return new ResponseEntity<>(roleService.createRole(role), HttpStatus.CREATED);
	}
	
	@PutMapping("/{roleId}")
	public ResponseEntity<Role> updateRole(@RequestBody() Role role, @PathVariable("roleId") Integer roleId) {
		return new ResponseEntity<>(roleService.updateRole(roleId, role), HttpStatus.OK);
	}
	
	@DeleteMapping("/{roleId}")
	public ResponseEntity<Void> updateRole(@PathVariable("roleId") Integer roleId) {
		roleService.deleteRole(roleId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
