package com.example.android.wednesday.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.wednesday.R;
import com.example.android.wednesday.adapters.FeaturedAdapter;
import com.example.android.wednesday.adapters.GridAdapter;
import com.example.android.wednesday.adapters.TopPicksAdapter;
import com.example.android.wednesday.models.CardModel;
import com.example.android.wednesday.models.CategoryModel;
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

    private FeaturedAdapter mAdapter;
    private TopPicksAdapter mTopPicksAdapter;
    private RecyclerView.LayoutManager mLayoutManagerFeatured;
    private RecyclerView.LayoutManager mLayoutManagerTopPicks;

    GridLayoutManager mGridManager;

    final int speedScroll = 2000;
    private Handler handler;
    private Runnable runnable;



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

        mLoopRecyclerView = (LoopRecyclerViewPager) rootView.findViewById(R.id.featured_picks);
        mRecyclerViewTopPicks = (RecyclerView) rootView.findViewById(R.id.top_picks);

        mRecyclerViewTopPicks.setHasFixedSize(true);
        mLoopRecyclerView.setHasFixedSize(true);

        mLayoutManagerFeatured = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mLayoutManagerTopPicks = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        mLoopRecyclerView.setLayoutManager(mLayoutManagerFeatured);
        mRecyclerViewTopPicks.setLayoutManager(mLayoutManagerTopPicks);

        List<CardModel> dataSource = new ArrayList<CardModel>();

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




        List<CategoryModel> categorySource = new ArrayList<>();

        for(int i = 0;i < 10; i++){
            categorySource.add(createCategory());
        }

        mGridManager = new GridLayoutManager(getContext(), 2);
        RecyclerView categoryRecyclerView = (RecyclerView) rootView.findViewById(R.id.categories);
        categoryRecyclerView.setHasFixedSize(true);
        categoryRecyclerView.setLayoutManager(mGridManager);

        GridAdapter gridAdapter = new GridAdapter(getContext(), categorySource);
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

    public CategoryModel createCategory(){
        return new CategoryModel("Category");
    }


}
