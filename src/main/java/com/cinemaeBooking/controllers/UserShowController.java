package com.cinemaeBooking.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cinemaeBooking.entities.Movie;
import com.cinemaeBooking.entities.RStatus;
import com.cinemaeBooking.entities.ShowDetails;
import com.cinemaeBooking.entities.ShowDetailsList;
import com.cinemaeBooking.exception.CustomErrorsException;
import com.cinemaeBooking.service.UserShowService;

@RestController
public class UserShowController {

    @Autowired
    UserShowService userShowService;

    @RequestMapping(value = "/getShowDetailsByTitleAndShowDate", method = RequestMethod.GET)
    public ResponseEntity<?> getShowDetailsByTitleAndShowDate(@RequestParam("title") String title, @RequestParam("showDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date showDate) {
        ShowDetailsList showDetailsList2 = new ShowDetailsList();
        List<ShowDetails> showDetailsList = new ArrayList<ShowDetails>();
        try {    
            showDetailsList = userShowService.getShowDetailsByTitleAndShowDate(title, showDate);
            if(showDetailsList.isEmpty()) {
                RStatus status = new RStatus();
                status.setStatusCode(400);
                status.setStatusMessage("No shows for this movie: " + title);
                return new ResponseEntity<RStatus>(status, HttpStatus.BAD_REQUEST);
            }
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
            status.setStatusMessage("Internal Error");
			return new ResponseEntity<RStatus>(status, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
        RStatus status = new RStatus();
        status.setStatusCode(200);
        status.setStatusMessage("Show details for the movie: "+ title);
        showDetailsList2.setRStatus(status);
        showDetailsList2.setShowDetailsList(showDetailsList);
        return new ResponseEntity<ShowDetailsList>(showDetailsList2, HttpStatus.OK);
    }
}
