package com.cinemaeBooking.business;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinemaeBooking.entities.Movie;
import com.cinemaeBooking.service.UserMovieService;

@Service
public class UserMovieDetails {
    @Autowired
    UserMovieService userMovieService;

    public Set<Movie> getAllMovies() {
        Set<Movie> moviesList = new HashSet<Movie>();
        moviesList = userMovieService.getAllMovies();
        return moviesList;
    }
}
