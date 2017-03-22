package com.example.android.wednesday.models;

import java.util.List;

/**
 * Created by hp pc on 3/17/2017.
 */

public class AskQuestionModel {

    public String question;
    public List<String> tags;
    public String userId;
    public String quesId;
    public List<AnswerModel> answers;



    public AskQuestionModel(){}

    public AskQuestionModel(String question, List<String> tags, List<AnswerModel> answers){
        this.question = question;
        this.tags = tags;
        this.answers = answers;

    }

    public AskQuestionModel(String question, List<String> tags){
        this.question = question;
        this.tags = tags;

    }

}
