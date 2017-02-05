package com.example.android.wednesday.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.android.wednesday.R;

public class ActivitiesFilter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_filter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.activities_cost_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View filterOptions = findViewById(R.id.cost_filterlist_activities);
                if (filterOptions.getVisibility() == View.GONE)
                    filterOptions.setVisibility(View.VISIBLE);
                else
                    filterOptions.setVisibility(View.GONE);
            }
        });

        findViewById(R.id.activities_location_filters).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View filterOptions = findViewById(R.id.location_filterlist_activities);
                if (filterOptions.getVisibility() == View.GONE)
                    filterOptions.setVisibility(View.VISIBLE);
                else
                    filterOptions.setVisibility(View.GONE);
            }
        });

        findViewById(R.id.activities_discount_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View filterOptions = findViewById(R.id.discount_filterlist_activities);
                if (filterOptions.getVisibility() == View.GONE)
                    filterOptions.setVisibility(View.VISIBLE);
                else
                    filterOptions.setVisibility(View.GONE);
            }
        });

        findViewById(R.id.filtered_places_activities).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivitylistActivity.class);
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
        }
        return super.onOptionsItemSelected(item);
    }
}
