package com.cinemaeBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinemaeBooking.entities.PaymentCard;

@Repository
public interface PaymentCardRepository extends JpaRepository<PaymentCard, Integer> {
    
	PaymentCard findByCardNumber(String cardNumber);
}
