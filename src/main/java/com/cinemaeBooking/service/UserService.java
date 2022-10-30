package com.cinemaeBooking.service;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

@Repository
public interface UserService 
{
	public int loginValidation(String userName, String password);
	public int adminLoginValidation(String userName, String password);
	public String getVerificationCode(String email) throws SQLException;;
}
