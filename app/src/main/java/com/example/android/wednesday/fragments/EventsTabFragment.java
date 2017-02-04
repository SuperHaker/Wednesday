package com.example.android.wednesday.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.android.wednesday.R;
import com.example.android.wednesday.activities.EventFilter;
import com.example.android.wednesday.adapters.FeaturedAdapter;
import com.example.android.wednesday.adapters.GridAdapter;
import com.example.android.wednesday.adapters.TopPicksAdapter;
import com.example.android.wednesday.models.CardModel;
import com.example.android.wednesday.models.CategoryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lsjwzh.widget.recyclerviewpager.LoopRecyclerViewPager;

import java.util.ArrayList;
import java.util.List;


public class EventsTabFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView mRecyclerViewTopPicks;
    private LoopRecyclerViewPager mLoopRecyclerView;
    CategoryModel model;

    private FeaturedAdapter mAdapter;
    private TopPicksAdapter mTopPicksAdapter;
    private RecyclerView.LayoutManager mLayoutManagerFeatured;
    private RecyclerView.LayoutManager mLayoutManagerTopPicks;
    private DatabaseReference mDatabaseReference ;
    CategoryModel categoryModel;
    ValueEventListener valueEventListener;
    GridAdapter gridAdapter;



    GridLayoutManager mGridManager;

    final int speedScroll = 2000;
    private Handler handler;
    private Runnable runnable;

    List<CategoryModel> categorySource;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public EventsTabFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_tab_one, container, false);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("events").child("categories");

        FrameLayout eventFilterFrame = (FrameLayout) rootView.findViewById(R.id.event_filter);
        Button eventFilter = (Button) eventFilterFrame.findViewById(R.id.f_button);
        eventFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EventFilter.class);
                startActivity(intent);
            }
        });

        mLoopRecyclerView = (LoopRecyclerViewPager) rootView.findViewById(R.id.featured_picks);
        mRecyclerViewTopPicks = (RecyclerView) rootView.findViewById(R.id.top_picks);

        mRecyclerViewTopPicks.setHasFixedSize(true);
        mLoopRecyclerView.setHasFixedSize(true);

        mLayoutManagerFeatured = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mLayoutManagerTopPicks = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        mLoopRecyclerView.setLayoutManager(mLayoutManagerFeatured);
        mRecyclerViewTopPicks.setLayoutManager(mLayoutManagerTopPicks);

        final List<CardModel> dataSource = new ArrayList<CardModel>();

        for(int i = 0;i<5; i++){
            dataSource.add(createCard(i));
        }

        mAdapter = new FeaturedAdapter(getContext(), dataSource);
        mTopPicksAdapter = new TopPicksAdapter(getContext(), dataSource);

        mLoopRecyclerView.setAdapter(mAdapter);
        mRecyclerViewTopPicks.setAdapter(mTopPicksAdapter);




        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                    mLoopRecyclerView.smoothScrollToPosition(mLoopRecyclerView.getCurrentPosition() + 1);
                    handler.postDelayed(this,speedScroll);

            }
        };




          categorySource = new ArrayList<>();
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    Log.v("Ye raha", "" + childDataSnapshot.getKey()); //displays the key for the node
                    Log.v("Ye raha", "" + childDataSnapshot.child("categoryName").getValue());
                    CategoryModel model = childDataSnapshot.getValue(CategoryModel.class);
                    categorySource.add(model);
                    gridAdapter.notifyDataSetChanged(); //gives the value for given keyname

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } ;

        mDatabaseReference.addValueEventListener(valueEventListener);


        mGridManager = new GridLayoutManager(getContext(), 2);
        RecyclerView categoryRecyclerView = (RecyclerView) rootView.findViewById(R.id.categories);
        categoryRecyclerView.setHasFixedSize(true);
        categoryRecyclerView.setLayoutManager(mGridManager);

        gridAdapter = new GridAdapter(getContext(), categorySource);
        categoryRecyclerView.setAdapter(gridAdapter);
        categoryRecyclerView.setNestedScrollingEnabled(false);




        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable,speedScroll);

    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    public CardModel createCard(int i){

        return  new CardModel("The Drunk House " + Integer.toString(i), "Rajouri", "1000");
    }



}
