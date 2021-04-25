/**
 * 
 */
package com.example.users.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.users.entities.Address;

/**
 * @author alamalcanta
 *
 */

@Repository
public interface AddressRepository  extends CrudRepository<Address, Integer>{

	
	@Query("SELECT a FROM Address a WHERE a.profile.id = ?2 AND a.profile.user.Id = ?1")
	List<Address> findAddressesByProfileIdAndUserId( Integer userId, Integer profileId);
}
