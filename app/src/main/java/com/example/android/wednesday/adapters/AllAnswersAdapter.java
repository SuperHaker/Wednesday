package com.example.android.wednesday.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.android.wednesday.R;
import com.example.android.wednesday.models.AnswerModel;
import com.example.android.wednesday.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hp pc on 3/24/2017.
 */


public class AllAnswersAdapter extends RecyclerView.Adapter<AllAnswersAdapter.AllAnswersViewHolder> {

    LayoutInflater inflater;
    Context context;
    Map<String, AnswerModel> map = new LinkedHashMap<>();
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    List<AnswerModel> list = Collections.EMPTY_LIST;
    List<String> keyList = new ArrayList<>();
    DatabaseReference userReference;


    public AllAnswersAdapter(Context context, Map<String, AnswerModel> map, DatabaseReference databaseReference, List<String> keyList) {
        inflater = LayoutInflater.from(context);
        this.context = context;
//        if(map != null) {
//            list = new ArrayList<AnswerModel>(map.values());
//        }

        this.map = map;
        userReference = FirebaseDatabase.getInstance().getReference().child("users");
        this.databaseReference = databaseReference;
        this.keyList = keyList;

    }


    @Override
    public AllAnswersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.answer_layout, parent, false);
        return new AllAnswersViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final AllAnswersViewHolder holder, int position) {
        holder.userImage.setImageResource(R.drawable.user);

        if (!map.isEmpty() || map != null) {
           final AnswerModel model = map.get(keyList.get(position));
            holder.ans.setText(model.answer);
            holder.timeStamp.setText(convertTime(model.time.get("timestamp").toString()));

            userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user =  dataSnapshot.child(model.answererUid).getValue(User.class);
                    Glide.with(context)
                            .load(user.userPhoto)
                            .into(holder.userImage);
                    holder.userName.setText(user.username);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            holder.votes.setText(Long.toString(model.upvotes.number));
            if ( map.get(keyList.get(position)).upvotes.listOfPeople != null) {
                Map<String, Boolean> votersMap =  map.get(keyList.get(position)).upvotes.listOfPeople;
                if (votersMap.containsKey(user.getUid())) {
                    if (votersMap.get(user.getUid())) {
                        holder.upvote.setChecked(true);
                    } else if (!votersMap.get(user.getUid())) {
                        holder.downvote.setChecked(true);
                    }
                } else {
                    holder.upvote.setChecked(false);
                    holder.downvote.setChecked(false);
                }

            } else {
                holder.upvote.setChecked(false);
                holder.downvote.setChecked(false);
            }
        }
    }

    @Override
    public int getItemCount() {
        return map.size();
    }

    public String convertTime(String time){
        Date date = new Date(Long.parseLong(time));
        SimpleDateFormat sdf;
        if((System.currentTimeMillis() - Long.parseLong(time))/3600000 >= 24){
            sdf = new SimpleDateFormat("dd MMM");
        }
//        else if((System.currentTimeMillis() - Long.parseLong(time))/(long)(3600000*24*365) >= 1){
//            sdf = new SimpleDateFormat("dd|MM|yy");
//        }
        else{
            sdf = new SimpleDateFormat("HH:mm");
        }
        // the format of your date
        sdf.setTimeZone(Calendar.getInstance().getTimeZone()); // give a timezone reference for formating (see comment at the bottom
        return sdf.format(date);
    }

    class AllAnswersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView ans;
        ToggleButton upvote;
        ToggleButton downvote;
        TextView votes, userName, timeStamp;
        CircleImageView userImage;

        public AllAnswersViewHolder(View itemView) {
            super(itemView);
            ans = (TextView) itemView.findViewById(R.id.answer);
            upvote = (ToggleButton) itemView.findViewById(R.id.upvote_answer);
            downvote = (ToggleButton) itemView.findViewById(R.id.downvote_answer);
            votes = (TextView) itemView.findViewById(R.id.votes);
            timeStamp = (TextView) itemView.findViewById(R.id.timeStamp);
            userImage = (CircleImageView) itemView.findViewById(R.id.user_image);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            upvote.setOnClickListener(this);
            downvote.setOnClickListener(this);

        }

        @Override
        public void onClick(final View view) {
            //(new ArrayList<String>(linkedHashMap.values())).get(pos);

            databaseReference.child(map.get(keyList.get(getAdapterPosition()))
                    .answerId).child("upvotes").child("number")
                    .runTransaction(new Transaction.Handler() {
                        String message = "";
                        Long value;

                        @Override
                        public Transaction.Result doTransaction(MutableData mutableData) {
                            if (mutableData.getValue() == null) {
                                return Transaction.success(mutableData);
                            }
                            value = (Long) mutableData.getValue();

                            if (view == upvote) {
                                if (upvote.isChecked()) {
                                    value += 1;

                                    message = "Upvoted";
                                    if(downvote.isChecked()) {
                                        ((Activity)context).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                downvote.setChecked(false);

                                            }
                                        });
                                        value++;
                                    }


                                    databaseReference.child(map.get(keyList.get(getAdapterPosition()))
                                            .answerId).child("upvotes")
                                            .child("listOfPeople").child(user.getUid()).setValue(true);
                                    mutableData.setValue(value);


                                } else if (!upvote.isChecked()) {
                                    value -= 1;
                                    message = "Upvote removed";

                                    databaseReference.child(map.get(keyList.get(getAdapterPosition()))
                                            .answerId).child("upvotes")
                                            .child("listOfPeople").child(user.getUid()).setValue(null);
                                    mutableData.setValue(value);

                                }
                            }

                           if (view == downvote) {
                                if (downvote.isChecked()) {
                                    value -= 1;
                                    message = "Downvoted";

                                    if(upvote.isChecked()) {
                                        ((Activity)context).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                upvote.setChecked(false);
                                            }});
                                        value--;
                                    }
                                    databaseReference.child(map.get(keyList.get(getAdapterPosition()))
                                            .answerId).child("upvotes")
                                            .child("listOfPeople").child(user.getUid()).setValue(false);
                                    mutableData.setValue(value);

                                } else if (!downvote.isChecked()) {
                                    value += 1;
                                    message = "Downvote removed";

                                    mutableData.setValue(value);
                                    databaseReference.child(map.get(keyList.get(getAdapterPosition()))
                                            .answerId).child("upvotes")
                                            .child("listOfPeople").child(user.getUid()).setValue(null);
                                    mutableData.setValue(value);


                                }
                            }

                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            votes.setText(value.toString());
                        }
                    });

        }
    }

}

