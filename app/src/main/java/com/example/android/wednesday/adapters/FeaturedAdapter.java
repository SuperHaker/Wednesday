package com.example.android.wednesday.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.wednesday.R;
import com.example.android.wednesday.activities.EventDetailsActivity;
import com.example.android.wednesday.models.CardModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by hp pc on 1/27/2017.
 */

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder> {

    private final LayoutInflater inflater;
    List<CardModel> data = Collections.EMPTY_LIST;
    private Context context;

    public FeaturedAdapter(Context context, List<CardModel> dataSource) {
        inflater = LayoutInflater.from(context);
        this.data = dataSource;
        this.context = context;
    }

    @Override
    public FeaturedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.image_card, parent, false);
        FeaturedViewHolder featuredViewHolder = new FeaturedViewHolder(context, view);
        return featuredViewHolder;

    }





    @Override
    public void onBindViewHolder(FeaturedViewHolder holder, int position) {

        CardModel currentCard = data.get(position);
        holder.eventPlace.setText(currentCard.eventPlace);
        holder.eventAddress.setText(currentCard.eventAddress);
        holder.eventCost.setText(currentCard.eventCost);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class FeaturedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView eventPlace;
        TextView eventAddress;
        TextView eventCost;
        private Context context;

        public FeaturedViewHolder(Context context, View view){
            super(view);
            this.context = context;
            eventPlace = (TextView) itemView.findViewById(R.id.event_place);
            eventAddress = (TextView) itemView.findViewById(R.id.event_address);
            eventCost = (TextView) itemView.findViewById(R.id.event_cost);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, EventDetailsActivity.class);
            context.startActivity(intent);
        }
    }


}



