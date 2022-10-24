package com.cinemaeBooking.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cinemaeBooking.entities.User;
import com.cinemaeBooking.repository.UserRepository;

@Service
public class UserDetailsServiceImplementation implements org.springframework.security.core.userdetails.UserDetailsService 
{

	@Autowired
	private UserRepository userRepository;
	
	@SuppressWarnings("null")
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		User user = userRepository.findByUserName(username);
		if(user != null)
		{
			throw new UsernameNotFoundException(username);
		}
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		//grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), grantedAuthorities);
	}

}
