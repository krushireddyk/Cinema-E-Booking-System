package com.cinemaeBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinemaeBooking.entities.ShowDetails;
import com.cinemaeBooking.entities.ShowId;

public interface ShowRepository extends JpaRepository<ShowDetails, ShowId>{
    public ShowDetails findByShowId(ShowId showId);
}
