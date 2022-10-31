package com.cinemaeBooking.serviceIMPL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinemaeBooking.dbutil.DbUtil;
import com.cinemaeBooking.entities.Status;
import com.cinemaeBooking.entities.User;
import com.cinemaeBooking.entities.UserType;
import com.cinemaeBooking.service.EncryptDecrypt;
import com.cinemaeBooking.service.UserService;


@Service
public class UserServiceImplementation implements UserService
{
	@Autowired
	EncryptDecrypt encryptDecrypt;
	
	Connection connection;
	int flag = 0;
	int adminFlag = 0;
	
	public UserServiceImplementation() throws SQLException
	{
		connection = DbUtil.getConnection();
	}

	@Override
	public User loginValidation(String userName, String password) 
	{
		User user = new User();
		try 
		{
			PreparedStatement statement = connection.prepareStatement("Select * from user where UserName='"+userName+"' and password='"+encryptDecrypt.encrypt(password)+"'");
			ResultSet resultSet = statement.executeQuery();	

			while(resultSet.next())
			{
				Status status = new Status();
				UserType ut = new UserType();
				user.setUserName(resultSet.getString(4));
				user.setPassword(resultSet.getString(7));
				status.setStatusID(resultSet.getInt(8));
				user.setStatus(status);
				ut.setRoleID(resultSet.getInt(9));
				user.setUsertype(ut);
			}			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public int adminLoginValidation(String userName, String password) 
	{
		try 
		{
			PreparedStatement statement = connection.prepareStatement("Select * from user where UserName='"+userName+"'");
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				if(resultSet.getString(4).equals(userName) && resultSet.getString(7).equals(password))
				{
					if(resultSet.getInt(8)==1 && resultSet.getInt(9)==1)
					{
						adminFlag = 1;
					}
					else 
					{
						System.out.println("User is not verified/User is not authorized to access");
						adminFlag = 0;
					}
				}
				else
				{
					System.out.println("Invalid Username or Password");
					adminFlag = 0;
				}
			}			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return adminFlag;
	}

	@Override
	public String getVerificationCode(String un) throws SQLException
	{	
		String vcode = null;
		try 
		{
			PreparedStatement statement;
			statement = connection.prepareStatement("Select * from user where UserName='"+un+"'");
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				if(resultSet.getString(4)==un)
				{
					vcode = resultSet.getString(10);
					break;
				}
			}
		} 
		catch (SQLException e) 
		{
			throw e;
		}
		return vcode;	
	}

	@Override
	public int verifyVerificationCode(User user) 
	{
		User updatedUser = new User();
		try 
		{
			PreparedStatement statement = connection.prepareStatement("update user set statusID=1 where UserName='"+user.getUserName()+"' and verificationCode='"+user.getVerificationCode()+"'");
			int resultSet = statement.executeUpdate();

			System.out.println(resultSet);
			if(resultSet > 0) {
				return 1;
			}
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return 0;

	}
}
