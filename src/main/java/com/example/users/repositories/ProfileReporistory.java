/**
 * 
 */
package com.example.users.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.users.entities.Profile;

/**
 * @author alamalcanta
 *
 */
@Repository
public interface ProfileReporistory extends CrudRepository<Profile, Integer> {
	
	@Query("SELECT p FROM Profile p WHERE p.id = ?2 AND p.user.Id = ?1")
	Optional<Profile> findByUserIdAndProfileId(Integer userId, Integer profileId);
}
