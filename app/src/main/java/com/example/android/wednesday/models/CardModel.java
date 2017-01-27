package com.example.android.wednesday.models;

/**
 * Created by hp pc on 1/27/2017.
 */

public class CardModel {

    public String eventPlace;
    public String eventAddress;
    public String eventCost;

    public CardModel() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public CardModel(String eventPlace, String eventAddress, String eventCost) {
        this.eventPlace = eventPlace;
        this.eventAddress = eventAddress;
        this.eventCost = eventCost;
    }

}
