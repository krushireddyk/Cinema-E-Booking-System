package com.cinemaeBooking.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Random;
import java.util.Set;

import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import com.cinemaeBooking.entities.HomeAddress;
import com.cinemaeBooking.entities.PaymentCard;
import com.cinemaeBooking.entities.RStatus;
import com.cinemaeBooking.entities.Status;
import com.cinemaeBooking.entities.User;
import com.cinemaeBooking.entities.UserType;
//import com.cinemaeBooking.repository.PayCardRepository;
import com.cinemaeBooking.repository.UserRepository;
import com.cinemaeBooking.service.EncryptDecrypt;
import com.cinemaeBooking.serviceIMPL.EmailService;
@RestController
public class Registration {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;

    @Autowired
    EncryptDecrypt encryptDecrypt;

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
	public ResponseEntity<?> registerAccount(@RequestBody User userForm, BindingResult bindingResult)
	{
		User user = new User();
        User savedUser = null;
        try {
            if (bindingResult.hasErrors()) 
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            user.setLastName(userForm.getLastName());
            user.setFirstName(userForm.getFirstName());
            user.setUserName(userForm.getUserName());
            user.setEmailID(userForm.getEmailID().toLowerCase());
            user.setPhoneNumber(userForm.getPhoneNumber());
            user.setPassword(encryptDecrypt.encrypt(userForm.getPassword()));
            //user.setAddress(userForm.getAddress());
            if(userForm.getAddress() != null) {
                HomeAddress address = userForm.getAddress();
                address.setCity(userForm.getAddress().getCity());
                address.setState(userForm.getAddress().getState());
                address.setStreet(userForm.getAddress().getStreet());
                address.setZipCode(userForm.getAddress().getZipCode());
                address.setUser(user);
                user.setAddress(address);
            }
            user.setVerificationCode(getSaltString());
            user.setPromotionEnabled(userForm.getPromotionEnabled());
            UserType userType = new UserType();//Instance of UserType
            userType.setRoleID(2);
            userType.setUserRole("Customer");
            //userType.setUser(user);
            user.setUsertype(userType);
            
            
            Status status = new Status();
            status.setStatusID(2);
            status.setStatus("InActive");
            //status.setUser(user);
            user.setStatus(status);
            
            Set<PaymentCard> paymentCards = userForm.getPaymentCards();
            
            for(PaymentCard paymentCard : paymentCards)
            {
                BillingAddress billingAddress = new BillingAddress();
                billingAddress.setCity(paymentCard.getAddress().getCity());
                billingAddress.setState(paymentCard.getAddress().getState());
                billingAddress.setStreet(paymentCard.getAddress().getStreet());
                billingAddress.setZipCode(paymentCard.getAddress().getZipCode());
                billingAddress.setPaymentCard(paymentCard);
                paymentCard.setAddress(billingAddress);
                paymentCard.setUser(user);
                paymentCards.add(paymentCard);
            }
            user.setPaymentCards(paymentCards);
            String verificationCode = getSaltString();
            savedUser = userRepository.save(user);
            savedUser.setPassword(encryptDecrypt.decrypt(savedUser.getPassword()));
            //emailService.sendRegistrationEmail(userForm.getEmailID().toLowerCase(), verificationCode);
        }
        catch(DataIntegrityViolationException e) {
            RStatus status = new RStatus();
            if(e.getLocalizedMessage().contains("EmailID")) {
                status.setStatusCode(400);
                status.setStatusMessage("emailID already registered, please provide different emailID");
            }
            else if (e.getLocalizedMessage().contains("UserName")) {
                status.setStatusCode(400);
                status.setStatusMessage("userName already exists, please provide different userName");
            }
            return new ResponseEntity<RStatus>(status, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<User>(savedUser, HttpStatus.OK);
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
