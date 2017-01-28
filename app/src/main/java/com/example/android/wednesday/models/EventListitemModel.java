package com.example.android.wednesday.models;

/**
 * Created by hp pc on 1/29/2017.
 */

public class EventListitemModel {

    public String eventName;
    public String eventPlace;
    public String eventDate;


    public EventListitemModel() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public EventListitemModel(String name, String place, String date) {
        this.eventName = name;
        this.eventPlace = place;
        this.eventDate = date;
    }
}
