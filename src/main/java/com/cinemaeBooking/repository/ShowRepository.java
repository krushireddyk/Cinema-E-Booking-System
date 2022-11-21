package com.cinemaeBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cinemaeBooking.entities.ShowDetails;
import com.cinemaeBooking.entities.ShowId;

public interface ShowRepository extends JpaRepository<ShowDetails, ShowId>{
    @Query(value = "SELECT * FROM showdetails where movieId=?1", nativeQuery = true)
	public List <ShowDetails> findByMovieId(int id);
    public ShowDetails findByShowId(ShowId showId);
}