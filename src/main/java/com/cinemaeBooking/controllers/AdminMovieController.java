package com.cinemaeBooking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cinemaeBooking.entities.Movie;
import com.cinemaeBooking.entities.Promotion;
import com.cinemaeBooking.entities.RStatus;
import com.cinemaeBooking.exception.CustomErrorsException;
import com.cinemaeBooking.service.AdminMovieService;
import com.cinemaeBooking.service.PromotionService;

@RestController
public class AdminMovieController {

	@Autowired
	AdminMovieService adminMovieService;
	
	@Autowired
	PromotionService promotionService;
	
	@RequestMapping(value = "/addMovie", method = RequestMethod.POST)
    public ResponseEntity<?> addMovie(@RequestBody Movie addMovieForm, BindingResult bindingResult)
    {
    	Movie movie = new Movie();
    	System.out.println(addMovieForm.getCategory());
    	try 
    	{
    		movie = adminMovieService.addMovie(addMovieForm, bindingResult);
        } 
        catch (CustomErrorsException e) 
    	{
        	RStatus status = new RStatus();
            status.setStatusCode(400);
            status.setStatusMessage(e.getMessage());
        }
    	catch(Exception e)
    	{
    		RStatus status = new RStatus();
            status.setStatusCode(400);
            status.setStatusMessage("Internal Error");
    	}
    	RStatus status = new RStatus();
        status.setStatusCode(200);
        status.setStatusMessage("Movie Added Successfully");
        
        movie.setRStatus(status);
        return new ResponseEntity<Movie>(movie, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/addPromotion", method = RequestMethod.POST)
	public ResponseEntity<?> addPromotion(@RequestBody Promotion addPromoForm, BindingResult bindingResult)
	{
		Promotion promotion = new Promotion();
		try
		{
			promotion = promotionService.addPromotion(addPromoForm, bindingResult);		
		}
		catch (CustomErrorsException e) 
    	{
        	RStatus status = new RStatus();
            status.setStatusCode(400);
            status.setStatusMessage(e.getMessage());
        }
		catch(Exception e)
    	{
    		RStatus status = new RStatus();
            status.setStatusCode(400);
            status.setStatusMessage("Internal Error");
    	}
		RStatus status = new RStatus();
        status.setStatusCode(200);
        status.setStatusMessage("Promo Added Successfully");
        
        promotion.setRStatus(status);
        return new ResponseEntity<Promotion>(promotion, HttpStatus.OK);
	}
}
