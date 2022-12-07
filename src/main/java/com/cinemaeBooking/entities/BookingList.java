package com.cinemaeBooking.entities;

import java.util.Set;

public class BookingList {
    private Set<Booking> bookingList;
    private RStatus rStatus;
    public Set<Booking> getBookingList() {
        return this.bookingList;
    }

    public void setBookingList(Set<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    public RStatus getRStatus() {
        return this.rStatus;
    }

    public void setRStatus(RStatus rStatus) {
        this.rStatus = rStatus;
    }

}

    
