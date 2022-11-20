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
		Promotion promotion = new Promotion();
		Promotion changedPromotion = null;
		try
		{
			if (bindingResult.hasErrors()) 
            {
				return null;
            }
			
			promotionRepository.deleteByPromotionCode(editPromoForm.getPromotionCode());
			
			promotion.setPromotionCode(editPromoForm.getPromotionCode());
			promotion.setPromotional_Value(editPromoForm.getPromotional_Value());
			promotion.setStartDate(editPromoForm.getStartDate());
			promotion.setEndDate(editPromoForm.getEndDate());
			
			changedPromotion = promotionRepository.save(promotion);
		}
		finally
		{
			
		}		
		return changedPromotion;
		
	}
}
