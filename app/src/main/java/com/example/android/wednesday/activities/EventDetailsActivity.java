package com.example.android.wednesday.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.android.wednesday.R;
import com.example.android.wednesday.adapters.MultipleImagesEventsAdapter;

public class EventDetailsActivity extends AppCompatActivity {

    private MultipleImagesEventsAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView mapButton = (ImageView) findViewById(R.id.event_map);
        recyclerView = (RecyclerView) findViewById(R.id.images_for_an_event);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new MultipleImagesEventsAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri gmmIntentUri = Uri.parse("google.navigation:q=Rajouri+Garden, +New+Delhi");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {

                    startActivity(mapIntent);
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
