package com.example.android.wednesday.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.wednesday.R;
import com.example.android.wednesday.adapters.AllAnswersAdapter;
import com.example.android.wednesday.models.AnswerModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AllAnswersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    AllAnswersAdapter adapter;
    TextView ques;
    Map<String, AnswerModel> dataSource = new LinkedHashMap<>();
    DatabaseReference databaseReference;
    ChildEventListener childEventListener = null;
    Button writeAnswer;
    List<String> keyList = new ArrayList<>();
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_answers);
        Intent intent = getIntent();
        final String s1 =  intent.getStringExtra("userId");
        final String s2 = intent.getStringExtra("quesId");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("asknow")
                .child(s2).child("answers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ques = (TextView) findViewById(R.id.question);
        writeAnswer = (Button) findViewById(R.id.write_answer);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        ques.setText(intent.getStringExtra("question"));
        recyclerView = (RecyclerView) findViewById(R.id.answers_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(AllAnswersActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        writeAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), WriteAnswerActivity.class);
                i.putExtra("question", ques.getText().toString());
                i.putExtra("userId", s1);
                i.putExtra("quesId", s2);
                startActivity(i);
            }
        });
//        adapter = new AllAnswersAdapter(getApplicationContext());
//        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(childEventListener == null){
            attachDatabaseReadListener();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        detachDatabaseReadListener();
    }

    public void attachDatabaseReadListener(){

        if(childEventListener == null){
            progressBar.setVisibility(View.VISIBLE);
            dataSource.clear();
            keyList.clear();
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    AnswerModel model = dataSnapshot.getValue(AnswerModel.class);
                    model.answerId = dataSnapshot.getKey();
                    keyList.add(dataSnapshot.getKey());
                    dataSource.put(dataSnapshot.getKey(), model);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    AnswerModel model = dataSnapshot.getValue(AnswerModel.class);
                    model.answerId = dataSnapshot.getKey();
                    dataSource.put(dataSnapshot.getKey(), model);
//                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
//            databaseReference.addChildEventListener(childEventListener);
            adapter = new AllAnswersAdapter(this, dataSource, databaseReference, keyList);
            progressBar.setVisibility(View.GONE);
            recyclerView.setAdapter(adapter);
        }


//        if (valueEventListener == null) {
//            progressBar.setVisibility(View.VISIBLE);
//            valueEventListener = new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    dataSource.clear();
//                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                        AnswerModel model = ds.getValue(AnswerModel.class);
//                        dataSource.add(model);
//                        adapter.notifyDataSetChanged();
//
//                    }
////                if(map != null)
////                    map.clear();
////                GenericTypeIndicator<Map<String, AnswerModel>> t = new GenericTypeIndicator<Map<String, AnswerModel>>() {};
////                 map = dataSnapshot.getValue(t);
//                    progressBar.setVisibility(View.GONE);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//
//            };
//            databaseReference.addValueEventListener(valueEventListener);
//            adapter = new AllAnswersAdapter(getApplicationContext(), dataSource);
//            recyclerView.setAdapter(adapter);
//        }
    }

    public void detachDatabaseReadListener(){
        if(childEventListener != null){
            databaseReference.removeEventListener(childEventListener);
            childEventListener = null;
        }

    }

}
