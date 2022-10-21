package com.cinemaeBooking.service;

import com.cinemaeBooking.entities.Customer;

public interface CustomerService 
{
	void save(Customer customer);
	Customer findByUserId(int userID);
	Customer findByUserName(String userName);
}
