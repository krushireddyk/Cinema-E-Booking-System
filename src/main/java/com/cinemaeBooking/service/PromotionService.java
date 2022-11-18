package com.cinemaeBooking.service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.cinemaeBooking.dbutil.DbUtil;
import com.cinemaeBooking.entities.Promotion;
import com.cinemaeBooking.repository.PromotionRepository;
import com.cinemaeBooking.serviceIMPL.EmailService;

@Service
public class PromotionService {
	
	@Autowired
	PromotionRepository promotionRepository;
	
	@Autowired
	EmailService emailService;
	
	public Promotion addPromotion(Promotion addPromoForm, BindingResult bindingResult)
	{
		Promotion promotion = new Promotion();
		Promotion savedPromotion = null;
		try
		{
			if (bindingResult.hasErrors()) 
            {
				return null;
            }
			
			promotion.setPromotionCode(addPromoForm.getPromotionCode());
			promotion.setPromotional_Value(addPromoForm.getPromotional_Value());
			promotion.setStartDate(addPromoForm.getStartDate());
			promotion.setEndDate(addPromoForm.getEndDate());
			
			savedPromotion = promotionRepository.save(promotion);
			
			Connection connection;
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
			}
		}
		finally
		{
			
		}		
		return savedPromotion;
	}
	
}
