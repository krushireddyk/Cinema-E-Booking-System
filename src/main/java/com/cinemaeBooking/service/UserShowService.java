package com.cinemaeBooking.service;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinemaeBooking.entities.Movie;
import com.cinemaeBooking.entities.ShowDetails;
import com.cinemaeBooking.exception.CustomErrorsException;
import com.cinemaeBooking.repository.MovieRepository;
import com.cinemaeBooking.repository.ShowRepository;

@Service
public class UserShowService {

    @Autowired
	MovieRepository movieRepository;
    
    @Autowired
	ShowRepository showRepository;

    @Transactional
	public List<ShowDetails> getShowDetailsByTitleAndShowDate(Movie movie)
	{
        Movie existingMovie = movieRepository.findByTitle(movie.getTitle());
        if(existingMovie == null) {
            throw new CustomErrorsException("Invalid Movie title");
        }
        Set<ShowDetails> showDetailsList = movie.getShowdetails();
        if(showDetailsList.isEmpty()){
            throw new CustomErrorsException("Please provide valid show details");
        }
		return showRepository.findByTitleAndShowDate(movie.getTitle(), showDetailsList.iterator().next().getShowId().getShowDate());
	}
}
