/**
 * 
 */
package com.example.users.controllers;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.users.entities.User;
import com.example.users.services.UserService;

import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author alam alcantara
 *
 */

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	@Timed("get.users")
	public ResponseEntity<Page<User>> getUsers(
			@RequestParam(value="page", required = false, defaultValue = "0") int page, 
			@RequestParam(value = "size", required = false, defaultValue = "100") int size) {
		
		return new ResponseEntity<>(userService.getUsers(page, size), HttpStatus.OK);
	}
	
	@GetMapping("/usernames")
	public ResponseEntity<Page<String>> getUsersUsernames(
			@RequestParam(value="page", required = false, defaultValue = "0") int page, 
			@RequestParam(value = "size", required = false, defaultValue = "100") int size) {
		
		return new ResponseEntity<>(userService.getUsersUsernames(page, size), HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	@ApiOperation(value = "Returns a user by its ID", response = User.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message ="The record was found"),
			@ApiResponse(code = 404, message ="The record was not found")
	})
	public ResponseEntity<User> getUserById(@PathVariable("userId") Integer userId) {
		return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
	}
	
	@GetMapping("/username/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
		return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
	}
	
	@PostMapping("/auth")
	public ResponseEntity<User> authenticate(@RequestBody() User user) {
		return new ResponseEntity<>(
				userService.getUserByUsernameAndPassword(
						user.getUsername(), user.getPassword()),
				HttpStatus.OK);
	}
	
	@DeleteMapping("/{username}")
	public ResponseEntity<Void> deleteUserByUsername(@PathVariable("username") String username) {
		userService.deleteUserByUsername(username);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}


}
