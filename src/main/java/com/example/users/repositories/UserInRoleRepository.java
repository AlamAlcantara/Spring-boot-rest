/**
 * 
 */
package com.example.users.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.users.entities.Role;
import com.example.users.entities.User;
import com.example.users.entities.UserInRole;

/**
 * @author alamalcanta
 *
 */
@Repository
public interface UserInRoleRepository extends JpaRepository<UserInRole, Integer>{

	@Query("SELECT u.user FROM UserInRole u WHERE u.role.name = ?1")
	public List<User> findUsersByRoleName(String roleName);
	
	public List<UserInRole> findByUser(User user);
}
