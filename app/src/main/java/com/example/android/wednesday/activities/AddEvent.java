package com.example.android.wednesday.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.android.wednesday.R;
import com.example.android.wednesday.fragments.MainEventsFragment;
import com.example.android.wednesday.models.EventModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEvent extends AppCompatActivity {



    @BindView(R.id.add_event_address)
    EditText addEventAddress;
    @BindView(R.id.add_event_cost_for_two)
    EditText addEventCostForTwo;
    @BindView(R.id.add_event_name)
    EditText addEventName;
    @BindView(R.id.add_event_artist_name)
    EditText addEventArtistName;
    @BindView(R.id.add_event_starttime)
    EditText addEventStartTime;
    @BindView(R.id.add_event_endtime)
    EditText addEventEndTime;
    @BindView(R.id.add_event_stags)
    EditText addEventStags;
    @BindView(R.id.add_event_first_highlight)
    EditText addEventFirstHighlight;
    @BindView(R.id.add_event_second_highlight)
    EditText addEventSecondHighlight;
    @BindView(R.id.add_event_third_highlight)
    EditText addEventThirdHighlight;
    @BindView(R.id.add_event_about)
    EditText addEventAbout;
    @BindView(R.id.add_event_offers)
    EditText addEventOffers;


    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        ButterKnife.bind(this);
        Button addEventButton2 = (Button) findViewById(R.id.add_event_to_database_button);
        RadioGroup eventCategoriesGroup  = (RadioGroup) findViewById(R.id.event_categories_group);
        eventCategoriesGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View v = findViewById(i);
                mDatabase = FirebaseDatabase.getInstance().getReference("events").child("categories").child(v.getTag().toString()).child("events");

            }
        });

        addEventButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EventModel eventModel = new EventModel(addEventName.getText().toString(), addEventAddress.getText().toString(), addEventCostForTwo.getText().toString(),
                        addEventStartTime.getText().toString(), addEventEndTime.getText().toString(), addEventArtistName.getText().toString(),
                        addEventAbout.getText().toString(), addEventOffers.getText().toString(), addEventFirstHighlight.getText().toString(),
                        addEventSecondHighlight.getText().toString(), addEventThirdHighlight.getText().toString(), addEventStags.getText().toString());
                mDatabase.push().setValue(eventModel);
                Toast.makeText(getApplicationContext(), "Event added to Database", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainEventsFragment.class));

            }
        });



    }

}
