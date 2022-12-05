package com.cinemaeBooking.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinemaeBooking.entities.Booking;
import com.cinemaeBooking.entities.Movie;
import com.cinemaeBooking.entities.PaymentCard;
import com.cinemaeBooking.entities.Promotion;
import com.cinemaeBooking.entities.ShowDetails;
import com.cinemaeBooking.entities.ShowId;
import com.cinemaeBooking.entities.User;
import com.cinemaeBooking.repository.BookingRepository;
import com.cinemaeBooking.repository.MovieRepository;
import com.cinemaeBooking.repository.PaymentCardRepository;
import com.cinemaeBooking.repository.PromotionRepository;
import com.cinemaeBooking.repository.ShowRepository;
import com.cinemaeBooking.repository.UserRepository;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    PromotionRepository promotionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShowRepository showRepository;

    @Autowired
    PaymentCardRepository paymentCardRepository;

    @Autowired
    EncryptDecrypt encryptDecrypt;

    @Autowired
    MovieRepository movieRepository;

    @Transactional
    public Booking submitOrder(Booking booking) throws Exception {
        Booking tempBooking = new Booking();
        tempBooking.setNumberOfTickets(booking.getNumberOfTickets());
        tempBooking.setTotalPrice(booking.getTotalPrice());
        PaymentCard paymentCard = paymentCardRepository.findByCardNumber(encryptDecrypt.encrypt(booking.getPaymentCard().getCardNumber()));
        tempBooking.setPaymentCard(paymentCard);
        Promotion promotion = promotionRepository.findByPromotionCode(booking.getPromotion().getPromotionCode());
        tempBooking.setPromotion(promotion);
        ShowDetails showDetails = showRepository.findByShowId(booking.getShowdetails().getShowId());
        tempBooking.setShowdetails(showDetails);
        User user = userRepository.findByUserName(booking.getUser().getUserName());
        System.out.println(user.getEmailID());
        tempBooking.setUser(user);
        Movie movie = movieRepository.findByTitle(booking.getMovie().getTitle());
        tempBooking.setMovie(movie);
        Booking savedBooking = bookingRepository.save(tempBooking);
        System.out.println(savedBooking.getBookingID());
        return savedBooking;
    }
    
}
