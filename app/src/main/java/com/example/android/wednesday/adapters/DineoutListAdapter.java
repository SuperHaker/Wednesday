package com.example.android.wednesday.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.wednesday.R;
import com.example.android.wednesday.activities.DineoutDetailsActivity;
import com.example.android.wednesday.models.ListitemModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by hp pc on 1/29/2017.
 */

public class DineoutListAdapter extends RecyclerView.Adapter<DineoutListAdapter.ListViewHolder> {


    private final LayoutInflater inflater;
    private Context context;
    List<ListitemModel> dataSource = Collections.EMPTY_LIST;
    public DineoutListAdapter(Context context, List<ListitemModel> dataSource){
        inflater = LayoutInflater.from(context);
        this.dataSource = dataSource;
        this.context = context;

    }

    @Override
    public DineoutListAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.dineout_listitem_layout, parent, false);
        ListViewHolder listViewHolder = new ListViewHolder(context, view);
        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView heart;
        private Context context;
        public ListViewHolder(Context context, View view){
            super(view);
            this.context = context;
            heart = (ImageView) itemView.findViewById(R.id.favorite_dineout);
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
                Intent intent = new Intent(context, DineoutDetailsActivity.class);
                context.startActivity(intent);
            }
        }
    }
}
