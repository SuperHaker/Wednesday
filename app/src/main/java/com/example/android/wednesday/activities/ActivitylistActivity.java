package com.example.android.wednesday.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.android.wednesday.R;
import com.example.android.wednesday.adapters.ActivityListAdapter;
import com.example.android.wednesday.models.EventListitemModel;

import java.util.ArrayList;
import java.util.List;

public class ActivitylistActivity extends AppCompatActivity {

    private RecyclerView listRecyclerView;
    private ActivityListAdapter listAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitylist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listRecyclerView = (RecyclerView) findViewById(R.id.activity_listView);
        listRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        listRecyclerView.setLayoutManager(mLayoutManager);

        List<EventListitemModel> dataSource = new ArrayList<>();

        for(int i = 0;i<10; i++){
            dataSource.add(createEventListitem(i));
        }

        listAdapter = new ActivityListAdapter(this, dataSource);
        listRecyclerView.setAdapter(listAdapter);
    }

    public EventListitemModel createEventListitem(int i){
        return new EventListitemModel("Activity Name" + Integer.toString(i), "Place", "Date");
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
