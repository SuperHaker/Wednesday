package com.example.android.wednesday.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.example.android.wednesday.R;
import com.example.android.wednesday.adapters.ListAdapter;
import com.example.android.wednesday.models.EventListitemModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventlistActivity extends AppCompatActivity {

    private RecyclerView listRecyclerView;
    private ListAdapter listAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    String node;

    List<EventListitemModel> dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventlist);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("events").child("categories");
        Intent intent = getIntent();
        String categoryClicked =  intent.getStringExtra("categoryClicked");
        listRecyclerView = (RecyclerView) findViewById(R.id.event_listView);
        listRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        listRecyclerView.setLayoutManager(mLayoutManager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Query query = mDatabaseReference.orderByChild("categoryName").equalTo(categoryClicked);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    Log.v("Ye kya hai", child.getKey());
                    node = child.getKey();
                    databaseReference = FirebaseDatabase.getInstance().getReference("events").child("categories").child(node).child("events");
                    Log.v("Ye le", "This is" + node);
                    dataSource = new ArrayList<>();
                    attachDatabaseReadListener();



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//        for(int i = 0; i<10;i++){
//            dataSource.add(new EventListitemModel("Event Name", "Place", "100"));
//        }



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

    private void attachDatabaseReadListener(){
        if(valueEventListener == null) {
            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    dataSource.clear();
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
//                        Log.v("Ye raha", "" + childDataSnapshot.getKey()); //displays the key for the node
//                        Log.v("Ye raha", "" + childDataSnapshot.child("eventName").getValue());
                        EventListitemModel model = childDataSnapshot.getValue(EventListitemModel.class);
                        dataSource.add(model);
                        listAdapter.notifyDataSetChanged(); //gives the value for given keyname

                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            };
//            Log.v("Ye le", node);
            databaseReference.addValueEventListener(valueEventListener);
            listAdapter = new ListAdapter(this, dataSource);
            listRecyclerView.setAdapter(listAdapter);

        }
    }

    private void detachDatabaseReadListener(){
        if(valueEventListener != null){
            mDatabaseReference.removeEventListener(valueEventListener);
            valueEventListener = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        detachDatabaseReadListener();
    }

//    public void getNode(String categoryClicked){
//
//        switch(categoryClicked){
//
//            case "Arts & Theatre":
//                node = "artsandtheatre";
//                break;
//            case "Bands & Singers":
//                node = "bandsandsingers";
//                break;
//            case "College Festivals":
//                nod
//        }
//
//    }
}
