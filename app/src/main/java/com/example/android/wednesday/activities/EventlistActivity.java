package com.example.android.wednesday.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.wednesday.R;
import com.example.android.wednesday.adapters.ListAdapter;
import com.example.android.wednesday.models.EventListitemModel;

import java.util.ArrayList;
import java.util.List;

public class EventlistActivity extends AppCompatActivity {

    private RecyclerView listRecyclerView;
    private ListAdapter listAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventlist);
        listRecyclerView = (RecyclerView) findViewById(R.id.event_listView);
        listRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        listRecyclerView.setLayoutManager(mLayoutManager);

        List<EventListitemModel> dataSource = new ArrayList<>();

        for(int i = 0;i<10; i++){
            dataSource.add(createEventListitem(i));
        }

        listAdapter = new ListAdapter(this, dataSource);
        listRecyclerView.setAdapter(listAdapter);



    }

    public EventListitemModel createEventListitem(int i){
        return new EventListitemModel("Event name" + Integer.toString(i), "Place", "Date");
    }
}
