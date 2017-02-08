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
import android.widget.TextView;

import com.example.android.wednesday.R;
import com.example.android.wednesday.activities.DineoutFilter;
import com.example.android.wednesday.adapters.DineoutCollectionsGridAdapter;
import com.example.android.wednesday.adapters.DineoutCuisinesGridAdapter;
import com.example.android.wednesday.adapters.DineoutLocationsGridAdapter;
import com.example.android.wednesday.adapters.FeaturedDineoutAdapter;
import com.example.android.wednesday.adapters.GridAdapter;
import com.example.android.wednesday.adapters.TopPicksDineoutAdapter;
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
 * create an instance of this fragment.
 */
public class DineOutTabFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerViewTopPicks;
    private LoopRecyclerViewPager mLoopRecyclerView;
    private RecyclerView dineoutLocationsRecyclerView;
    private RecyclerView dineoutCuisinesRecyclerView;
    private RecyclerView dineoutCollectionsRecyclerView;


    private FeaturedDineoutAdapter mAdapter;
    private TopPicksDineoutAdapter mTopPicksAdapter;
    private DineoutLocationsGridAdapter dineoutLocationsGridAdapter;
    private DineoutCuisinesGridAdapter dineoutCuisinesGridAdapter;
    private DineoutCollectionsGridAdapter dineoutCollectionsGridAdapter;
    List<CategoryModel> categorySource;

    private RecyclerView.LayoutManager mLayoutManagerFeatured;
    private RecyclerView.LayoutManager mLayoutManagerTopPicks;

    GridAdapter gridAdapter;

    private Handler handler;
    private Runnable runnable;
    final int speedScroll = 2000;

    GridLayoutManager mGridManager;
    GridLayoutManager gridLayoutManager;
    GridLayoutManager gridLayoutManager2;
    GridLayoutManager gridLayoutManager3;

    List<CategoryModel> locationList = new ArrayList<>();
    List<CategoryModel> cuisineList = new ArrayList<>();
    List<CategoryModel> collectionsList = new ArrayList<>();
    ValueEventListener valueEventListener;
    private DatabaseReference mDatabaseReference;

    List<CardModel> dataSource;



    public DineOutTabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DineOutTabFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        final View rootView =  inflater.inflate(R.layout.fragment_dine_out, container, false);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("dineouts").child("categories");

        mLoopRecyclerView = (LoopRecyclerViewPager) rootView.findViewById(R.id.featured_picks_dineout);
        mRecyclerViewTopPicks = (RecyclerView) rootView.findViewById(R.id.top_picks_dineout);
        mRecyclerViewTopPicks.setHasFixedSize(true);
        mLoopRecyclerView.setHasFixedSize(true);

        mLayoutManagerFeatured = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mLayoutManagerTopPicks = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        mLoopRecyclerView.setLayoutManager(mLayoutManagerFeatured);
        mRecyclerViewTopPicks.setLayoutManager(mLayoutManagerTopPicks);

        dataSource = new ArrayList<CardModel>();

        for(int i = 0;i<5; i++){
            dataSource.add(createCard(i));
        }

        mAdapter = new FeaturedDineoutAdapter(getContext(), dataSource);
        mTopPicksAdapter = new TopPicksDineoutAdapter(getContext(), dataSource);

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
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager2 = new GridLayoutManager(getContext(), 2);
        gridLayoutManager3 = new GridLayoutManager(getContext(), 2);
        RecyclerView categoryRecyclerView = (RecyclerView) rootView.findViewById(R.id.categories_dineout);
        dineoutLocationsRecyclerView = (RecyclerView) rootView.findViewById(R.id.locations_dineout_grid);
        dineoutCuisinesRecyclerView = (RecyclerView) rootView.findViewById(R.id.cuisines_dineout_grid);
        dineoutCollectionsRecyclerView = (RecyclerView) rootView.findViewById(R.id.collections_dineout_grid);

        categoryRecyclerView.setHasFixedSize(true);
        dineoutLocationsRecyclerView.setHasFixedSize(true);
        dineoutCollectionsRecyclerView.setHasFixedSize(true);
        dineoutCuisinesRecyclerView.setHasFixedSize(true);

        categoryRecyclerView.setLayoutManager(mGridManager);
        dineoutLocationsRecyclerView.setLayoutManager(gridLayoutManager);
        dineoutCuisinesRecyclerView.setLayoutManager(gridLayoutManager2);
        dineoutCollectionsRecyclerView.setLayoutManager(gridLayoutManager3);

        gridAdapter = new GridAdapter(getContext(), categorySource,3);
        categoryRecyclerView.setAdapter(gridAdapter);
        categoryRecyclerView.setNestedScrollingEnabled(false);
        locationList.clear();
        createLocationList();
        dineoutLocationsGridAdapter = new DineoutLocationsGridAdapter(getContext(), locationList);
        cuisineList.clear();
        collectionsList.clear();
        dineoutCollectionsGridAdapter = new DineoutCollectionsGridAdapter(getContext(), collectionsList);
        dineoutCuisinesGridAdapter = new DineoutCuisinesGridAdapter(getContext(), cuisineList);

        dineoutLocationsRecyclerView.setAdapter(dineoutLocationsGridAdapter);
        dineoutCuisinesRecyclerView.setAdapter(dineoutCuisinesGridAdapter);
        dineoutCollectionsRecyclerView.setAdapter(dineoutCollectionsGridAdapter);





        dineoutLocationsRecyclerView.setNestedScrollingEnabled(false);
        dineoutCuisinesRecyclerView.setNestedScrollingEnabled(false);
        dineoutCollectionsRecyclerView.setNestedScrollingEnabled(false);
       final TextView button1 = (TextView) rootView.findViewById(R.id.dineout_locations_button);
        final TextView button2 = (TextView) rootView.findViewById(R.id.dineout_cuisines_button);
        final TextView button3 = (TextView) rootView.findViewById(R.id.dineout_collections_button);




        rootView.findViewById(R.id.dineout_locations_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(locationList.isEmpty()){
                    createLocationList();
                    button1.setBackgroundResource(R.drawable.rounded_pressed);
                    button2.setBackgroundResource(R.drawable.rounded_corners);
                    button3.setBackgroundResource(R.drawable.rounded_corners);

                    button1.setTextColor(getResources().getColor(android.R.color.white));
                    button2.setTextColor(getResources().getColor(android.R.color.black));
                    button3.setTextColor(getResources().getColor(android.R.color.black));

                    dineoutLocationsGridAdapter = new DineoutLocationsGridAdapter(getContext(), locationList);
                    dineoutLocationsRecyclerView.setAdapter(dineoutLocationsGridAdapter);
                    cuisineList.clear();
                    collectionsList.clear();
                    dineoutLocationsGridAdapter.notifyDataSetChanged();
                    dineoutCuisinesGridAdapter.notifyDataSetChanged();
                    dineoutCollectionsGridAdapter.notifyDataSetChanged();



                }


            }
        });

        rootView.findViewById(R.id.dineout_cuisines_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cuisineList.isEmpty()){
                    createCuisineList();
                    button2.setBackgroundResource(R.drawable.rounded_pressed);
                    button1.setBackgroundResource(R.drawable.rounded_corners);
                    button3.setBackgroundResource(R.drawable.rounded_corners);

                    button2.setTextColor(getResources().getColor(android.R.color.white));
                    button1.setTextColor(getResources().getColor(android.R.color.black));
                    button3.setTextColor(getResources().getColor(android.R.color.black));

                    dineoutCuisinesGridAdapter = new DineoutCuisinesGridAdapter(getContext(), cuisineList);
                    dineoutCuisinesRecyclerView.setAdapter(dineoutCuisinesGridAdapter);
                    locationList.clear();
                    collectionsList.clear();
                    dineoutLocationsGridAdapter.notifyDataSetChanged();
                    dineoutCuisinesGridAdapter.notifyDataSetChanged();
                    dineoutCollectionsGridAdapter.notifyDataSetChanged();
                }
            }
        });

        rootView.findViewById(R.id.dineout_collections_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(collectionsList.isEmpty()){
                    createCollectionsList();
                    button3.setBackgroundResource(R.drawable.rounded_pressed);
                    button2.setBackgroundResource(R.drawable.rounded_corners);
                    button1.setBackgroundResource(R.drawable.rounded_corners);

                    button3.setTextColor(getResources().getColor(android.R.color.white));
                    button2.setTextColor(getResources().getColor(android.R.color.black));
                    button1.setTextColor(getResources().getColor(android.R.color.black));

                    dineoutCollectionsGridAdapter = new DineoutCollectionsGridAdapter(getContext(), collectionsList);
                    dineoutCollectionsRecyclerView.setAdapter(dineoutCollectionsGridAdapter);
                    cuisineList.clear();
                    locationList.clear();
                    dineoutLocationsGridAdapter.notifyDataSetChanged();
                    dineoutCuisinesGridAdapter.notifyDataSetChanged();
                    dineoutCollectionsGridAdapter.notifyDataSetChanged();
                }
            }
        });






        return rootView;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu2, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case R.id.filters:
                Intent intent = new Intent(getContext(), DineoutFilter.class);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
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

        return  new CardModel("Restaurant Name " + Integer.toString(i), "Place", "Cost");
    }

    public CategoryModel createCategory(){
        return new CategoryModel("Category");
    }


    public void createLocationList(){
        for(int i = 0;i < 10; i++){
            locationList.add(createLocation());
        }
    }

    public void createCuisineList(){
        for(int i = 0;i < 10; i++){
            cuisineList.add(createCuisine());
        }
    }

    public void createCollectionsList(){
        for(int i = 0;i < 10; i++){
            collectionsList.add(createCollection());
        }
    }

    public CategoryModel createLocation(){
        return new CategoryModel("Tilak Nagar");
    }

    public CategoryModel createCuisine(){
        return new CategoryModel("Continental");
    }

    public CategoryModel createCollection(){
        return new CategoryModel("Collection");
    }

    private void attachDatabaseReadListener(){
        if(valueEventListener == null) {
            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    categorySource.clear();
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
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







