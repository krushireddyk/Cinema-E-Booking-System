package com.cinemaeBooking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cinemaeBooking.entities.User;
import com.cinemaeBooking.repository.UserRepository;

@RestController
public class EditProfileController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/getProfile/{userName}")
    public User getUser(@PathVariable String userName){
        return userRepository.findByUserName(userName);
    }
}
