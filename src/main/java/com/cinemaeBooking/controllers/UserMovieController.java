package com.cinemaeBooking.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cinemaeBooking.business.UserMovieDetails;
import com.cinemaeBooking.entities.Movie;
import com.cinemaeBooking.entities.MoviesList;
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
        if(moviesList.isEmpty()) {
            RStatus status = new RStatus();
            status.setStatusCode(400);
            status.setStatusMessage("Empty movie list");
            return new ResponseEntity<RStatus>(status, HttpStatus.BAD_REQUEST);
        }
        RStatus status = new RStatus();
        status.setStatusCode(200);
        status.setStatusMessage("Get All Movie Details");
        MoviesList moviesList2 = new MoviesList();
        moviesList2.setMoviesList(moviesList);
        moviesList2.setRStatus(status);
        return new ResponseEntity<MoviesList>(moviesList2, HttpStatus.OK);
    }

    @RequestMapping(value = "/movies/currentAndComingSoon", method = RequestMethod.GET)
    public ResponseEntity<?> getCurrentlyAndComingSoonMovies() {
        Set<Movie> moviesList = new HashSet<Movie>();
        moviesList = userMovieDetails.getCurrentlyAndComingSoonMovies();
        if(moviesList.isEmpty()) {
            RStatus status = new RStatus();
            status.setStatusCode(400);
            status.setStatusMessage("Empty movie list");
            return new ResponseEntity<RStatus>(status, HttpStatus.BAD_REQUEST);
        }
        RStatus status = new RStatus();
        status.setStatusCode(200);
        status.setStatusMessage("Get Current and Coming Soon Movie Details");
        MoviesList moviesList2 = new MoviesList();
        moviesList2.setMoviesList(moviesList);
        moviesList2.setRStatus(status);
        return new ResponseEntity<MoviesList>(moviesList2, HttpStatus.OK);
    }

    @RequestMapping(value = "/movies/comingSoon", method = RequestMethod.GET)
    public ResponseEntity<?> getComingSoonMovies() {
        Set<Movie> moviesList = new HashSet<Movie>();
        moviesList = userMovieDetails.getComingSoonMovies();
        if(moviesList.isEmpty()) {
            RStatus status = new RStatus();
            status.setStatusCode(400);
            status.setStatusMessage("Empty movie list");
            return new ResponseEntity<RStatus>(status, HttpStatus.BAD_REQUEST);
        }
        RStatus status = new RStatus();
        status.setStatusCode(200);
        status.setStatusMessage("Get Coming Soon Movie Details");
        MoviesList moviesList2 = new MoviesList();
        moviesList2.setMoviesList(moviesList);
        moviesList2.setRStatus(status);
        
        return new ResponseEntity<MoviesList>(moviesList2, HttpStatus.OK);
    }

    @RequestMapping(value = "/movies/current", method = RequestMethod.GET)
    public ResponseEntity<?> getCurrentMovies() {
        Set<Movie> moviesList = new HashSet<Movie>();
        moviesList = userMovieDetails.getCurrentMovies();
        if(moviesList.isEmpty()) {
            RStatus status = new RStatus();
            status.setStatusCode(400);
            status.setStatusMessage("Empty movie list");
            return new ResponseEntity<RStatus>(status, HttpStatus.BAD_REQUEST);
        }
        RStatus status = new RStatus();
        status.setStatusCode(200);
        status.setStatusMessage("Get Current Movie Details");
        MoviesList moviesList2 = new MoviesList();
        moviesList2.setMoviesList(moviesList);
        moviesList2.setRStatus(status);
        return new ResponseEntity<MoviesList>(moviesList2, HttpStatus.OK);
    }

    @RequestMapping(value = "/movieByTitle/{title}", method = RequestMethod.GET)
    public ResponseEntity<?> getMovieByTitle(@PathVariable String title) {
        Movie movie = userMovieDetails.getMovieByTitle(title);
        if(movie == null) {
            RStatus status = new RStatus();
            status.setStatusCode(400);
            status.setStatusMessage("Movie Not found");
            return new ResponseEntity<RStatus>(status, HttpStatus.BAD_REQUEST);
        }
        RStatus status = new RStatus();
        status.setStatusCode(200);
        status.setStatusMessage("Movie Found");
        movie.setRStatus(status);
        return new ResponseEntity<Movie>(movie, HttpStatus.OK);
    }

    @RequestMapping(value = "/movieByCategory/{category}", method = RequestMethod.GET)
    public ResponseEntity<?> getMovieByCategory(@PathVariable String category) {
        Set<Movie> moviesList = new HashSet<Movie>();
        moviesList = userMovieDetails.getMovieByCategory(category);
        if(moviesList.isEmpty()) {
            RStatus status = new RStatus();
            status.setStatusCode(400);
            status.setStatusMessage("Empty movie list");
            return new ResponseEntity<RStatus>(status, HttpStatus.BAD_REQUEST);
        }
        RStatus status = new RStatus();
        status.setStatusCode(200);
        status.setStatusMessage("Get movies by category");
        MoviesList moviesList2 = new MoviesList();
        moviesList2.setMoviesList(moviesList);
        moviesList2.setRStatus(status);
        return new ResponseEntity<MoviesList>(moviesList2, HttpStatus.OK);
    }

    @RequestMapping(value = "/searchByTitle/{title}", method = RequestMethod.GET)
    public ResponseEntity<?> searchMovieByTitle(@PathVariable String title) {
        Set<Movie> moviesList = new HashSet<Movie>();
        moviesList = userMovieDetails.searchMovieByTitle(title);
        if(moviesList.isEmpty()) {
            RStatus status = new RStatus();
            status.setStatusCode(400);
            status.setStatusMessage("Empty movie list");
            return new ResponseEntity<RStatus>(status, HttpStatus.BAD_REQUEST);
        }
        RStatus status = new RStatus();
        status.setStatusCode(200);
        status.setStatusMessage("Search movies by title");
        MoviesList moviesList2 = new MoviesList();
        moviesList2.setMoviesList(moviesList);
        moviesList2.setRStatus(status);
        return new ResponseEntity<MoviesList>(moviesList2, HttpStatus.OK);
    }

    @RequestMapping(value = "/searchByCategory/{category}", method = RequestMethod.GET)
    public ResponseEntity<?> searchMovieByCategory(@PathVariable String category) {
        Set<Movie> moviesList = new HashSet<Movie>();
        moviesList = userMovieDetails.searchMovieByCategory(category);
        if(moviesList.isEmpty()) {
            RStatus status = new RStatus();
            status.setStatusCode(400);
            status.setStatusMessage("Empty movie list");
            return new ResponseEntity<RStatus>(status, HttpStatus.BAD_REQUEST);
        }
        RStatus status = new RStatus();
        status.setStatusCode(200);
        status.setStatusMessage("Search movies by category");
        MoviesList moviesList2 = new MoviesList();
        moviesList2.setMoviesList(moviesList);
        moviesList2.setRStatus(status);
        return new ResponseEntity<MoviesList>(moviesList2, HttpStatus.OK);
    }
}
