package com.example.android.wednesday.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hp pc on 3/22/2017.
 */

public class AnswerModel {

    public String answer;
    public String answerId;
    public String answererUid;
    public Upvotes upvotes;
    public Map<String, Object> time = new HashMap<>();

    public AnswerModel(){}

    public AnswerModel(String answer, Upvotes upvotes, String answererUid){
        this.answer = answer;
        this.upvotes = upvotes;
        this.answererUid = answererUid;


    }
}
