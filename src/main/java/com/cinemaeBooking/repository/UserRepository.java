package com.cinemaeBooking.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.cinemaeBooking.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> 
{
	Optional<User> findById(Integer userID);
	User findByUserName(String userName);
	User findByEmailID(String emailID);
	//User save(User user);
	User findByVerificationCode(String verificationCode);
	//User[] findAllByOptPromotionalEmails(String optPromotionalEmails);
}
