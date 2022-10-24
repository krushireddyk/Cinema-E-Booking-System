package com.cinemaeBooking.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ticket")
public class Ticket 
{
	@Id
	private long ticket_id;
	private TicketType age = TicketType.ADULT;
	//private Seat seat;
	//private Order order;
	
	public long getTicket_id() {
		return ticket_id;
	}
	public void setTicket_id(long ticket_id) {
		this.ticket_id = ticket_id;
	}
	public TicketType getAge() {
		return age;
	}
	public void setAge(TicketType age) {
		this.age = age;
	}
	/*public Seat getSeat() {
		return seat;
	}
	public void setSeat(Seat seat) {
		this.seat = seat;
	}*/
	/*public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}*/
	
}
