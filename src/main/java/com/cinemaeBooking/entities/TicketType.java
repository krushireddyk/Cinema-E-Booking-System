package com.cinemaeBooking.entities;

public enum TicketType 
{
	ADULT(12.00f),
	SENIOR(10.00f),
    CHILD(7.50f);

	public float price;
    TicketType(float price) 
    {
        this.price = price;
    }
}
