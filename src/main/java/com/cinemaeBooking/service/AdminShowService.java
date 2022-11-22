package com.cinemaeBooking.service;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cinemaeBooking.entities.Movie;
import com.cinemaeBooking.entities.Screen;
import com.cinemaeBooking.entities.ShowDetails;
import com.cinemaeBooking.entities.ShowId;
import com.cinemaeBooking.exception.CustomErrorsException;
import com.cinemaeBooking.repository.MovieRepository;
import com.cinemaeBooking.repository.ScreenRepository;
import com.cinemaeBooking.repository.ShowRepository;


@Service
public class AdminShowService {

    @Autowired
	ShowRepository showRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ScreenRepository screenRepository;
    
    @Transactional
	public ShowDetails addShow(String title, ShowDetails showDetails) throws Exception
	{
        if(showDetails.getShowId() != null){
            ShowDetails oldShowDetails = showRepository.findByShowId(showDetails.getShowId());
            if(oldShowDetails != null && oldShowDetails.getShowId() != null) {
                if(oldShowDetails.getShowId().getShowDate().compareTo(showDetails.getShowId().getShowDate()) == 0) {
                    if(oldShowDetails.getShowId().getShowTime().compareTo(showDetails.getShowId().getShowTime()) == 0) {
                        if(oldShowDetails.getShowId().getScreen().getScreenID().equals(showDetails.getShowId().getScreen().getScreenID())) {
                            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String startDateString = outputFormat.format(showDetails.getShowId().getShowDate());
                            throw new CustomErrorsException("This Slot("+ startDateString + " " + showDetails.getShowId().getShowTime()+ ") at Screen " + showDetails.getShowId().getScreen().getScreenID() + " is already booked");
                        }
                    }
                }
            }
        }
        else {
            throw new CustomErrorsException("Invalid screen and show details");
        }
        
		Movie movie = movieRepository.findByTitle(title);
		if(movie == null) {
			throw new CustomErrorsException("Invalid movie title");
		}
		ShowDetails savedShow = null;
		ShowDetails showDetail = new ShowDetails();
        showDetail.setShowDuration(showDetails.getShowDuration());
        Screen screen = screenRepository.findByScreenID(showDetails.getShowId().getScreen().getScreenID());
        if(screen == null) 
        {
            throw new CustomErrorsException("Please provide valid/existing screen details");
        }
        ShowId showId = new ShowId();
        showId.setShowDate(showDetails.getShowId().getShowDate());
        showId.setShowTime(showDetails.getShowId().getShowTime());
        showId.setScreen(screen);
        showDetail.setShowId(showId);
        showDetail.setMovie(movie);
		savedShow = showRepository.save(showDetail);
		return savedShow;
	}
}
