package com.cinemaeBooking.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

	private AuthenticationManager authenticationManager;
	private UserDetailsService userDetailsService;
	
	public String findLoggedInUsername()
	{
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(userDetails instanceof User)
		{
			return ((UserDetails)userDetails).getUsername();
		}
		return null;
	}
	
	public String Login(String username, String password)
	{
		try 
        {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

            authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            if (usernamePasswordAuthenticationToken.isAuthenticated()) 
            {
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

            if (userDetails.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ADMIN")))
            {
                return "redirect:/admin";
            }
            else
            {
                return "redirect:/home";
            }
        }
		
		catch (UsernameNotFoundException e) 
        {
            return "redirect:/login?error";
        }
		
		catch(BadCredentialsException b) 
        {
            return "redirect:/login?error";
        }
		

	}
}
