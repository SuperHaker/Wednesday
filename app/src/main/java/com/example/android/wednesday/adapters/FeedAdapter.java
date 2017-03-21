package com.example.android.wednesday.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.wednesday.R;
import com.example.android.wednesday.models.ImageUriModel;
import com.example.android.wednesday.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.robertlevonyan.views.chip.Chip;

import java.util.Collections;
import java.util.List;

/**
 * Created by hp pc on 3/12/2017.
 */


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    DatabaseReference databaseReference;

    private final LayoutInflater inflater;
    public Context context;
    List<ImageUriModel> list = Collections.EMPTY_LIST;
    public FeedAdapter(Context context, List<ImageUriModel> list, DatabaseReference databaseReference){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
        this.databaseReference = databaseReference.child("users");

    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.feed_layout, parent, false);

        return new FeedViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(final FeedViewHolder holder, int position) {
            ImageUriModel currentCard = list.get(position);
                Glide.with(context)
                .load(currentCard.link)
                .into(holder.imageView);

        databaseReference.child(currentCard.userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                holder.userName.setText(user.username);
                Glide.with(context)
                        .load(user.userPhoto)
                        .into(holder.userImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class FeedViewHolder extends RecyclerView.ViewHolder {
        View tagHolder;
        Context context;
        View v;
        Chip chip;
        LayoutInflater vi;
        ImageView imageView;
        ImageView userImage;
        TextView userName;

        public FeedViewHolder(View itemView, Context context){
            super(itemView);
            this.context = context;
            imageView = (ImageView) itemView.findViewById(R.id.feed_image);
            userImage = (ImageView) itemView.findViewById(R.id.user_image);
            userName = (TextView) itemView.findViewById(R.id.user_name);


        }

    }


}
