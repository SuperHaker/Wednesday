package com.example.android.wednesday.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.wednesday.R;
import com.example.android.wednesday.activities.EventDetailsActivity;
import com.example.android.wednesday.models.EventListitemModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by hp pc on 1/29/2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.EventListViewHolder> {


    private final LayoutInflater inflater;
    private Context context;
    List<EventListitemModel> dataSource = Collections.EMPTY_LIST;
    public ListAdapter(Context context, List<EventListitemModel> dataSource){
        inflater = LayoutInflater.from(context);
        this.dataSource = dataSource;
        this.context = context;

    }

    @Override
    public ListAdapter.EventListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.event_listitem_layout, parent, false);
        ListAdapter.EventListViewHolder listViewHolder = new ListAdapter.EventListViewHolder(context, view);
        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(ListAdapter.EventListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    class EventListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView heartEvent;
        private Context context;
        public EventListViewHolder(Context context, View view){
            super(view);
            this.context = context;
            heartEvent = (ImageView) itemView.findViewById(R.id.favorite);
            heartEvent.setTag(R.drawable.whiteheart);
            view.setOnClickListener(this);
            heartEvent.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (view.getId() == heartEvent.getId()) {
                if((Integer)heartEvent.getTag() == R.drawable.whiteheart) {
                    heartEvent.setImageResource(R.drawable.redheart);
                    heartEvent.setTag(R.drawable.redheart);
                }
                else{
                    heartEvent.setImageResource(R.drawable.whiteheart);
                    heartEvent.setTag(R.drawable.whiteheart);
                }
            } else {
                Intent intent = new Intent(context, EventDetailsActivity.class);
                context.startActivity(intent);
            }
        }
    }
}


