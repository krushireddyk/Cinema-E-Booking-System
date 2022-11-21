package com.cinemaeBooking.service;

import java.util.Base64;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.cinemaeBooking.dbutil.DbUtil;
import com.cinemaeBooking.entities.Movie;
import com.cinemaeBooking.entities.Promotion;
import com.cinemaeBooking.entities.Screen;
import com.cinemaeBooking.entities.ShowDetails;
import com.cinemaeBooking.entities.ShowId;
import com.cinemaeBooking.entities.User;
import com.cinemaeBooking.exception.CustomErrorsException;
import com.cinemaeBooking.repository.MovieRepository;
import com.cinemaeBooking.repository.PromotionRepository;
import com.cinemaeBooking.repository.ScreenRepository;
import com.cinemaeBooking.repository.UserRepository;

@Service
public class AdminMovieService 
{
	
	@Autowired
	MovieRepository movieRepository;

	@Autowired
	ScreenRepository screenRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PromotionRepository promotionRepository;
	
	@Transactional
	public Movie addMovie(Movie addMovieForm, BindingResult bindingResult) throws Exception
	{
		Movie movie = new Movie();
		Movie savedMovie = null;
		try
		{
			if (bindingResult.hasErrors()) 
            {
                throw new CustomErrorsException("Invalid movie Details");
            }
			
			movie.setTitle(addMovieForm.getTitle());
			movie.setCategory(addMovieForm.getCategory());
			movie.setMovieCast(addMovieForm.getMovieCast());
			movie.setDirector(addMovieForm.getDirector());
			movie.setProducer(addMovieForm.getProducer());
			movie.setSynopsis(addMovieForm.getSynopsis());
			movie.setReview("Good"); // work on this
			movie.setTrailerLink(addMovieForm.getTrailerLink());
			String imageData = addMovieForm.getBase64().split(";base64,", 2)[1];
			byte [] encodedBytes = Base64.getMimeDecoder().decode(imageData);
			movie.setPicture(encodedBytes);
			movie.setRating(3); // work on this
			savedMovie = movieRepository.save(movie);
		}
		catch(DataIntegrityViolationException e)
		{
			//RStatus status = new RStatus();
			if(e.getLocalizedMessage().contains("Title"))
			{
                throw new CustomErrorsException("Movie title is already registered (or) Movie title already exists");
			}
			
		}
		return savedMovie;
	}

	@Transactional
	public Movie editMovie(String title, Movie addMovieForm) throws Exception
	{
		Movie movie = movieRepository.findByTitle(title);
		if(movie == null) {
			throw new CustomErrorsException("Invalid movie title");
		}

		Movie savedMovie = null;
		
		Set<ShowDetails> oldShowDetails = movie.getShowdetails();
		
		Set<ShowDetails> updatedShowDetails = new HashSet<ShowDetails>();
		
		for(ShowDetails show : addMovieForm.getShowdetails()) 
		{
			for(ShowDetails oldShow : oldShowDetails) 
			{
				if(show.getShowId() != null) {
					if(show.getShowId().getScreen() != null) {
						System.out.println(show.getShowId().getScreen().getScreenID());
						System.out.println(oldShow.getShowId().getScreen().getScreenID());
						if(show.getShowId().getScreen().getScreenID().equals(oldShow.getShowId().getScreen().getScreenID())) {
							if(oldShow.getShowId().getShowDate().compareTo(show.getShowId().getShowDate()) == 0) {
								if(oldShow.getShowId().getShowTime().compareTo(show.getShowId().getShowTime()) == 0) {
									throw new CustomErrorsException("This show slot is already booked");
								}
							}
						}
					}
					
				}
			}
			ShowDetails showDetail = new ShowDetails();
			//showDetail.setShowDate(show.getShowDate());
			showDetail.setShowDuration(show.getShowDuration());
			//showDetail.setShowTime(show.getShowTime());
			Screen screen = screenRepository.findByScreenID(show.getShowId().getScreen().getScreenID());
			if(screen == null) 
			{
				throw new CustomErrorsException("Please provide valid/existing screen details");
			}
			ShowId showId = new ShowId();
			showId.setShowDate(show.getShowId().getShowDate());
			showId.setShowTime(show.getShowId().getShowTime());
			showId.setScreen(show.getShowId().getScreen());
			showDetail.setShowId(showId);
			showDetail.setMovie(movie);
			updatedShowDetails.add(showDetail);
		}
		updatedShowDetails.addAll(oldShowDetails);
		movie.setShowdetails(updatedShowDetails);
		savedMovie = movieRepository.save(movie);
		return savedMovie;
	}
	
	@Transactional
	public List<User> getAllUsers()
	{
		return userRepository.findAll();
	}
	
	@Transactional
	public List<Promotion> getAllPromotions()
	{
		return promotionRepository.findAll();
	}
	
	@Transactional
	public boolean deletePromotion(String promotionCode)
	{
		if(promotionRepository.findByPromotionCode(promotionCode)==null)
		{
			return false;
		}
		promotionRepository.deleteByPromotionCode(promotionCode);
		return true;
	}
	
	@Transactional
	public boolean deleteMovie(String title)
	{
		if(movieRepository.findByTitle(title)==null)
		{
			return false;
		}
		movieRepository.deleteByTitle(title);
		return true;
	}
	
	@Transactional
	public void suspendUser(String userName) throws SQLException
	{
		Connection connection;
		try
		{
			connection = DbUtil.getConnection();
			PreparedStatement statement = connection.prepareStatement("UPDATE user set StatusID=3 where UserName='"+userName+"' ");
			statement.execute();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}		
}
