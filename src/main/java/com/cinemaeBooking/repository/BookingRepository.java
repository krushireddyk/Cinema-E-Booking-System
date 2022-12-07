package com.cinemaeBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cinemaeBooking.entities.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>
{
    @Query(value = "SELECT * FROM booking b INNER JOIN user u WHERE b.userID = u.userID and u.userName = ?1 order by b.showDate desc, b.showTime desc", nativeQuery = true)
    public List<Booking> findAllBookingsByUserName(String userName);
}
