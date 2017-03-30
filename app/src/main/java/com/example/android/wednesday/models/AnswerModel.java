package com.example.android.wednesday.models;

/**
 * Created by hp pc on 3/22/2017.
 */

public class AnswerModel {

    public String answer;
    public String answerId;
    public String answererUid;
    public Upvotes upvotes;

    public AnswerModel(){}

    public AnswerModel(String answer, Upvotes upvotes, String answererUid){
        this.answer = answer;
        this.upvotes = upvotes;
        this.answererUid = answererUid;
    }
}
