package com.cinemaeBooking.serviceIMPL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.cinemaeBooking.dbutil.DbUtil;
import com.cinemaeBooking.service.UserService;

@Service
public class UserServiceImplementation implements UserService
{
	Connection connection;
	int flag = 0;
	int adminFlag = 0;
	
	public UserServiceImplementation() throws SQLException
	{
		connection = DbUtil.getConnection();
	}

	@Override
	public int loginValidation(String userName, String password) 
	{
		try 
		{
			PreparedStatement statement = connection.prepareStatement("Select * from user where UserName='"+userName+"'");
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				if(resultSet.getString(4).equals(userName) && resultSet.getString(7).equals(password))
				{
					if(resultSet.getInt(8)==1 && resultSet.getInt(9)==2)
					{
						flag = 1;
					}
					else 
					{
						System.out.println("User is not verified/User is not authorized to access");
						flag = 0;
					}
				}
				else
				{
					System.out.println("Invalid Username or Password");
					flag = 0;
				}
			}			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return flag;
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
}
