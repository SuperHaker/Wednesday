package com.example.android.wednesday.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.wednesday.R;
import com.example.android.wednesday.activities.ActivityDetailsActivity;
import com.example.android.wednesday.models.ListitemModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by hp pc on 1/29/2017.
 */

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ActivityListViewHolder> {


    private final LayoutInflater inflater;
    private Context context;
    List<ListitemModel> dataSource = Collections.EMPTY_LIST;
    public ActivityListAdapter(Context context, List<ListitemModel> dataSource){
        inflater = LayoutInflater.from(context);
        this.dataSource = dataSource;
        this.context = context;

    }

    @Override
    public ActivityListAdapter.ActivityListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activities_listitem_layout, parent, false);
        ActivityListAdapter.ActivityListViewHolder listViewHolder = new ActivityListAdapter.ActivityListViewHolder(context, view);
        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(ActivityListAdapter.ActivityListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    class ActivityListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView heart;
        private Context context;
        public ActivityListViewHolder(Context context, View view){
            super(view);
            this.context = context;
            heart = (ImageView) itemView.findViewById(R.id.favorite_activity);
            heart.setTag(R.drawable.whiteheart);
            view.setOnClickListener(this);
            heart.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (view.getId() == heart.getId()) {
                if((Integer)heart.getTag() == R.drawable.whiteheart) {
                    heart.setImageResource(R.drawable.redheart);
                    heart.setTag(R.drawable.redheart);
                }
                else{
                    heart.setImageResource(R.drawable.whiteheart);
                    heart.setTag(R.drawable.whiteheart);
                }
            } else {
                Intent intent = new Intent(context, ActivityDetailsActivity.class);
                context.startActivity(intent);
            }
        }
    }
}
