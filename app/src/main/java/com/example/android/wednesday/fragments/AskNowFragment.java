package com.example.android.wednesday.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

import com.example.android.wednesday.R;
import com.example.android.wednesday.activities.AllAnswersActivity;
import com.example.android.wednesday.activities.AskQuestionActivity;
import com.example.android.wednesday.adapters.AskNowAdapter;
import com.example.android.wednesday.adapters.AskNowArrayAdapter;
import com.example.android.wednesday.models.AnswerModel;
import com.example.android.wednesday.models.AskQuestionModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
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
    private AutoCompleteTextView editText;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    List<AskQuestionModel> dataSource;
    ProgressBar progressBar;
    ChildEventListener childEventListener;
    AskNowArrayAdapter arrayAdapter;

    final List<AskQuestionModel> list = new ArrayList<>();
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
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AskNowTheme);

        // clone the inflater using the ContextThemeWrapper
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);


        View v =  localInflater.inflate(R.layout.fragment_ask_now, container, false);
        progressBar = (ProgressBar) v.findViewById(R.id.progress);
                recyclerView = (RecyclerView) v.findViewById(R.id.ask_now_list);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        editText = (AutoCompleteTextView) v.findViewById(R.id.ask_now_edittext);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String queryText = editText.getText().toString();
                if(!queryText.isEmpty()){

                    Query query = databaseReference.orderByChild("question")
                            .startAt(queryText)
                            .endAt(queryText + "\uf8ff");
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            list.clear();
                            if(dataSnapshot.exists()) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    AskQuestionModel model = ds.getValue(AskQuestionModel.class);
                                    model.quesId = ds.getKey();
                                    list.add(model);
                                }
                            }
                            list.add(new AskQuestionModel("Add your question", null, null));
                            arrayAdapter = new AskNowArrayAdapter(getContext(), R.layout.dropdown_item, list);
                            editText.setAdapter(arrayAdapter);
                            editText.showDropDown();


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });

                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



//        editText.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                    editText.showDropDown();
//                }
//                return false;
//            }
//        });

        editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(((AskQuestionModel)adapterView.getItemAtPosition(i)).question
                        .equals("Add your question")) {
                    editText.setText("");
                    Intent intent  = new Intent(getContext(), AskQuestionActivity.class);
                    intent.putExtra("question", editText.getText().toString());
                    startActivity(intent);
                }
                else{
                    editText.setText("");
                    String quesid = list.get(i).quesId;
                    Intent intent  = new Intent(getContext(), AllAnswersActivity.class);
                    intent.putExtra("userId", list.get(i).asker);
                    intent.putExtra("quesId", quesid);
                    intent.putExtra("question", list.get(i).question);
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

//            childEventListener = new ChildEventListener() {
//                @Override
//                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                    AskQuestionModel model = dataSnapshot.getValue(AskQuestionModel.class);
//                    model.userId = childDataSnapshot.getKey();
//                    model.quesId = ds.getKey();
//                    dataSource.add(model);
//
//                }
//
//                @Override
//                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                }
//
//                @Override
//                public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                }
//
//                @Override
//                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            };


            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    dataSource.clear();
                    list.clear();
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {

                            AskQuestionModel model = childDataSnapshot.getValue(AskQuestionModel.class);
                            GenericTypeIndicator<Map<String, AnswerModel>> t = new GenericTypeIndicator<Map<String, AnswerModel>>() {};
                            Map<String, AnswerModel> map =  childDataSnapshot.child("answers").getValue(t);
                            if(map != null) {
                                Collection<AnswerModel> collection = map.values();
//                                List<AnswerModel> answerModel = new ArrayList<>();
//                                answerModel.addAll(collection);
//                                model.answersList = answerModel;
                                model.map = map;
                            }
                            model.quesId = childDataSnapshot.getKey();
                            dataSource.add(model);
                            adapter.notifyDataSetChanged();

                    }
                    progressBar.setVisibility(View.GONE);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {


                }

            };
            databaseReference.addValueEventListener(valueEventListener);
            adapter = new AskNowAdapter(getContext(), dataSource, databaseReference);
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

