package com.cinemaeBooking.service;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.cinemaeBooking.entities.User;

@Repository
public interface UserService 
{
	public User loginValidation(String userName, String password);
	public int adminLoginValidation(String userName, String password);
	public String getVerificationCode(String email) throws SQLException;
	public int verifyVerificationCode(User user);
	public int changePassword(User user);
}
