package com.example.android.wednesday.models;

/**
 * Created by hp pc on 2/4/2017.
 */

public class EventModel{

    public String eventName;
    public String eventPlace;
    public String eventCost;
    public String eventFirstHighlight;
    public String eventSecondHighlight;
    public String eventThirdHighlight;
    public String eventAbout;
    public String eventStartTime;
    public String eventEndTime;
    public String eventStags;
    public String eventArtist;
    public String eventOffers;

    public EventModel(String eventName, String eventPlace, String eventCost,
                      String eventStartTime, String eventEndTime, String eventArtist,
                      String eventAbout, String eventOffers, String eventFirstHighlight, String eventSecondHighlight, String eventThirdHighlight,
                      String eventStags) {

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
