package com.cinemaeBooking.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cinemaeBooking.business.UserMovieDetails;
import com.cinemaeBooking.entities.Movie;
import com.cinemaeBooking.entities.RStatus;

@RestController
@RequestMapping("/user")
public class UserMovieController {

    @Autowired
    UserMovieDetails userMovieDetails;
    
    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public ResponseEntity<?> getAllMovies() {
        Set<Movie> moviesList = new HashSet<Movie>();
        moviesList = userMovieDetails.getAllMovies();
        if(moviesList == null) {
            RStatus status = new RStatus();
            status.setStatusCode(400);
            status.setStatusMessage("Empty movie list");
            return new ResponseEntity<RStatus>(status, HttpStatus.OK);
        }
        RStatus status = new RStatus();
        status.setStatusCode(200);
        status.setStatusMessage("Get All Movie Details");
        Set<Movie> moviesStatusList = new HashSet<Movie>();
        for(Movie movie : moviesList){
            movie.setRStatus(status);
            moviesStatusList.add(movie);
         }
        return new ResponseEntity<Set<Movie>>(moviesList, HttpStatus.OK);
    }
}
