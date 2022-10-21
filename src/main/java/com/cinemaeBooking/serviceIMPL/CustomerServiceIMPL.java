package com.cinemaeBooking.serviceIMPL;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cinemaeBooking.entities.Customer;
import com.cinemaeBooking.repository.CustomerRepository;
import com.cinemaeBooking.service.CustomerService;

public class CustomerServiceIMPL implements CustomerService,UserDetailsService
{

	private CustomerRepository customerRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public void save(Customer customer) 
	{
		customer.setAccountStatus("INACTIVE");
		customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
		customerRepository.save(customer);	
	}

	@Override
	public Customer findByUserId(int userID) 
	{
		return customerRepository.findByUserId(userID);
	}

	@Override
	public Customer findByUserName(String userName) 
	{
		return customerRepository.findByUserName(userName);
	}
	

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException 
	{
		Customer customer = customerRepository.findByUserName(userName);
		if(customer == null)
		{
			return null;
		}
		List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(customer.getRole());
        String password = customer.getPassword();
        return new User(userName, password, auth);
	}

}
