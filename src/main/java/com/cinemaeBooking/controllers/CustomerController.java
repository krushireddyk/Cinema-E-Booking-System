package com.cinemaeBooking.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.cinemaeBooking.entities.Customer;
import com.cinemaeBooking.service.CustomerService;
//import com.cinemaeBooking.serviceIMPL.EmailService;

public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	//@Autowired
	//private EmailService emailService;
	
	@GetMapping("/signin")
    public String showLogin() 
	{
        return "signin";
    }
	
	@GetMapping("/signup")
	public String registration(Model model)
	{
		model.addAttribute("userForm", new Customer());
		return "signup";
	}
	
	@PostMapping("/signup")
	public String registration(@ModelAttribute("userForm") Customer userForm, Model model, BindingResult bindingResult)
	{
		Customer existingCustomer = customerService.findByUserName(userForm.getUserName());
		if(existingCustomer != null)
		{
			model.addAttribute("message","The username has already taken !!");
			return "signup";
		}
		else 
		{
			customerService.save(userForm);
			Customer customer = new Customer();
			customer.setFirstName(userForm.getFirstName());
			customer.setLastName(userForm.getLastName());
			customer.setPhoneNumber(userForm.getPhoneNumber());
			customer.setUserName(userForm.getUserName());
			customer.setEmail(userForm.getEmail());
			customer.setPassword(userForm.getPassword());
			customer.setPaymentCard(userForm.getPaymentCard());
			customer.setAddress(userForm.getAddress());
			customer.setCity(userForm.getCity());
			customer.setState(userForm.getState());
			customer.setZipcode(userForm.getZipcode());
			customerService.save(customer);
			//emailService.sendVerificationEmail(userForm.getEmail(),userForm.getFirstName());
			return "redirect:/signupconfirm";
		}
	}
}
