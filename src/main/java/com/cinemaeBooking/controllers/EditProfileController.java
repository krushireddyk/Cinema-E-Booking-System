package com.cinemaeBooking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cinemaeBooking.business.EditProfileDetails;
import com.cinemaeBooking.entities.RStatus;
import com.cinemaeBooking.entities.User;
import com.cinemaeBooking.exception.CustomErrorsException;
import com.cinemaeBooking.service.EncryptDecrypt;
import com.cinemaeBooking.serviceIMPL.EmailService;

@RestController
@RequestMapping("/editProfile")
public class EditProfileController {

    @Autowired
    EditProfileDetails editProfileDetailsService;

    @Autowired
    EmailService emailService;

    @Autowired
    EncryptDecrypt encryptDecrypt;

    @RequestMapping(value = "/{userName}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable String userName) {
        User user = null;
        try {
            user = editProfileDetailsService.getUser(userName);
        } catch (CustomErrorsException e) {
            RStatus status = new RStatus();
            status.setStatusCode(400);
            status.setStatusMessage(e.getMessage());
            return new ResponseEntity<RStatus>(status, HttpStatus.BAD_REQUEST);
        }
        if(user == null) {
            RStatus status = new RStatus();
            status.setStatusCode(400);
            status.setStatusMessage("userName cannot be found");
            return new ResponseEntity<RStatus>(status, HttpStatus.BAD_REQUEST);
        }
        RStatus status = new RStatus();
        status.setStatusCode(200);
        status.setStatusMessage("User Details");
        user.setRstatus(status);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{userName}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProfile(@PathVariable String userName, @RequestBody User user) {
        
        User updateUser = null;
        try {
            updateUser = editProfileDetailsService.editUser(userName, user);
            updateUser.setPassword(encryptDecrypt.decrypt(updateUser.getPassword()));
            emailService.sendEmail(updateUser.getEmailID(), updateUser.getFirstName());
        } 
        catch (CustomErrorsException e) {
            RStatus status = new RStatus();
            status.setStatusCode(400);
            status.setStatusMessage(e.getMessage());
            return new ResponseEntity<RStatus>(status, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            RStatus status = new RStatus();
            status.setStatusCode(500);
            status.setStatusMessage(e.getLocalizedMessage());
            return new ResponseEntity<RStatus>(status, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        RStatus status = new RStatus();
        status.setStatusCode(200);
        status.setStatusMessage("User updated successfully");

        return new ResponseEntity<User>(updateUser, HttpStatus.OK);
    }

}
