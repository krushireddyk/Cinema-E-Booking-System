package com.cinemaeBooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinemaeBooking.entities.User;
import com.cinemaeBooking.repository.UserRepository;

@Service
public class CheckOutPageDetails 
{
	@Autowired
	UserRepository userRepository;
	
	public User getUserCheckoutDetails(String userName)
	{
		User user;
		//User requiredDetailsUser = null;
		user = userRepository.findByUserName(userName);
		
		/*
		requiredDetailsUser.setFirstName(user.getFirstName());
		requiredDetailsUser.setLastName(user.getLastName());
		requiredDetailsUser.setUserName(user.getUserName());
		requiredDetailsUser.setEmailID(user.getEmailID());
		requiredDetailsUser.setPaymentCards(user.getPaymentCards());		
		return requiredDetailsUser;
		*/
		return user;
	}
}
