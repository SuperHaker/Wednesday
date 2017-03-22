package com.example.android.wednesday.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.wednesday.R;
import com.example.android.wednesday.models.AnswerModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WriteAnswerActivity extends AppCompatActivity {

    TextView ques;
    DatabaseReference databaseReference;
    EditText writable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_answer);
        writable = (EditText) findViewById(R.id.writable);
        Intent intent = getIntent();
        String question = intent.getStringExtra("question");
        String userId = intent.getStringExtra("userId");
        String quesId = intent.getStringExtra("quesId");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("asknow").child(userId).child(quesId).child("answers");
        ques = (TextView) findViewById(R.id.ques);
        ques.setText(question);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.answer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.done:
                Toast.makeText(WriteAnswerActivity.this, "Done clicked", Toast.LENGTH_SHORT).show();
                AnswerModel model = new AnswerModel(writable.getText().toString(), 0);
                databaseReference.push().setValue(model);
                finish();

        }
        return true;
    }
}
