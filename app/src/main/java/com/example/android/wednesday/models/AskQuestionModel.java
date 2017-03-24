package com.example.android.wednesday.models;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hp pc on 3/17/2017.
 */

public class AskQuestionModel {

    public String question;
    public List<String> tags;
    public String userId;
    public String quesId;
//    public List<AnswerModel> answersList;
    public Map<String, AnswerModel> map = new LinkedHashMap<>();



    public AskQuestionModel(){
    }


    public AskQuestionModel(String question, List<String> tags){
        this.question = question;
        this.tags = tags;

    }

}
