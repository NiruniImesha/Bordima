package com.projectmad.bordima.Model;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Feedback {

    private String FID, data,review, time ;
    private Float rating;

    public Feedback() {

    }

    public Feedback(String FID, String data, String review, String time, Float rating) {
        this.FID = FID;
        this.data = data;
        this.review = review;
        this.time = time;
        this.rating = rating;
    }

    public String getFID() {
        return FID;
    }

    public void setFID(String FID) {
        this.FID = FID;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
