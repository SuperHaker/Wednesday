package com.example.android.wednesday.adapters;

/**
 * Created by hp pc on 2/1/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.wednesday.R;
import com.example.android.wednesday.activities.DineoutDetailsActivity;
import com.example.android.wednesday.models.CardModel;

import java.util.Collections;
import java.util.List;


public class FeaturedDineoutAdapter extends RecyclerView.Adapter<FeaturedDineoutAdapter.FeaturedViewHolder> {

    private final LayoutInflater inflater;
    List<CardModel> data = Collections.EMPTY_LIST;
    private Context context;

    public FeaturedDineoutAdapter(Context context, List<CardModel> dataSource) {
        inflater = LayoutInflater.from(context);
        this.data = dataSource;
        this.context = context;
    }

    @Override
    public FeaturedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.featured_dineout_card, parent, false);
        FeaturedViewHolder featuredViewHolder = new FeaturedViewHolder(context, view);
        return featuredViewHolder;

    }


    @Override
    public void onBindViewHolder(FeaturedViewHolder holder, int position) {

//        CardModel currentCard = data.get(position);
//        holder.activityPlace.setText(currentCard.eventPlace);
//        holder.activityAddress.setText(currentCard.eventAddress);
//        holder.activityCost.setText(currentCard.eventCost);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class FeaturedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

//        TextView activityPlace;
//        TextView activityAddress;
//        TextView activityCost;
        private Context context;

        public FeaturedViewHolder(Context context, View view){
            super(view);
            this.context = context;
//            activityPlace = (TextView) itemView.findViewById(R.id.activity_place);
//            activityAddress = (TextView) itemView.findViewById(R.id.activity_address);
//            activityCost = (TextView) itemView.findViewById(R.id.activity_cost);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, DineoutDetailsActivity.class);
            context.startActivity(intent);
        }
    }


}



