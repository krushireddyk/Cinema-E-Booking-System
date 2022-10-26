package com.cinemaeBooking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cinemaeBooking.entities.User;
import com.cinemaeBooking.service.EditProfileService;

@RestController
@RequestMapping("/editProfile")
public class EditProfileController {

    @Autowired
    EditProfileService editProfileService;

    @RequestMapping(value = "/{userName}", method = RequestMethod.GET)
    public User getUser(@PathVariable String userName){
        return editProfileService.getUser(userName);
    }

    @RequestMapping(value = "/update/{userName}", method = RequestMethod.PUT)
    public User updateProfile(@PathVariable String userName, @RequestBody User user){
        return editProfileService.saveUser(userName, user);
    }
}
