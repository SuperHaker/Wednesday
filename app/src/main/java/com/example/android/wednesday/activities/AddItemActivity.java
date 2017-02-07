package com.example.android.wednesday.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.android.wednesday.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddItemActivity extends AppCompatActivity {

    @BindView(R.id.add_event_button)
    Button addEventButton;
    @BindView(R.id.add_activity_button)
    Button addActivityButton;
    @BindView(R.id.add_dineout_button)
    Button addDineoutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.add_event_button)
    public void addEvent(){
        Intent intent = new Intent(getApplicationContext(), AddEvent.class);
        startActivity(intent);
    }

    @OnClick(R.id.add_activity_button)
    public void addActivity(){
        Intent intent = new Intent(getApplicationContext(), AddActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.add_dineout_button)
    public void addDineout(){
        Intent intent = new Intent(getApplicationContext(), AddDineout.class);
        startActivity(intent);
    }

}
