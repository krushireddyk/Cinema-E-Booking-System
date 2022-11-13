package com.cinemaeBooking.business;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.cinemaeBooking.entities.Movie;
import com.cinemaeBooking.entities.RStatus;
import com.cinemaeBooking.exception.CustomErrorsException;
import com.cinemaeBooking.repository.MovieRepository;
import com.cinemaeBooking.service.UserMovieService;

@Service
public class UserMovieDetails {
    @Autowired
    UserMovieService userMovieService;
    
    @Autowired
    MovieRepository movieRepository;

    public Set<Movie> getAllMovies() {
        Set<Movie> moviesList = new HashSet<Movie>();
        moviesList = userMovieService.getAllMovies();
        return moviesList;
    }

    public Set<Movie> getCurrentlyAndComingSoonMovies() {
        Set<Movie> moviesList = new HashSet<Movie>();
        moviesList = userMovieService.getCurrentlyAndComingSoonMovies();
        return moviesList;
    }

    public Set<Movie> getComingSoonMovies() {
        Set<Movie> moviesList = new HashSet<Movie>();
        moviesList = userMovieService.getComingSoonMovies();
        return moviesList;
    }

    public Set<Movie> getCurrentMovies() {
        Set<Movie> moviesList = new HashSet<Movie>();
        moviesList = userMovieService.getCurrentMovies();
        return moviesList;
    }

    public Movie getMovieByTitle(String title) {
        Movie movie = null;
        movie = userMovieService.getMovieByTitle(title);
        return movie;
    }

    public Set<Movie> getMovieByCategory(String category) {
        Set<Movie> moviesList = new HashSet<Movie>();
        moviesList = userMovieService.getMovieByCategory(category);
        return moviesList;
    }

    public Set<Movie> searchMovieByTitle(String title) {
        Set<Movie> moviesList = new HashSet<Movie>();
        moviesList = userMovieService.searchMovieByTitle(title);
        return moviesList;
    }

    public Set<Movie> searchMovieByCategory(String category) {
        Set<Movie> moviesList = new HashSet<Movie>();
        moviesList = userMovieService.searchMovieByCategory(category);
        return moviesList;
    }
    
}
