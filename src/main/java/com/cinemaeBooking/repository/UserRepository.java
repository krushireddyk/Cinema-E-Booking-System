package com.cinemaeBooking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.cinemaeBooking.entities.User;

@SuppressWarnings("unchecked")
public interface UserRepository extends JpaRepository<User, Integer> 
{
	Optional<User> findById(Integer userID);
	User findByUserName(String userName);
	User findByEmailID(String emailID);
	//User save(User user);
	//User findByVerificationCode(String verificationCode);
	//User[] findAllByOptPromotionalEmails(String optPromotionalEmails);
}
