package com.example.android.wednesday.models;

import java.util.Map;

/**
 * Created by hp pc on 3/26/2017.
 */

public class Upvotes {
    public long number;
    public Map<String, Boolean> listOfPeople;

    public Upvotes(){}

    public Upvotes(long number, Map<String, Boolean> listOfPeople){
        this.number = number;
        this.listOfPeople = listOfPeople;
    }

}
