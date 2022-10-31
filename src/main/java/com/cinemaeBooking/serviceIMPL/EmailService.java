package com.cinemaeBooking.serviceIMPL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.mail.internet.InternetAddress;
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
        String emailContents = templateEngine.process("verifyAccount", context);
        
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(email);
            message.setFrom(new InternetAddress("jhr2k64@gmail.com"));
            message.setSubject("change your password");
            message.setSentDate(new Date());
            message.setText(emailContents, true);
        };
        mailSender.send(preparator);
    }

}

