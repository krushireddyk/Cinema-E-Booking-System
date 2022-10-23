package com.cinemaeBooking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cinemaeBooking.entities.User;

import com.cinemaeBooking.service.SecurityService;

@Controller
public class Login 
{
	//@Autowired
	//private UserRepository userRepository;
	
	@Autowired
	private SecurityService securityService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String displayLoginPage(ModelMap model, String error,
            @RequestParam(value = "logout", required=false) String logout,
            @RequestParam(value = "forgot", required=false) String forgot)
	{
		model.addAttribute("login", new User());
        if (error != null)
            model.addAttribute("error", "Your username and password are invalid.");
        if (logout != null)
            model.addAttribute("message", "Logout Successful.");
        if (forgot != null)
            model.addAttribute("reset", "A temporary password has been sent to your email");
        
        System.out.println("the page has loaded clearly");
        return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Object submitLogin(@ModelAttribute("login") User userForm, Model model)
	{
		//User userIns = userRepository.findByUserName(userForm.getUserName());
		return securityService.Login(userForm.getUserName(), userForm.getPassword());
	}
}
