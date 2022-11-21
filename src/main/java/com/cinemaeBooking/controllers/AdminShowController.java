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
import com.cinemaeBooking.entities.ShowDetails;
import com.cinemaeBooking.exception.CustomErrorsException;
import com.cinemaeBooking.service.AdminShowService;

@RestController
public class AdminShowController {

    @Autowired
    AdminShowService adminShowService;

    @RequestMapping(value = "/addShow/{title}", method = RequestMethod.POST)
	public ResponseEntity<?> addShow(@PathVariable String title, @RequestBody ShowDetails showDetails)
	{
        ShowDetails showDetails2 = null;
		try {
            showDetails2 = adminShowService.addShow(title, showDetails);
        }
        catch (CustomErrorsException e) 
    	{
        	RStatus status = new RStatus();
            status.setStatusCode(400);
            status.setStatusMessage(e.getMessage());
			return new ResponseEntity<RStatus>(status, HttpStatus.BAD_REQUEST);
        } 
        catch(Exception e)
    	{
			e.printStackTrace();
    		RStatus status = new RStatus();
            status.setStatusCode(400);
            status.setStatusMessage(e.getLocalizedMessage());
			return new ResponseEntity<RStatus>(status, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
        RStatus status = new RStatus();
        status.setStatusCode(200);
        status.setStatusMessage("Show Added Successfully");
        showDetails2.setRStatus(status);
        return new ResponseEntity<ShowDetails>(showDetails2, HttpStatus.OK);
	}
    
}
