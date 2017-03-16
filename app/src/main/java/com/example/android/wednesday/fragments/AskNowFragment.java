package com.example.android.wednesday.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.android.wednesday.R;
import com.example.android.wednesday.activities.AskQuestionActivity;
import com.example.android.wednesday.adapters.AskNowAdapter;
import com.example.android.wednesday.models.AskQuestionModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskNowFragment extends Fragment {
    private RecyclerView recyclerView;
    private AskNowAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private AutoCompleteTextView autoCompleteTextView;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    List<AskQuestionModel> dataSource;

    public static final String[] list = new String[] {
      "Add your question"
    };

    public AskNowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("asknow");

        dataSource = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v =  inflater.inflate(R.layout.fragment_ask_now, container, false);

                recyclerView = (RecyclerView) v.findViewById(R.id.ask_now_list);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        autoCompleteTextView = (AutoCompleteTextView) v.findViewById(R.id.ask_now_edittext);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, list);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    autoCompleteTextView.setFocusable(true);
                    autoCompleteTextView.requestFocus();
                    autoCompleteTextView.showDropDown();
                }
                return true;
            }
        });

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getContext(), AskQuestionActivity.class));

            }
        });

        if(valueEventListener == null) {
            attachDatabaseReadListener();
        }

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        detachDatabaseReadListener();
    }

    private void attachDatabaseReadListener() {
        if (valueEventListener == null) {
            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    dataSource.clear();
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                        AskQuestionModel model = childDataSnapshot.getValue(AskQuestionModel.class);
                        dataSource.add(model);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            };
            databaseReference.addValueEventListener(valueEventListener);
            adapter = new AskNowAdapter(getContext(), dataSource);
            recyclerView.setAdapter(adapter);
        }
    }
        private void detachDatabaseReadListener(){
            if(valueEventListener != null){
                databaseReference.removeEventListener(valueEventListener);
                valueEventListener = null;
            }
        }


    }

