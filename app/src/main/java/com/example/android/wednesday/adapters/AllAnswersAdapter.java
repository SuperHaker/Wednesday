package com.example.android.wednesday.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.wednesday.R;
import com.example.android.wednesday.models.AnswerModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.List;
import java.util.Map;

/**
 * Created by hp pc on 3/24/2017.
 */


public class AllAnswersAdapter extends RecyclerView.Adapter<AllAnswersAdapter.AllAnswersViewHolder> {

    LayoutInflater inflater;
    Context context;
    List<AnswerModel> list;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    public AllAnswersAdapter(Context context, List<AnswerModel> list, DatabaseReference databaseReference) {
        inflater = LayoutInflater.from(context);
        this.context = context;
//        if(map != null) {
//            list = new ArrayList<AnswerModel>(map.values());
//        }

        this.list = list;
        this.databaseReference = databaseReference;


    }


    @Override
    public AllAnswersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.answer_layout, parent, false);
        return new AllAnswersViewHolder(view);

    }

    @Override
    public void onBindViewHolder(AllAnswersViewHolder holder, int position) {
        if (!list.isEmpty() || list != null) {
            AnswerModel model = list.get(position);
            holder.ans.setText(model.answer);
            holder.votes.setText(Long.toString(model.upvotes.number));
            if (list.get(position).upvotes.listOfPeople != null) {
                Map<String, Boolean> map = list.get(position).upvotes.listOfPeople;
                if (map.containsKey(user.getUid())) {
                    if (map.get(user.getUid())) {
                        holder.upvote.setImageResource(R.drawable.ic_arrow_up_drop_circle_black_24dp);
                    } else if (!map.get(user.getUid())) {
                        holder.downvote.setImageResource(R.drawable.ic_arrow_down_drop_circle_black_24dp);
                    }
                } else {
                    holder.upvote.setImageResource(R.drawable.ic_arrow_up_drop_circle_outline_black_24dp);
                    holder.downvote.setImageResource(R.drawable.ic_arrow_down_drop_circle_outline_black_24dp);
                }

            } else {
                holder.upvote.setImageResource(R.drawable.ic_arrow_up_drop_circle_outline_black_24dp);
                holder.downvote.setImageResource(R.drawable.ic_arrow_down_drop_circle_outline_black_24dp);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class AllAnswersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView ans;
        ImageView upvote;
        ImageView downvote;
        TextView votes;

        public AllAnswersViewHolder(View itemView) {
            super(itemView);
            ans = (TextView) itemView.findViewById(R.id.answer);
            upvote = (ImageView) itemView.findViewById(R.id.upvote_answer);
            downvote = (ImageView) itemView.findViewById(R.id.downvote_answer);
            votes = (TextView) itemView.findViewById(R.id.votes);
            upvote.setOnClickListener(this);
            downvote.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            if (view == upvote || view == downvote) {
//                 Map<String, Boolean> map = new HashMap<String, Boolean>(list.get(getAdapterPosition()).upvotes.listOfPeople);
//                 List<String> userKeys = new ArrayList<>(set.keySet());
//                 if(map.containsKey(user.getUid())){
//                     Toast.makeText(context, "Already done", Toast.LENGTH_SHORT).show();
//                 } else {
                databaseReference.child(list.get(getAdapterPosition()).answerId).child("upvotes").child("number")
                        .runTransaction(new Transaction.Handler() {
                            String message = "";
                            Long value;

                            @Override
                            public Transaction.Result doTransaction(MutableData mutableData) {
                                value = (Long) mutableData.getValue();
                                if (value == null) {
                                    return Transaction.success(mutableData);
                                }

                                if (view == upvote) {
                                    if (upvote.getDrawable().getConstantState() ==
                                            context.getResources().getDrawable(R.drawable.ic_arrow_up_drop_circle_outline_black_24dp).getConstantState()) {
                                        value += 1;
                                        message = "Upvoted";
                                        upvote.setImageResource(R.drawable.ic_arrow_up_drop_circle_black_24dp);
                                        downvote.setImageResource(R.drawable.ic_arrow_down_drop_circle_outline_black_24dp);
                                        databaseReference.child(list.get(getAdapterPosition()).answerId).child("upvotes")
                                                .child("listOfPeople").child(user.getUid()).setValue(true);

                                    } else if(upvote.getDrawable().getConstantState() ==
                                            context.getResources().getDrawable(R.drawable.ic_arrow_up_drop_circle_black_24dp).getConstantState()){
                                        value -= 1;
                                        message = "Upvote removed";
                                        upvote.setImageResource(R.drawable.ic_arrow_up_drop_circle_outline_black_24dp);
                                        databaseReference.child(list.get(getAdapterPosition()).answerId).child("upvotes")
                                                .child("listOfPeople").child(user.getUid()).setValue(null);
                                    }

                                } else if (view == downvote) {
                                    if (downvote.getDrawable().getConstantState() ==
                                            context.getResources().getDrawable(R.drawable.ic_arrow_down_drop_circle_outline_black_24dp).getConstantState()) {
                                        value -= 1;
                                        message = "Downvoted";
                                        upvote.setImageResource(R.drawable.ic_arrow_up_drop_circle_outline_black_24dp);
                                        downvote.setImageResource(R.drawable.ic_arrow_down_drop_circle_black_24dp);
                                        databaseReference.child(list.get(getAdapterPosition()).answerId).child("upvotes")
                                                .child("listOfPeople").child(user.getUid()).setValue(false);
                                    } else if(downvote.getDrawable().getConstantState() ==
                                            context.getResources().getDrawable(R.drawable.ic_arrow_down_drop_circle_black_24dp).getConstantState()){
                                        value += 1;
                                        message = "Downvote removed";
                                        downvote.setImageResource(R.drawable.ic_arrow_down_drop_circle_outline_black_24dp);
                                        databaseReference.child(list.get(getAdapterPosition()).answerId).child("upvotes")
                                                .child("listOfPeople").child(user.getUid()).setValue(null);
                                    }
                                }
                                mutableData.setValue(value);

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

//         }
    }
}
