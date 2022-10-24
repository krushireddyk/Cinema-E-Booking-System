package com.cinemaeBooking.repository;

import org.springframework.data.repository.CrudRepository;
import com.cinemaeBooking.entities.User;

public interface UserRepository extends CrudRepository<User,Long> 
{
	User findById(long userID);
	User findByUserName(String userName);
	User findByEmailID(String emailID);
	//User findByVerificationCode(String verificationCode);
	//User[] findAllByOptPromotionalEmails(String optPromotionalEmails);
}
