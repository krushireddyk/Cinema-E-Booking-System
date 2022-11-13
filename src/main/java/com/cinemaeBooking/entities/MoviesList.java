package com.cinemaeBooking.entities;

import java.util.Set;

public class MoviesList {
    private Set<Movie> moviesList;
    private RStatus rStatus;

    public Set<Movie> getMoviesList() {
        return this.moviesList;
    }

    public void setMoviesList(Set<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    public RStatus getRStatus() {
        return this.rStatus;
    }

    public void setRStatus(RStatus rStatus) {
        this.rStatus = rStatus;
    }
}
