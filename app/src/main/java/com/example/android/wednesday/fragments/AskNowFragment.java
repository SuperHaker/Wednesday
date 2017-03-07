package com.example.android.wednesday.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.wednesday.R;
import com.example.android.wednesday.adapters.AskNowAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskNowFragment extends Fragment {
    private RecyclerView recyclerView;
    private AskNowAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public AskNowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_ask_now, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.ask_now_list);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new AskNowAdapter(getContext());
        recyclerView.setAdapter(adapter);


        return v;
    }

}
