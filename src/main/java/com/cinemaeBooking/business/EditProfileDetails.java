package com.cinemaeBooking.business;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinemaeBooking.entities.PaymentCard;
import com.cinemaeBooking.entities.User;
import com.cinemaeBooking.exception.CustomErrorsException;
import com.cinemaeBooking.service.EditProfileService;
import com.cinemaeBooking.service.EncryptDecrypt;

@Service
public class EditProfileDetails {

    @Autowired
    EncryptDecrypt encryptDecrypt;

    @Autowired
    EditProfileService editProfileService;

    public User editUser(String userName, User updatedUser) throws Exception{
        User editedUser = editProfileService.editUser(userName, updatedUser);
        Set<PaymentCard> savedPaymentCards = editedUser.getPaymentCards();
        Set<PaymentCard> savedDecryptedPaymentCards = new HashSet<PaymentCard>();
        for(PaymentCard paymentCard : savedPaymentCards) {
            PaymentCard paymentCardDecrypted = new PaymentCard();
            paymentCardDecrypted.setAddress(paymentCard.getAddress());
            paymentCardDecrypted.setCard_Number(encryptDecrypt.decrypt(paymentCard.getCard_Number()));
            paymentCardDecrypted.setExpiryDate(paymentCard.getExpiryDate());
            paymentCardDecrypted.setPaymentID(paymentCard.getPaymentID());
            savedDecryptedPaymentCards.add(paymentCardDecrypted);
        }
        editedUser.setPaymentCards(savedDecryptedPaymentCards);
        return editedUser;
    }

    public User getUser(String userName) throws CustomErrorsException{
        User getUser = editProfileService.getUser(userName);
        if(getUser == null) {
            throw new CustomErrorsException("userName cannot be found");
        }
        Set<PaymentCard> savedPaymentCards = getUser.getPaymentCards();
        Set<PaymentCard> savedDecryptedPaymentCards = new HashSet<PaymentCard>();
        for(PaymentCard paymentCard : savedPaymentCards) {
            PaymentCard paymentCardDecrypted = new PaymentCard();
            paymentCardDecrypted.setAddress(paymentCard.getAddress());
            paymentCardDecrypted.setCard_Number(encryptDecrypt.decrypt(paymentCard.getCard_Number()));
            paymentCardDecrypted.setExpiryDate(paymentCard.getExpiryDate());
            paymentCardDecrypted.setPaymentID(paymentCard.getPaymentID());
            savedDecryptedPaymentCards.add(paymentCardDecrypted);
        }
        getUser.setPaymentCards(savedDecryptedPaymentCards);
        getUser.setPassword(encryptDecrypt.decrypt(getUser.getPassword()));
        return getUser;
    }
}
