package com.cinemaeBooking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cinemaeBooking.entities.Booking;
import com.cinemaeBooking.entities.RStatus;
import com.cinemaeBooking.service.BookingService;

@RestController
@RequestMapping("/checkout")
public class BookingController {

    @Autowired
    BookingService bookingService;
    
    @RequestMapping(value = "/submitOrder", method = RequestMethod.POST)
	public ResponseEntity<?> submitOrder(@RequestBody Booking booking)
	{
        Booking savedBooking = null;
        try {
            savedBooking = bookingService.submitOrder(booking);
        }
        catch(Exception e) {
            e.printStackTrace();
            RStatus status = new RStatus();
            status.setStatusCode(500);
            status.setStatusMessage(e.getLocalizedMessage());
            return new ResponseEntity<RStatus>(status, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        RStatus status = new RStatus();
        status.setStatusCode(200);
        status.setStatusMessage("Booking submitted sucessfully");
        savedBooking.setRStatus(status);
        return new ResponseEntity<Booking>(savedBooking, HttpStatus.OK);
    }
}
