package com.cinemaeBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.cinemaeBooking.entities.Promotion;



public interface PromotionRepository extends CrudRepository<Promotion,Integer> 
{
	@Query(value = "SELECT * FROM promotion", nativeQuery = true)
	public List<Promotion> findAll();
}
