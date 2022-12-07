package com.cinemaeBooking.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cinemaeBooking.entities.PaymentCard;
import com.cinemaeBooking.entities.User;
import com.cinemaeBooking.service.CheckOutPageDetails;
import com.cinemaeBooking.service.EncryptDecrypt;
import com.cinemaeBooking.service.PromotionService;

@RestController
public class CheckOutController 
{
	@Autowired
	PromotionService promotionService;
	@Autowired
	CheckOutPageDetails checkOutPageDetails;
	@Autowired
	EncryptDecrypt encryptDecrypt;
	
	@RequestMapping(value = "/verifyPromoCodegetValue/{promoCode}", method = RequestMethod.GET)
	public float promotionalValue(@PathVariable String promoCode)
	{
		return promotionService.verifyPromotion(promoCode);
	}
	
	@RequestMapping(value="/getCheckoutDetails/{userName}", method = RequestMethod.GET)
	public User checkOutDetails(@PathVariable String userName)
	{
		User finalUser = checkOutPageDetails.getUserCheckoutDetails(userName);
		Set<PaymentCard> savedDecryptedPaymentCards = new HashSet<PaymentCard>();	
		for(PaymentCard paymentCard : finalUser.getPaymentCards())
		{
			PaymentCard paymentCardDecrypted = new PaymentCard();
			paymentCardDecrypted.setAddress(paymentCard.getAddress());
            paymentCardDecrypted.setCardNumber(encryptDecrypt.decrypt(paymentCard.getCardNumber()));
            paymentCardDecrypted.setExpiryDate(paymentCard.getExpiryDate());
            paymentCardDecrypted.setPaymentID(paymentCard.getPaymentID());
            savedDecryptedPaymentCards.add(paymentCardDecrypted);
		}
		finalUser.setPaymentCards(savedDecryptedPaymentCards);
		return finalUser;
	}
}

