package com.cinemaeBooking.service;

import java.util.ArrayList;
import java.util.List;

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
import com.cinemaeBooking.exception.CustomErrorsException;
import com.cinemaeBooking.repository.BookingRepository;
import com.cinemaeBooking.repository.MovieRepository;
import com.cinemaeBooking.repository.PaymentCardRepository;
import com.cinemaeBooking.repository.PromotionRepository;
import com.cinemaeBooking.repository.ShowRepository;
import com.cinemaeBooking.repository.UserRepository;
import com.cinemaeBooking.serviceIMPL.EmailService;

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
    
    @Autowired
    EmailService emailService;

    @Transactional
    public Booking submitOrder(Booking booking) throws Exception {
        Booking tempBooking = new Booking();
        tempBooking.setNumberOfTickets(booking.getNumberOfTickets());
        tempBooking.setTotalPrice(booking.getTotalPrice());  
        Promotion promotion = promotionRepository.findByPromotionCode(booking.getPromotion().getPromotionCode());
        if(promotion == null) {
            throw new CustomErrorsException("Invalid promotion code: " + booking.getPromotion().getPromotionCode());
        }
        tempBooking.setPromotion(promotion);
        User user = userRepository.findByUserName(booking.getUser().getUserName());
        if(user == null) {
            throw new CustomErrorsException("Invalid user name: " + booking.getUser().getUserName());
        }
        System.out.println(user.getEmailID());
        tempBooking.setUser(user);
        PaymentCard paymentCard = paymentCardRepository.findByCardNumber(encryptDecrypt.encrypt(booking.getPaymentCard().getCardNumber()));
        if(paymentCard == null) {
            throw new CustomErrorsException("Invalid payment card number: " + booking.getPaymentCard().getCardNumber()); 
        }
        tempBooking.setPaymentCard(paymentCard);
        Movie movie = movieRepository.findByTitle(booking.getMovie().getTitle());
        if(movie == null) {
            throw new CustomErrorsException("Invalid movie name: " + booking.getMovie().getTitle());
        }
        tempBooking.setMovie(movie);
        ShowId providedShowId = booking.getShowdetails().getShowId();
        Boolean isValidShow = false;
        for(ShowDetails showDetails : movie.getShowdetails()) {
            if(providedShowId.equals(showDetails.getShowId())) {
                isValidShow = true;
                break;
            }
        }
        if(isValidShow == false) {
            throw new CustomErrorsException("Invalid show details for the movie name: " +booking.getMovie().getTitle());
        }
        ShowDetails showDetails = showRepository.findByShowId(providedShowId);
        if(showDetails == null) {
            throw new CustomErrorsException("Invalid show details");
        }
        tempBooking.setShowdetails(showDetails);
        Booking savedBooking = bookingRepository.save(tempBooking);
        System.out.println(savedBooking.getBookingID());
        emailService.sendBookingEmail(savedBooking);//send email on booking confirmation
        return savedBooking;
    }

    @Transactional
    public List<Booking> findAllBookingsByUserName(String userName) {
        List<Booking> bookingList = new ArrayList<Booking>();
        bookingList = bookingRepository.findAllBookingsByUserName(userName);
        return bookingList;
    }
    
}
