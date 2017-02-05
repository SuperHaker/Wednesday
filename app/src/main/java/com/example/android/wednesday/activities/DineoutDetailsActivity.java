package com.example.android.wednesday.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.android.wednesday.R;
import com.example.android.wednesday.adapters.MultipleImagesDineoutAdapter;

public class DineoutDetailsActivity extends AppCompatActivity {

    private MultipleImagesDineoutAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dineout_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView mapButton = (ImageView) findViewById(R.id.dineout_map);
        recyclerView = (RecyclerView) findViewById(R.id.images_for_a_dineout);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new MultipleImagesDineoutAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }return super.onOptionsItemSelected(item);
    }
}
