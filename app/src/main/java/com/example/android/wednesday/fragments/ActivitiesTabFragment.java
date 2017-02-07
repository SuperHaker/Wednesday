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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.wednesday.R;
import com.example.android.wednesday.activities.ActivitiesFilter;
import com.example.android.wednesday.adapters.FeaturedActivitiesAdapter;
import com.example.android.wednesday.adapters.GridAdapter;
import com.example.android.wednesday.adapters.TopPicksActivitiesAdapter;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ActivitiesTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivitiesTabFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView mRecyclerViewTopPicks;
    private LoopRecyclerViewPager mLoopRecyclerView;

    private FeaturedActivitiesAdapter mAdapter;
    private TopPicksActivitiesAdapter mTopPicksAdapter;
    private RecyclerView.LayoutManager mLayoutManagerFeatured;
    private RecyclerView.LayoutManager mLayoutManagerTopPicks;
    GridAdapter gridAdapter;
    private Handler handler;
    private Runnable runnable;
    final int speedScroll = 2000;
    ValueEventListener valueEventListener;
    private DatabaseReference mDatabaseReference;
    List<CategoryModel> categorySource;

    GridLayoutManager mGridManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ActivitiesTabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActivitiesTabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivitiesTabFragment newInstance(String param1, String param2) {
        ActivitiesTabFragment fragment = new ActivitiesTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_tab_two, container, false);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("activities").child("categories");
        mLoopRecyclerView = (LoopRecyclerViewPager) rootView.findViewById(R.id.featured_picks_activities);
        mRecyclerViewTopPicks = (RecyclerView) rootView.findViewById(R.id.top_picks_activities);
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

        mAdapter = new FeaturedActivitiesAdapter(getContext(), dataSource);
        mTopPicksAdapter = new TopPicksActivitiesAdapter(getContext(), dataSource);

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

        attachDatabaseReadListener();

        mGridManager = new GridLayoutManager(getContext(), 2);
        RecyclerView categoryRecyclerView = (RecyclerView) rootView.findViewById(R.id.categories_activities);
        categoryRecyclerView.setHasFixedSize(true);
        categoryRecyclerView.setLayoutManager(mGridManager);

        gridAdapter = new GridAdapter(getContext(), categorySource, 2);
        categoryRecyclerView.setAdapter(gridAdapter);
        categoryRecyclerView.setNestedScrollingEnabled(false);

        return rootView;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu2, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case R.id.filters:
                Intent intent = new Intent(getContext(), ActivitiesFilter.class);
                startActivity(intent);
                return true;

        }return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        detachDatabaseReadListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable,speedScroll);


    }

    public CardModel createCard(int i){

        return  new CardModel("Random Lake " + Integer.toString(i), "Kasol", "Cost");
    }

    private void attachDatabaseReadListener(){
        if(valueEventListener == null) {
            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    categorySource.clear();
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

            };
            mDatabaseReference.addValueEventListener(valueEventListener);

        }
    }

    private void detachDatabaseReadListener(){
        if(valueEventListener != null){
            mDatabaseReference.removeEventListener(valueEventListener);
            valueEventListener = null;
        }
    }
}




