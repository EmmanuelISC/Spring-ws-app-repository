package com.emmanuel.app.ws.io.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.emmanuel.app.ws.io.entity.UserEntity;

// the first value is the object that needs to be persisted, the second is thr data type of the 
// ID field 
@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
	//this indicate to find email field from userEnity previously declared 
	UserEntity findByEmail(String email);
	UserEntity findByUserId(String userId);	
	UserEntity findUserByEmailVerificationToken(String token);
}
