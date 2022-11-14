package com.cinemaeBooking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cinemaeBooking.entities.Screen;

@Repository
public interface ScreenRepository extends CrudRepository<Screen, Integer> {
    public Screen findByScreenID(String screenID);
}
