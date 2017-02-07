package com.example.android.wednesday.models;

/**
 * Created by hp pc on 2/4/2017.
 */

public class EventModel {

    String eventName;
    String eventPlace;
    String eventCost;
    String eventFirstHighlight;
    String eventSecondHighlight;
    String eventThirdHighlight;
    String eventAbout;
    String eventStartTime;
    String eventEndTime;
    String eventStags;
    String eventArtist;
    String eventOffers;

    public EventModel(String eventName, String eventPlace, String eventCost,
                      String eventStartTime, String eventEndTime, String eventArtist,
                      String eventAbout, String eventOffers, String eventFirstHighlight, String eventSecondHighlight, String eventThirdHighlight,
                      String eventStags){

        this.eventStags = eventStags;
        this.eventName = eventName;
        this.eventPlace = eventPlace;
        this.eventCost = eventCost;
        this.eventArtist = eventArtist;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.eventFirstHighlight = eventFirstHighlight;
        this.eventSecondHighlight = eventSecondHighlight;
        this.eventThirdHighlight = eventThirdHighlight;
        this.eventAbout = eventAbout;
        this.eventOffers = eventOffers;
    }


}
