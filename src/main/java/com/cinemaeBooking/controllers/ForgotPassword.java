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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cinemaeBooking.entities.User;
import com.cinemaeBooking.repository.UserRepository;

public class ForgotPassword {
	
	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
	public String showForgotPasswordPage(ModelMap model, String error) 
	{
        model.addAttribute("email", new User());
        if (error != null)
        {
            model.addAttribute("error", "This email does not exist.");
        }

        return "forgotpassword";
    }
	
	/*@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public Object submit(@ModelAttribute("email") String forgotEmail, BindingResult bindingResult) throws IOException, MessagingException
	{
		if (bindingResult.hasErrors()) 
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        /*User user = userRepository.findByEmail(forgotEmail);
        if(user != null) 
        {
            String tempPassword = generateRandomPassword();
            user.setPassword(bCryptPasswordEncoder.encode(tempPassword));
            userRepository.save(user);
            sendmail(forgotEmail, tempPassword);
            return "redirect:/login?forgot";
        } 
        else 
        {
            return "redirect:/forgotpassword?error";
        }
	}*/
	
	private String generateRandomPassword()
	{
		String allChars = "1234567890!@#abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        while (sb.length() < 11) 
        {
            int index = (int) (random.nextFloat() * allChars.length());
            sb.append(allChars.charAt(index));
        }
        String randomPass = sb.toString();
        return randomPass;
	}
	
	private void sendmail(String emailRecipient, String randomPassword) throws AddressException, MessagingException, IOException
	{
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
		
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() 
        {
            protected PasswordAuthentication getPasswordAuthentication() 
            {
                return new PasswordAuthentication("jhr2k64@gmail.com", "HotStuffSecure1!");
            }
        });
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("jhr2k64@gmail.com", false));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailRecipient));
		message.setSubject("Registration Confirmation Email");
		message.setContent("Hello, \n\n We are sorry you've forgotten your password. We have sent you a temporary password" +
                " in hopes that you are able to recover it.\n\nYour temporary password is: " +randomPassword, "text/html");
		message.setSentDate(new Date());
		Transport.send(message);
	}

}
