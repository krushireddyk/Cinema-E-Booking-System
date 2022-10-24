package com.cinemaeBooking.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

//import com.cinemaeBooking.entities.Order;
import com.cinemaeBooking.entities.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Long>
{
	Ticket findById(long ticket_id);
    //Collection<Ticket> findAllByOrder(Order order);
}
