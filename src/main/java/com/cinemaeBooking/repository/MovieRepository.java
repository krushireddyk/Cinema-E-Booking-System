package com.cinemaeBooking.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cinemaeBooking.entities.Movie;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Integer>  {
    public Set<Movie> findAll();
}
