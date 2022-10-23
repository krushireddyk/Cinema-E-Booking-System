package com.cinemaeBooking.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cinemaeBooking.entities.User;
import com.cinemaeBooking.repository.PayCardRepository;
import com.cinemaeBooking.repository.UserRepository;

public class Registration {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PayCardRepository payCardRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
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
	public Object registerAccount(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) throws IOException, MessagingException
	{
		return null;
	}
	
	@RequestMapping(value = "/registrationconfirmation", method = RequestMethod.GET)
	public String showRegistrationConfirmationPage(ModelMap model) 
	{
        model.addAttribute("registrationconfirmation", new User());
        return "registrationconfirmation";
    }
	
	@RequestMapping(value = "/registrationconfirmation", method = RequestMethod.POST)
	public Object submitConfirmationCode(@ModelAttribute("registrationconfirmation") User userForm, Model model)
	{
		User userInstance = userRepository.findByVerificationCode(userForm.getVerificationCode());
        if (userInstance == null || !(userInstance.getVerificationCode().matches(userForm.getVerificationCode()))) 
        {
            System.out.println("Incorrect Verification Code");
            System.out.println(userInstance);
            return "registrationconfirmation";
        }
        
        if (!(userInstance == null || !(userInstance.getVerificationCode().matches(userForm.getVerificationCode())))) 
        {
            userInstance.setUserStatus("ACTIVE");
            userRepository.save(userInstance);
            System.out.println("Account is now active");
            return "redirect:/login";
        }
        
        return "redirect:/registrationconfirmation";
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
	
	private void sendmail(String emailRecipient, String verCode) throws AddressException, MessagingException, IOException 
	{
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() 
        {
            protected PasswordAuthentication getPasswordAuthentication() 
            {
                return new PasswordAuthentication("cinemaregconf@gmail.com", "HotStuffSecure1!");
            }
        });
        
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("cinemaregconf@gmail.com", false));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailRecipient));
        msg.setSubject("Registration Confirmation Email");
        msg.setContent("Hello, \n\nThank you for registering to our cinema e-booking system!\n\nYour verification code is: "+verCode, "text/html");
        msg.setSentDate(new Date());
        Transport.send(msg);
    }

}
