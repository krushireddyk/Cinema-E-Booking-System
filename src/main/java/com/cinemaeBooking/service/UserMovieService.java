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
}
