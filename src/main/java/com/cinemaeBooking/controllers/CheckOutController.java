package com.cinemaeBooking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cinemaeBooking.entities.User;
import com.cinemaeBooking.service.CheckOutPageDetails;
import com.cinemaeBooking.service.PromotionService;

@RestController
public class CheckOutController 
{
	@Autowired
	PromotionService promotionService;
	@Autowired
	CheckOutPageDetails checkOutPageDetails;
	
	@RequestMapping(value = "/verifyPromoCodegetValue/{promoCode}", method = RequestMethod.GET)
	public float promotionalValue(@PathVariable String promoCode)
	{
		return promotionService.verifyPromotion(promoCode);
	}
	
	@RequestMapping(value="/getCheckoutDetails/{userName}", method = RequestMethod.GET)
	public User checkOutDetails(@PathVariable String userName)
	{
		return checkOutPageDetails.getUserCheckoutDetails(userName);
	}
}
