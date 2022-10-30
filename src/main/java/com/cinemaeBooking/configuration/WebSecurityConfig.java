package com.cinemaeBooking.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.google.common.collect.ImmutableList;

//import com.cinemaeBooking.serviceIMPL.CustomerServiceIMPL;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
	//@Autowired
	//private CustomerServiceIMPL customerService;
	
	//@Autowired
	//private UserAuthenticationHandler userAuthenticationHandler;
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() 
    {
        return new BCryptPasswordEncoder();
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception 
    {
        http.cors().and().csrf().disable();
        //http.csrf().disable();
        /*.authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers(HttpMethod.GET,"/signin").permitAll()
        .antMatchers(HttpMethod.GET, "/signup").permitAll()
        .antMatchers(HttpMethod.GET,"/editProfile/*").permitAll()
        .antMatchers(HttpMethod.GET,"/editProfile/update/*").permitAll()
        .antMatchers(HttpMethod.GET,"/signupconfirm").permitAll()
         .antMatchers(HttpMethod.GET,"/orderconfirmation").permitAll()
        .anyRequest().authenticated();
        /*http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/signin", "/signup","/signupconfirm","/orderconfirmation", "/getProfile").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/signin").permitAll()
                //.successHandler(userAuthenticationHandler)
                .and()
                .logout().invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/signout"))
                .logoutSuccessUrl("/signout-success").permitAll();*/
    }

	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception 
    {
        //auth.userDetailsService(customerService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}