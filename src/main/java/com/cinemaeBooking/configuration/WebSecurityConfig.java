/*package com.cinemaeBooking.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//import com.cinemaeBooking.serviceIMPL.CustomerServiceIMPL;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
	//@Autowired
	//private CustomerServiceIMPL customerService;
	
	@Autowired
	private UserAuthenticationHandler userAuthenticationHandler;
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() 
    {
        return new BCryptPasswordEncoder();
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception 
    {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/signin", "/signup","/signupconfirm","/orderconfirmation").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/signin").permitAll()
                .successHandler(userAuthenticationHandler)
                .and()
                .logout().invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/signout"))
                .logoutSuccessUrl("/signout-success").permitAll();
    }

	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception 
    {
        //auth.userDetailsService(customerService).passwordEncoder(bCryptPasswordEncoder());
    }
}*/
