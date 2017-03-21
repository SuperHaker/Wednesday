package com.example.android.wednesday.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.wednesday.R;

public class WriteAnswerActivity extends AppCompatActivity {

    TextView ques;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_answer);
        Intent intent = getIntent();
        String question = intent.getStringExtra("question");
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
        }
        return true;
    }
}
