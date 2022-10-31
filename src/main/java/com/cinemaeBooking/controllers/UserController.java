package com.cinemaeBooking.controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cinemaeBooking.entities.RStatus;
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
	public ResponseEntity<?> userLogin(@RequestBody User user)
	{
		//System.out.println(userName);
		User u = userService.loginValidation(user.getUserName(),user.getPassword());
		if(u.getUserName()==null)
		{
			RStatus r = new RStatus();
			r.setStatusCode(400);
			r.setStatusMessage("Invalid credentials");
			return new ResponseEntity<RStatus>(r, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<User>(u, HttpStatus.OK);
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
	@RequestMapping(value = "/verifyverificationcode",method=RequestMethod.PUT)
	public ResponseEntity<?>  verifyVerificationCode(@RequestBody User user)
	{
		System.out.println(user.getUserName()+user.getVerificationCode());
		int status = userServiceImplementation.verifyVerificationCode(user);
		RStatus rstatus = new RStatus();
		if(status == 0) {
			rstatus.setStatusCode(400);
			rstatus.setStatusMessage("Invalid verfication details: userName or verification Code");
			return new ResponseEntity<RStatus>(rstatus, HttpStatus.BAD_REQUEST);
		}
		rstatus.setStatusCode(200);
		rstatus.setStatusMessage("User Verification successful");
		return new ResponseEntity<RStatus>(rstatus, HttpStatus.OK);
		
	}
}
