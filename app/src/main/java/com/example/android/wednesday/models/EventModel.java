package com.example.android.wednesday.models;

/**
 * Created by hp pc on 2/4/2017.
 */

public class EventModel {

    String eventPlace;
    String eventCost;
    String eventHighlights;
    String eventAbout;
    String eventStartTime;
    String eventEndTime;
    String eventArtist;
    String eventOffers;

    public EventModel(String eventPlace, String eventCost,
                      String eventStartTime, String eventEndTime, String eventArtist,
                      String eventHighlights, String eventAbout, String eventOffers){

        this.eventPlace = eventPlace;
        this.eventCost = eventCost;
        this.eventArtist = eventArtist;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.eventHighlights = eventHighlights;
        this.eventAbout = eventAbout;
        this.eventOffers = eventOffers;
    }


}
