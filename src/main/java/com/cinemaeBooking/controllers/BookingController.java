package com.cinemaeBooking.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cinemaeBooking.business.BookingDetails;
import com.cinemaeBooking.entities.Booking;
import com.cinemaeBooking.entities.BookingList;
import com.cinemaeBooking.entities.RStatus;
import com.cinemaeBooking.exception.CustomErrorsException;

@RestController
@RequestMapping("/checkout")
public class BookingController {

    @Autowired
    BookingDetails bookingDetails;

    @RequestMapping(value = "/submitOrder", method = RequestMethod.POST)
	public ResponseEntity<?> submitOrder(@RequestBody Booking booking)
	{
        Booking savedBooking = null;
        try {
            savedBooking = bookingDetails.submitOrder(booking);
        }
        catch (CustomErrorsException e) 
    	{
        	RStatus status = new RStatus();
            status.setStatusCode(400);
            status.setStatusMessage(e.getMessage());
			return new ResponseEntity<RStatus>(status, HttpStatus.BAD_REQUEST);
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

    @RequestMapping(value = "/orderHistory/{userName}", method = RequestMethod.GET)
    public ResponseEntity<?> orderHistory(@PathVariable String userName) {
        List<Booking> bookingList = new ArrayList<Booking>();
        try {
            bookingList = bookingDetails.findAllBookingsByUserName(userName);
            if(bookingList.isEmpty()) {
                RStatus status = new RStatus();
                status.setStatusCode(400);
                status.setStatusMessage("No order history");
                return new ResponseEntity<RStatus>(status, HttpStatus.BAD_REQUEST);
            }
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
        status.setStatusMessage("Order history of userName: " + userName);
        BookingList finalBookingList = new BookingList();
        finalBookingList.setBookingList(bookingList);
        finalBookingList.setRStatus(status);
        return new ResponseEntity<BookingList>(finalBookingList, HttpStatus.OK);
    }
}
