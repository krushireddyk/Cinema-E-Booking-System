package com.cinemaeBooking.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cinemaeBooking.entities.Movie;
import com.cinemaeBooking.entities.Promotion;
import com.cinemaeBooking.entities.RStatus;
import com.cinemaeBooking.entities.User;
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

			return new ResponseEntity<RStatus>(status, HttpStatus.BAD_REQUEST);
        }
		catch(Exception e)
    	{
    		RStatus status = new RStatus();
            status.setStatusCode(400);
            status.setStatusMessage("Internal Error");

			return new ResponseEntity<RStatus>(status, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
		RStatus status = new RStatus();
        status.setStatusCode(200);
        status.setStatusMessage("Promo Added Successfully");
        
        promotion.setRStatus(status);
        return new ResponseEntity<Promotion>(promotion, HttpStatus.OK);
	}

	@RequestMapping(value = "/addShow/{title}", method = RequestMethod.PUT)
    public ResponseEntity<?> addShow(@PathVariable String title, @RequestBody Movie addMovieForm)
    {
    	Movie movie = new Movie();
    	try 
    	{
    		movie = adminMovieService.addShow(title, addMovieForm);
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
        
        movie.setRStatus(status);
        return new ResponseEntity<Movie>(movie, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/getUsers", method = RequestMethod.GET)
	public List<User> getUsers()
	{
		return adminMovieService.getAllUsers();
	}
	
	@RequestMapping(value = "/getPromotions", method = RequestMethod.GET)
	public List<Promotion> getPromotions()
	{
		return adminMovieService.getAllPromotions();
	}
	
	@RequestMapping(value = "/deletePromotion/{promotionCode}", method = RequestMethod.DELETE)
	public String deletePromotion(@PathVariable String promotionCode)
	{
		if(adminMovieService.deletePromotion(promotionCode)==true)
		{
			return promotionCode+" deleted successfully";
		}
		else
		{
			return promotionCode+" does not exists";
		}
	}
	
	@RequestMapping(value = "/deleteMovie/{title}", method = RequestMethod.DELETE)
	public String deleteMovie(@PathVariable String title)
	{
		if(adminMovieService.deleteMovie(title) == true)
		{
			return title+" deleted successfully";
		}
		else 
		{
			return title+" does not exists";
		}
	}
	
	@RequestMapping(value = "/suspendUser/{userName}", method = RequestMethod.PUT)
	public String suspendUser(@PathVariable String userName) throws SQLException
	{
		adminMovieService.suspendUser(userName);
		return "User Suspended Successfully";
	}
	
	@RequestMapping(value = "/sendPromotionalEmail/{promoCode}", method = RequestMethod.POST)
	public String sendPromotionalEmail(String promoCode)
	{
		promotionService.sendPromotionEmail(promoCode);
		return "Promotional email has been sent Successfully";
	}
	
}
