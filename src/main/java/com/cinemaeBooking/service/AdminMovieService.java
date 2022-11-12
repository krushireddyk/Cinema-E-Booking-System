package com.cinemaeBooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.cinemaeBooking.entities.Movie;
import com.cinemaeBooking.exception.CustomErrorsException;
import com.cinemaeBooking.repository.MovieRepository;

@Service
public class AdminMovieService 
{
	@Autowired
	MovieRepository movieRepository;
	
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
			movie.setPicture(addMovieForm.getPicture());
			movie.setRating(3); // work on this
			System.out.println(addMovieForm.getCategory());
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
}
