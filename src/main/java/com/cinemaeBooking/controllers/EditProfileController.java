package com.cinemaeBooking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cinemaeBooking.entities.User;
import com.cinemaeBooking.service.EditProfileService;
import com.cinemaeBooking.serviceIMPL.EmailService;

@RestController
@RequestMapping("/editProfile")
public class EditProfileController {

    @Autowired
    EditProfileService editProfileService;

    @Autowired
    EmailService emailService;

    @RequestMapping(value = "/{userName}", method = RequestMethod.GET)
    public User getUser(@PathVariable String userName){
        return editProfileService.getUser(userName);
    }

    @RequestMapping(value = "/update/{userName}", method = RequestMethod.PUT)
    public User updateProfile(@PathVariable String userName, @RequestBody User user) throws Exception{
        
        User updateUser = editProfileService.editUser(userName, user);
        emailService.sendEmail(user.getEmailID(), updateUser.getFirstName());

        return updateUser;
    }

}
