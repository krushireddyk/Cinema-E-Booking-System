package com.cinemaeBooking.controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cinemaeBooking.entities.User;
import com.cinemaeBooking.repository.UserRepository;
import com.cinemaeBooking.service.UserService;
import com.cinemaeBooking.serviceIMPL.EmailService;
import com.cinemaeBooking.serviceIMPL.UserServiceImplementation;

@RestController
public class UserController 
{
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserServiceImplementation userServiceImplementation;
	@Autowired
	private EmailService emailService;
	
	//@PostMapping("login/{userName}/{password}")
	//public int userLogin(@PathVariable("userName") String userName,@PathVariable("password") String password)
	@PostMapping("/login")
	public int userLogin(@RequestBody String userName, String password)
	{
		int flag = userService.loginValidation(userName, password);
		if(flag == 0)
		{
			return 0;
		}
		return flag;
	}
	@PostMapping("/adminLogin")
	//@GetMapping("adminLogin/{userName}/{password}")
	public int adminLogin(@RequestBody String userName, String password)
	{
		int adminFlag = userService.adminLoginValidation(userName, password);
		if(adminFlag == 0)
		{
			return 0;
		}
		return adminFlag;
	}
	
	@RequestMapping(value = "/forgotPassword/{userName}",method=RequestMethod.GET)
	public String getVerificationCode(@PathVariable String uname)
	{
		String un = null;
        try 
        {
			un= userServiceImplementation.getVerificationCode(uname);	
			emailService.sendForgotPasswordEmail(uname,un);
		} 
        catch (SQLException e) 
        {
			e.printStackTrace();
		}
		return un;
    }
}
