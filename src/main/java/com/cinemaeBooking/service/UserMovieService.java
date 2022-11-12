package com.cinemaeBooking.service;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinemaeBooking.entities.Movie;
import com.cinemaeBooking.repository.MovieRepository;

@Service
public class UserMovieService {
    @Autowired
    MovieRepository movieRepository;

    @Transactional
    public Set<Movie> getAllMovies() {
        Set<Movie> moviesList = new HashSet<Movie>();
        moviesList = movieRepository.findAll();
        return moviesList;
    }

    @Transactional
    public Set<Movie> getCurrentlyAndComingSoonMovies() {
        Set<Movie> moviesList = new HashSet<Movie>();
        moviesList = movieRepository.findAllCurrentlyRunningAndComingSoonMovies();
        return moviesList;
    }

    @Transactional
    public Set<Movie> getComingSoonMovies() {
        Set<Movie> moviesList = new HashSet<Movie>();
        moviesList = movieRepository.findAllComingSoonMovies();
        return moviesList;
    }

    @Transactional
    public Set<Movie> getCurrentMovies() {
        Set<Movie> moviesList = new HashSet<Movie>();
        moviesList = movieRepository.findAllCurrentMovies();
        return moviesList;
    }

    @Transactional
    public Movie getMovieByTitle(String title) {
        Movie movie = null;
        movie = movieRepository.findByTitle(title);
        return movie;
    }

    @Transactional
    public Set<Movie> getMovieByCategory(String category) {
        Set<Movie> moviesList = new HashSet<Movie>();
        moviesList = movieRepository.findByCategory(category);
        return moviesList;
    }

    @Transactional
    public Set<Movie> searchMovieByTitle(String title) {
        Set<Movie> moviesList = new HashSet<Movie>();
        moviesList = movieRepository.findByTitleContaining(title);
        return moviesList;
    }

    @Transactional
    public Set<Movie> searchMovieByCategory(String category) {
        Set<Movie> moviesList = new HashSet<Movie>();
        moviesList = movieRepository.findByCategoryContaining(category);
        return moviesList;
    }
}
