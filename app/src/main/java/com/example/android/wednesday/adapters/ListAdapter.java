package com.example.android.wednesday.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.wednesday.R;
import com.example.android.wednesday.activities.EventDetailsActivity;
import com.example.android.wednesday.models.EventModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by hp pc on 1/29/2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.EventListViewHolder> {


    private final LayoutInflater inflater;
    private Context context;
    int type;
    List<EventModel> dataSource = Collections.EMPTY_LIST;

    public ListAdapter(Context context, List<EventModel> dataSource, int layoutType){
        inflater = LayoutInflater.from(context);
        this.dataSource = dataSource;
        this.context = context;
        type = layoutType;

    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    @Override
    public ListAdapter.EventListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == 1) {
             view = inflater.inflate(R.layout.event_listitem_layout, parent, false);
        }


        return new ListAdapter.EventListViewHolder(context, view, viewType);

    }

    @Override
    public void onBindViewHolder(ListAdapter.EventListViewHolder holder, int position) {
        EventModel currentCard = dataSource.get(position);
        holder.name.setText(currentCard.eventName);
        holder.place.setText(currentCard.eventPlace);
        holder.cost.setText(currentCard.eventCost);

    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    class EventListViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView place;
        TextView cost;
        ImageView heartEvent;
        private Context context;
        public EventListViewHolder(final Context context, View view,final int viewType){
            super(view);
            this.context = context;
            heartEvent = (ImageView) itemView.findViewById(R.id.favorite);
            heartEvent.setTag(R.drawable.whiteheart);
            if(viewType == 1) {
                name = (TextView) itemView.findViewById(R.id.event_name);
                place = (TextView) itemView.findViewById(R.id.event_place);
                cost = (TextView) itemView.findViewById(R.id.event_cost);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(viewType == 1){
                        Intent intent = new Intent(context, EventDetailsActivity.class);
                        context.startActivity(intent);
                    }

                }
            });
            heartEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if((Integer)heartEvent.getTag() == R.drawable.whiteheart) {
                        heartEvent.setImageResource(R.drawable.redheart);
                        heartEvent.setTag(R.drawable.redheart);
                    }
                    else{
                        heartEvent.setImageResource(R.drawable.whiteheart);
                        heartEvent.setTag(R.drawable.whiteheart);
                    }
                }
            });
        }


    }
}


