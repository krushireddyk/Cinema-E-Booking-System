package com.cinemaeBooking.entities;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

@Embeddable
public class ShowId implements Serializable{
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date showDate;
    private LocalTime showTime;
    @ManyToOne
    @JoinColumn(name = "screenID")
    private Screen screen;
    /*@ManyToOne(optional = true)
	@JoinColumn(name ="screenID", referencedColumnName = "screenID", foreignKey = @ForeignKey(name = "player_stat_fk_player_id"))
    private Screen screen;*/

    public Date getShowDate() {
        return this.showDate;
    }

    public void setShowDate(Date showDate) {
        this.showDate = showDate;
    }

    public LocalTime getShowTime() {
        return this.showTime;
    }

    public void setShowTime(LocalTime showTime) {
        this.showTime = showTime;
    }

    public Screen getScreen() {
        return this.screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShowId)) return false;
        ShowId that = (ShowId) o;
        return Objects.equals(getShowDate(), that.getShowDate()) &&
                Objects.equals(getShowTime(), that.getShowTime()) &&
                 Objects.equals(getScreen(), that.getScreen());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getShowDate(), getShowTime(), getScreen());
    }
}
