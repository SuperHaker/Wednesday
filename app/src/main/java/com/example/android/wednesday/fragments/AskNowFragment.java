package com.example.android.wednesday.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

import com.example.android.wednesday.R;
import com.example.android.wednesday.activities.AskQuestionActivity;
import com.example.android.wednesday.adapters.AskNowAdapter;
import com.example.android.wednesday.models.AnswerModel;
import com.example.android.wednesday.models.AskQuestionModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskNowFragment extends Fragment {
    private RecyclerView recyclerView;
    private AskNowAdapter adapter = null;
    private RecyclerView.LayoutManager mLayoutManager;
    private AutoCompleteTextView autoCompleteTextView;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    List<AskQuestionModel> dataSource;
    ProgressBar progressBar;

    public static final List<String> list = new ArrayList<String>();
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
        progressBar = (ProgressBar) v.findViewById(R.id.progress);
                recyclerView = (RecyclerView) v.findViewById(R.id.ask_now_list);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        autoCompleteTextView = (AutoCompleteTextView) v.findViewById(R.id.ask_now_edittext);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.dropdown_item, list);
        autoCompleteTextView.setAdapter(adapter);

//        autoCompleteTextView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                    autoCompleteTextView.showDropDown();
//                }
//                return false;
//            }
//        });

        autoCompleteTextView.setThreshold(0);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapter.getItem(i).equals("Add your question")) {
                    autoCompleteTextView.setText("");
                    Intent intent  = new Intent(getContext(), AskQuestionActivity.class);
                    intent.putExtra("question", autoCompleteTextView.getText().toString());
                    startActivity(intent);
                }

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
            progressBar.setVisibility(View.VISIBLE);
            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    dataSource.clear();
                    list.clear();
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                        for (DataSnapshot ds : childDataSnapshot.getChildren()) {
                            AskQuestionModel model = ds.getValue(AskQuestionModel.class);
                            GenericTypeIndicator<Map<String, AnswerModel>> t = new GenericTypeIndicator<Map<String, AnswerModel>>() {};
                            Map<String, AnswerModel> map =  ds.child("answers").getValue(t);
                            if(map != null) {
                                Collection<AnswerModel> collection = map.values();
//                                List<AnswerModel> answerModel = new ArrayList<>();
//                                answerModel.addAll(collection);
//                                model.answersList = answerModel;
                                model.map = map;
                            }
                                model.userId = childDataSnapshot.getKey();
                            model.quesId = ds.getKey();
                            dataSource.add(model);
                            list.add(model.question);


                            adapter.notifyDataSetChanged();
                        }
                    }
                    list.add("Add your question");
                    progressBar.setVisibility(View.GONE);

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

