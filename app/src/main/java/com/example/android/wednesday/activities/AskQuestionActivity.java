package com.example.android.wednesday.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.wednesday.R;
import com.example.android.wednesday.models.AskQuestionModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class AskQuestionActivity extends AppCompatActivity {

    EditText askedQuestion;
    EditText questionTags;
    Button postQuestion;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);
        askedQuestion = (EditText) findViewById(R.id.asked_question);
        questionTags = (EditText) findViewById(R.id.question_tags);
        postQuestion = (Button) findViewById(R.id.post_question_button);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("asknow");


        postQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] ar = questionTags.getText().toString().split("\\s*,\\s*");
                List<String> list = Arrays.asList(ar);
                AskQuestionModel askQuestionModel = new AskQuestionModel(askedQuestion.getText().toString(), list);
                databaseReference.push().setValue(askQuestionModel);
                Toast.makeText(AskQuestionActivity.this, "Question Added", Toast.LENGTH_SHORT).show();
                finish();
            }
        });



    }
}
