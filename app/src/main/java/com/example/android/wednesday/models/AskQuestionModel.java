package com.example.android.wednesday.models;

import java.util.List;

/**
 * Created by hp pc on 3/17/2017.
 */

public class AskQuestionModel {

    public String question;
    public List<String> tags;


    public AskQuestionModel(){}

    public AskQuestionModel(String question, List<String> tags){
        this.question = question;
        this.tags = tags;

    }

}
