package com.cinemaeBooking.entities;

import java.util.List;

public class ShowDetailsList {
    private List<ShowDetails> showDetailsList;
    private RStatus rStatus;

    public List<ShowDetails> getShowDetailsList() {
        return this.showDetailsList;
    }

    public void setShowDetailsList(List<ShowDetails> showDetailsList) {
        this.showDetailsList = showDetailsList;
    }

    public RStatus getRStatus() {
        return this.rStatus;
    }

    public void setRStatus(RStatus rStatus) {
        this.rStatus = rStatus;
    }


}
