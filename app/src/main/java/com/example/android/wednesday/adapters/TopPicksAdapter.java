package com.example.android.wednesday.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.wednesday.R;
import com.example.android.wednesday.activities.EventDetailsActivity;
import com.example.android.wednesday.models.CardModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by hp pc on 1/29/2017.
 */

public class TopPicksAdapter extends RecyclerView.Adapter<TopPicksAdapter.TopPicksViewHolder>  {

    private final LayoutInflater inflater;
    List<CardModel> data = Collections.EMPTY_LIST;
    private Context context;


    public TopPicksAdapter(Context context, List<CardModel> dataSource){
        inflater = LayoutInflater.from(context);
        this.data = dataSource;
        this.context = context;
    }

    @Override
    public TopPicksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.image_card, parent, false);
        TopPicksAdapter.TopPicksViewHolder topPicksViewHolder = new TopPicksAdapter.TopPicksViewHolder(context, view);
        return topPicksViewHolder;    }

    @Override
    public void onBindViewHolder(TopPicksViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class TopPicksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TopPicksViewHolder(Context context, View view){
            super(view);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, EventDetailsActivity.class);
            context.startActivity(intent);

        }
    }

}



