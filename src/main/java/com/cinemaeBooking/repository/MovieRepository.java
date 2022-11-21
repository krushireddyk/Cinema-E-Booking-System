package com.cinemaeBooking.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cinemaeBooking.entities.Movie;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Integer>  {
    public Set<Movie> findAll();

    @Query(value = "SELECT * FROM movie m LEFT JOIN showdetails sd ON m.movieID = sd.movieID WHERE (CONCAT(sd.showDate, ' ', sd.showTime) >= NOW()) OR sd.showDate is NULL and sd.showTime is NULL and sd.screenID is NULL"
    , nativeQuery = true)
    public Set<Movie> findAllCurrentlyRunningAndComingSoonMovies();

    @Query(value = "SELECT * FROM movie m LEFT JOIN showdetails sd ON m.movieID = sd.movieID WHERE sd.showDate is NULL and sd.showTime is NULL and sd.screenID is NULL"
    , nativeQuery = true)
    public Set<Movie> findAllComingSoonMovies();

    @Query(value = "SELECT * FROM movie m INNER JOIN showdetails sd WHERE m.movieID = sd.movieID AND CONCAT(sd.showDate, ' ', sd.showTime) >= NOW()"
    , nativeQuery = true)
    public Set<Movie> findAllCurrentMovies();

    public Movie findByTitle(String title);
    //public boolean findByTitle(String title);

    public Set<Movie> findByCategory(String category);

    public Set<Movie> findByTitleContaining(String title);

    public Set<Movie> findByCategoryContaining(String title);
    
    public void deleteByTitle(String title);
  
}
