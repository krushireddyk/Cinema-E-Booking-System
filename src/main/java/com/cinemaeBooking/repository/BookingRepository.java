package com.cinemaeBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinemaeBooking.entities.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>
{
    
}
