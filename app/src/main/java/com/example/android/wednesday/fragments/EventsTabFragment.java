package com.example.android.wednesday.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.android.wednesday.R;
import com.example.android.wednesday.activities.EventFilter;
import com.example.android.wednesday.adapters.FeaturedAdapter;
import com.example.android.wednesday.adapters.GridAdapter;
import com.example.android.wednesday.adapters.ImageAdapter;
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
import java.util.TimerTask;


public class EventsTabFragment extends Fragment implements ViewPager.OnPageChangeListener{
    final int speedScroll = 2000;
    ValueEventListener valueEventListener;
    GridAdapter gridAdapter;
    List<CardModel> dataSource;
    GridLayoutManager mGridManager;
    List<CategoryModel> categorySource;
    private RecyclerView mRecyclerViewTopPicks;
    private LoopRecyclerViewPager mLoopRecyclerView;
    private FeaturedAdapter mAdapter;
    private TopPicksAdapter mTopPicksAdapter;
    private RecyclerView.LayoutManager mLayoutManagerFeatured;
    private RecyclerView.LayoutManager mLayoutManagerTopPicks;
    private DatabaseReference mDatabaseReference;
    private Handler handler;
    private Runnable runnable;
    ProgressBar progressBar;
    ViewPager viewPager;
    ImageAdapter imageAdapter;

    public EventsTabFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("events").child("categories");

        dataSource = new ArrayList<CardModel>();

        for (int i = 0; i < 5; i++) {
            dataSource.add(createCard(i));
        }



        categorySource = new ArrayList<>();




    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);


        mLoopRecyclerView = (LoopRecyclerViewPager) rootView.findViewById(R.id.featured_picks_activities);
        mRecyclerViewTopPicks = (RecyclerView) rootView.findViewById(R.id.top_picks);
        progressBar = (ProgressBar) rootView.findViewById(R.id.load_categories);
        mLoopRecyclerView.setNestedScrollingEnabled(false);

//         viewPager = (ViewPager) rootView.findViewById(R.id.image_pagerview);
//        viewPager.addOnPageChangeListener(this);
//        imageAdapter = new ImageAdapter(getContext());
//        viewPager.setAdapter(imageAdapter);
//        Timer timer = new Timer();
//        timer.schedule(new UpdateTimeTask(), 5000, 5000);


        mRecyclerViewTopPicks.setHasFixedSize(true);
        mLoopRecyclerView.setHasFixedSize(true);

        mLayoutManagerFeatured = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mLayoutManagerTopPicks = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        mLoopRecyclerView.setLayoutManager(mLayoutManagerFeatured);
        mRecyclerViewTopPicks.setLayoutManager(mLayoutManagerTopPicks);

        mAdapter = new FeaturedAdapter(getContext(), dataSource);
        mTopPicksAdapter = new TopPicksAdapter(getContext(), dataSource);

        mLoopRecyclerView.setAdapter(mAdapter);
        mRecyclerViewTopPicks.setAdapter(mTopPicksAdapter);


        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                mLoopRecyclerView.smoothScrollToPosition(mLoopRecyclerView.getCurrentPosition() + 1);
                handler.postDelayed(this, speedScroll);

            }
        };
//        mLoopRecyclerView.post(new Runnable() {
//            @Override
//            public void run() {
//                mLoopRecyclerView.smoothScrollToPosition(mLoopRecyclerView.getCurrentPosition() + 1);
//
//            }
//        });

        mGridManager = new GridLayoutManager(getContext(), 2);
        gridAdapter = new GridAdapter(getContext(), categorySource, 1);

        RecyclerView categoryRecyclerView = (RecyclerView) rootView.findViewById(R.id.categories);
        categoryRecyclerView.setHasFixedSize(true);
        categoryRecyclerView.setLayoutManager(mGridManager);

        if(valueEventListener == null) {
            attachDatabaseReadListener();
        }
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
        detachDatabaseReadListener();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu2, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.filters:
                Intent intent = new Intent(getContext(), EventFilter.class);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public CardModel createCard(int i) {

        return new CardModel("The Drunk House " + Integer.toString(i), "Rajouri", "1000");
    }

    private void attachDatabaseReadListener() {
        if (valueEventListener == null) {
            progressBar.setVisibility(View.VISIBLE);
            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    categorySource.clear();
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                        CategoryModel model = childDataSnapshot.getValue(CategoryModel.class);
                        categorySource.add(model);
                        gridAdapter.notifyDataSetChanged(); //gives the value for given keyname

                    }
                    progressBar.setVisibility(View.INVISIBLE);

                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }


            };
            mDatabaseReference.addValueEventListener(valueEventListener);

        }
    }

    private void detachDatabaseReadListener() {
        if (valueEventListener != null) {
            mDatabaseReference.removeEventListener(valueEventListener);
            valueEventListener = null;
        }
    }
    class UpdateTimeTask extends TimerTask {
        public void run() {
            viewPager.post(new Runnable() {
                public void run() {

                    if (viewPager.getCurrentItem() < imageAdapter
                            .getCount() - 1) {
                        viewPager.setCurrentItem(
                                viewPager.getCurrentItem() + 1, true);


                    } else {
                        viewPager.setCurrentItem(0, true);

                    }
                }
            });
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
