package com.cinemaeBooking.serviceIMPL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.cinemaeBooking.entities.Booking;

import javax.mail.internet.InternetAddress;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class EmailService 
{
    private final TemplateEngine templateEngine;
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    public EmailService(TemplateEngine templateEngine) 
    {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("mail/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCacheable(false);
        templateEngine.setTemplateResolver(templateResolver);
        this.templateEngine = templateEngine;
    }

    public void sendEmail(String email, String firstName) 
    {
        Context context = new Context();
        context.setVariable("name", firstName);
        String emailContents = templateEngine.process("profileChanged", context);
        
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(email);
            message.setFrom(new InternetAddress("no-reply@cinemaeBooking.com"));
            message.setSubject("Profile has been changed");
            message.setSentDate(new Date());
            message.setText(emailContents, true);
        };
        mailSender.send(preparator);
    }
    
    public void sendRegistrationEmail(String email, String verificationCode, String userName)
    {
    	Context context = new Context();
        context.setVariable("code", verificationCode);
        String emailContents = templateEngine.process("verifyAccount", context);
        
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(email);
            message.setFrom(new InternetAddress("jhr6066@gmail.com"));
            message.setSubject("Cinema e-Booking : Verify your Account with :"+verificationCode+", userName: "+userName);
            message.setSentDate(new Date());
            message.setText(emailContents, true);
        };
        mailSender.send(preparator);
    }
    
    public void sendForgotPasswordEmail(String email, String verificationCode)
    {
    	Context context = new Context();
        context.setVariable("code", verificationCode);
        String emailContents = templateEngine.process("forgotPassword", context);
        
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(email);
            message.setFrom(new InternetAddress("jhr2k64@gmail.com"));
            message.setSubject("Reset your password with: "+verificationCode);
            message.setSentDate(new Date());
            message.setText(emailContents, true);
        };
        mailSender.send(preparator);
    }
    
    public void sendPromotionalEmail(String email, String promotionCode)
    {
    	Context context = new Context();
        context.setVariable("code", promotionCode);
        String emailContents = templateEngine.process("NewPromotion", context);
        
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(email);
            message.setFrom(new InternetAddress("jhr6066@gmail.com"));
            message.setSubject("use the discount and book your tickets "+promotionCode);
            message.setSentDate(new Date());
            message.setText(emailContents, true);
        };
        mailSender.send(preparator);
    }
    
    public void sendBookingEmail(Booking booking)
    {
    	Context context = new Context();
    	context.setVariable(booking.getMovie().getTitle(), booking.getBookingID());
    	String emailContents = templateEngine.process("BookingConfirmation", context);
    	MimeMessagePreparator preparator = mimeMessage -> 
    	{
    		MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
    		message.setTo(booking.getUser().getEmailID());
    		message.setFrom(new InternetAddress("jhr6066@gmail.com"));
    		message.setSubject("Your Booking Confirmation for :"+booking.getMovie().getTitle());
    		message.setSentDate(new Date());
            message.setText(emailContents, true);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            String startDateString = outputFormat.format(booking.getShowdetails().getShowId().getShowDate());
            message.setText(booking.getMovie().getTitle()+" "+booking.getShowdetails().getShowDuration()+" Enjoy the movie\n"+"\t Your booking id: CEBS "+booking.getBookingID()+" "+
            		"Date : "+startDateString+"\t "+"Screen "+booking.getShowdetails().getShowId().getScreen().getScreenID()+" \t"+"Show Time : "+booking.getShowdetails().getShowId().getShowTime());
            //message.setText(booking.getShowdetails().getShowId().getShowDate()+"\t "+booking.getShowdetails().getShowId().getScreen()+" \t"+booking.getShowdetails().getShowId().getShowTime());
    	};
    	mailSender.send(preparator);
    }

}


