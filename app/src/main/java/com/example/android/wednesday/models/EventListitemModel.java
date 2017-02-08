package com.example.android.wednesday.models;

/**
 * Created by hp pc on 1/29/2017.
 */

public class EventListitemModel {

    public String eventName;
    public String eventPlace;
    public String eventCost;


    public EventListitemModel() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public EventListitemModel(String eventName, String eventPlace, String eventCost) {
        this.eventName = eventName;
        this.eventPlace = eventPlace;
        this.eventCost = eventCost;
    }
}
