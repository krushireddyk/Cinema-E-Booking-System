package com.cinemaeBooking.service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.cinemaeBooking.dbutil.DbUtil;
import com.cinemaeBooking.entities.Promotion;
import com.cinemaeBooking.exception.CustomErrorsException;
import com.cinemaeBooking.repository.PromotionRepository;
import com.cinemaeBooking.serviceIMPL.EmailService;

@Service
public class PromotionService {
	
	@Autowired
	PromotionRepository promotionRepository;
	
	@Autowired
	EmailService emailService;
	
	public Promotion addPromotion(Promotion addPromoForm, BindingResult bindingResult) throws CustomErrorsException
	{
		Promotion promotion = new Promotion();
		Promotion savedPromotion = null;
		try
		{
			if (bindingResult.hasErrors()) 
            {
				return null;
            }
			
			if(addPromoForm.getStartDate().compareTo(addPromoForm.getEndDate()) > 0) {
				throw new CustomErrorsException("End date must be after start date");
			}
			promotion.setPromotionCode(addPromoForm.getPromotionCode());
			promotion.setPromotional_Value(addPromoForm.getPromotional_Value());
			promotion.setStartDate(addPromoForm.getStartDate());
			promotion.setEndDate(addPromoForm.getEndDate());
			
			savedPromotion = promotionRepository.save(promotion);
			
			/*Connection connection;
			try 
			{
				connection = DbUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement("select EmailID from user where promotionEnabled=1;");
				ResultSet resultSet = statement.executeQuery();	
				
				while(resultSet.next())
				{		
					emailService.sendPromotionalEmail(resultSet.getString(1), savedPromotion.getPromotionCode());
				}
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}*/
		}
		catch(DataIntegrityViolationException e)
		{
			if(e.getLocalizedMessage().contains("PromotionCode"))
			{
                throw new CustomErrorsException("Promo code already exists");
			}
			
		}
		finally
		{
			
		}		
		return savedPromotion;
	}
	
	public void sendPromotionEmail(String promoCode)
	{
		Connection connection;
		try 
		{
			connection = DbUtil.getConnection();
			PreparedStatement statement = connection.prepareStatement("select EmailID from user where promotionEnabled=1;");
			ResultSet resultSet = statement.executeQuery();	
			
			while(resultSet.next())
			{		
				emailService.sendPromotionalEmail(resultSet.getString(1), promoCode);
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public Promotion editPromotion(Promotion editPromoForm, BindingResult bindingResult)
	{
		Promotion changedPromotion = null;
		Promotion existingPromotion;
		try
		{
			if (bindingResult.hasErrors()) 
            {
				return null;
            }
			
			existingPromotion = promotionRepository.findByPromotionCode(editPromoForm.getPromotionCode());
			
			existingPromotion.setPromotionCode(editPromoForm.getPromotionCode());//only if we get the promo code in edit promo form
			existingPromotion.setStartDate(editPromoForm.getStartDate());
			existingPromotion.setEndDate(editPromoForm.getEndDate());
			existingPromotion.setPromotional_Value(editPromoForm.getPromotional_Value());			
			changedPromotion = promotionRepository.save(existingPromotion);
		}
		finally
		{
			
		}		
		return changedPromotion;
		
	}
	
	public float verifyPromotion(String promocode)
	{
		Promotion promotion;
		promotion = promotionRepository.findByPromotionCode(promocode);
		if(promotion!=null)
		{
			return promotion.getPromotional_Value();
		}
		else
		{
			return 0;
		}
	}
}

