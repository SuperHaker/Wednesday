package com.example.android.wednesday.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.android.wednesday.R;

public class EventFilter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_filter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViewById(R.id.event_cost_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View filterOptions = findViewById(R.id.cost_filterlist_event);
                if (filterOptions.getVisibility() == View.GONE)
                    filterOptions.setVisibility(View.VISIBLE);
                else
                    filterOptions.setVisibility(View.GONE);
            }
        });

        findViewById(R.id.event_location_filters).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View filterOptions = findViewById(R.id.location_filterlist_event);
                if (filterOptions.getVisibility() == View.GONE)
                    filterOptions.setVisibility(View.VISIBLE);
                else
                    filterOptions.setVisibility(View.GONE);
            }
        });

        findViewById(R.id.event_discount_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View filterOptions = findViewById(R.id.discount_filterlist_event);
                if (filterOptions.getVisibility() == View.GONE)
                    filterOptions.setVisibility(View.VISIBLE);
                else
                    filterOptions.setVisibility(View.GONE);
            }
        });

        findViewById(R.id.filtered_places_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EventlistActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }return super.onOptionsItemSelected(item);
    }
}
