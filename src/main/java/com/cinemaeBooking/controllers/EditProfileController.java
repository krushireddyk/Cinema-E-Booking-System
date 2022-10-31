package com.cinemaeBooking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cinemaeBooking.entities.RStatus;
import com.cinemaeBooking.entities.User;
import com.cinemaeBooking.exception.CustomErrorsException;
import com.cinemaeBooking.service.EditProfileService;
import com.cinemaeBooking.service.EncryptDecrypt;
import com.cinemaeBooking.serviceIMPL.EmailService;

@RestController
@RequestMapping("/editProfile")
public class EditProfileController {

    @Autowired
    EditProfileService editProfileService;

    @Autowired
    EmailService emailService;

    @Autowired
    EncryptDecrypt encryptDecrypt;

    @RequestMapping(value = "/{userName}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable String userName) {
        User user = editProfileService.getUser(userName);
        if(user == null) {
            RStatus status = new RStatus();
            status.setStatusCode(400);
            status.setStatusMessage("userName cannot be found");
            return new ResponseEntity<RStatus>(status, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{userName}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProfile(@PathVariable String userName, @RequestBody User user) {
        
        User updateUser = null;
        try {
            updateUser = editProfileService.editUser(userName, user);
            updateUser.setPassword(encryptDecrypt.decrypt(updateUser.getPassword()));
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
        //emailService.sendEmail(updateUser.getEmailID(), updateUser.getFirstName());

        return new ResponseEntity<User>(updateUser, HttpStatus.OK);
    }

}
