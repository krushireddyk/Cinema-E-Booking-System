package com.cinemaeBooking.controllers;

import java.io.IOException;
import java.util.Random;
import java.util.Set;

import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cinemaeBooking.entities.BillingAddress;
import com.cinemaeBooking.entities.PaymentCard;
import com.cinemaeBooking.entities.Status;
import com.cinemaeBooking.entities.User;
import com.cinemaeBooking.entities.UserType;
//import com.cinemaeBooking.repository.PayCardRepository;
import com.cinemaeBooking.repository.UserRepository;
import com.cinemaeBooking.serviceIMPL.EmailService;
@RestController
public class Registration {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;

	//@Autowired
	//private PayCardRepository payCardRepository;
	
	//@Autowired
	//private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public Registration(UserRepository userRepository) 
    {
        this.userRepository = userRepository;
    }
	
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String showRegistrationPage(Model model) 
	{
		model.addAttribute("userForm", new User());
		return "registration";
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public Object registerAccount(@RequestBody User userForm, BindingResult bindingResult) throws IOException, MessagingException
	{
		User user = new User();
		if (bindingResult.hasErrors()) 
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
		user.setLastName(userForm.getLastName());
		user.setFirstName(userForm.getFirstName());
		user.setUserName(userForm.getUserName());
		user.setEmailID(userForm.getEmailID().toLowerCase());
		user.setPhoneNumber(userForm.getPhoneNumber());
		user.setPassword(userForm.getPassword());
        user.setAddress(userForm.getAddress());
        user.setVerificationCode(getSaltString());
		UserType userType = new UserType();//Instance of UserType
		userType.setRoleID(2);
		//userType.setUserRole("Customer");
		
        
		Status status = new Status();
		status.setStatusID(1);
		//status.setStatus("InActive");
		
        Set<PaymentCard> paymentCards = userForm.getPaymentCards();
        
        for(PaymentCard paymentCard : paymentCards)
        {
        	BillingAddress billingAddress = paymentCard.getAddress();
            billingAddress.setPaymentCard(paymentCard);
            paymentCard.setAddress(billingAddress);
            paymentCard.setUser(user);
            paymentCards.add(paymentCard);
        }
        user.setPaymentCards(paymentCards);
        String verificationCode = getSaltString();
        emailService.sendRegistrationEmail(userForm.getEmailID().toLowerCase(), verificationCode);   
        return userRepository.save(user);
	}
	
	@RequestMapping(value = "/accountVerification", method = RequestMethod.GET)
	public String showRegistrationConfirmationPage(ModelMap model) 
	{
        model.addAttribute("accountVerification", new User());
        return "accountVerification";
    }
	
	@RequestMapping(value = "/accountVerification", method = RequestMethod.POST)
	public Object submitConfirmationCode(@ModelAttribute("accountVerification") User userForm, Model model)
	{
		User userInstance = userRepository.findByEmailID(userForm.getEmailID());
        if (userInstance == null || !(userInstance.getVerificationCode().matches(userForm.getVerificationCode()))) 
        {
            System.out.println("Incorrect Verification Code");
            System.out.println(userInstance);
            return "accountVerification";
        }
        
        if (!(userInstance == null || !(userInstance.getVerificationCode().matches(userForm.getVerificationCode())))) 
        {
        	Status status = new Status();
        	status.setStatus("ACTIVE");
            userRepository.save(userInstance);
            System.out.println("Account is now active");
            return "redirect:/login";
        }
        return "redirect:/accountVerification";
	}
	
	protected String getSaltString() 
	{
        String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        while (sb.length() < 6) // length of the random string.
        { 
            int index = (int) (random.nextFloat() * allChars.length());
            sb.append(allChars.charAt(index));
        }
        String saltString = sb.toString();
        return saltString;
    }
}
