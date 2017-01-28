package com.example.android.wednesday.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.wednesday.R;
import com.example.android.wednesday.activities.EventDetailsActivity;
import com.example.android.wednesday.models.EventListitemModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by hp pc on 1/29/2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {


    private final LayoutInflater inflater;
    private Context context;
    List<EventListitemModel> dataSource = Collections.EMPTY_LIST;
    public ListAdapter(Context context, List<EventListitemModel> dataSource){
        inflater = LayoutInflater.from(context);
        this.dataSource = dataSource;
        this.context = context;

    }

    @Override
    public ListAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.event_listitem_layout, parent, false);
        ListAdapter.ListViewHolder listViewHolder = new ListAdapter.ListViewHolder(context, view);
        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(ListAdapter.ListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Context context;
        public ListViewHolder(Context context, View view){
            super(view);
            this.context = context;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, EventDetailsActivity.class);
            context.startActivity(intent);
        }
    }
}
