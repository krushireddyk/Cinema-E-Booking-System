package com.cinemaeBooking.repository;

import org.springframework.data.repository.CrudRepository;
import com.cinemaeBooking.entities.User;

public interface UserRepository extends CrudRepository<User,Long> 
{
	User findById(long user_id);
	User findByUserName(String userName);
	User findByEmail(String email);
	User findByVerificationCode(String verificationCode);
	User[] findAllByOptPromotionalEmails(String optPromotionalEmails);
}
