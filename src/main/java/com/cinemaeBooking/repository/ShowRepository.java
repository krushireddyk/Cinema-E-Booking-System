package com.cinemaeBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.cinemaeBooking.entities.ShowDetails;

public interface ShowRepository extends CrudRepository<ShowDetails,Integer>
{
	@Query(value = "SELECT * FROM showdetails where movieId=?1", nativeQuery = true)
	public List <ShowDetails> findByMovieId(int id);
}
