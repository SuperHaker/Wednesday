package com.example.android.wednesday.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.wednesday.R;
import com.example.android.wednesday.activities.ActivitylistActivity;
import com.example.android.wednesday.activities.DineoutlistActivity;
import com.example.android.wednesday.activities.EventlistActivity;
import com.example.android.wednesday.models.CategoryModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by hp pc on 1/27/2017.
 */

public class GridAdapter extends RecyclerView.Adapter {


    private List<CategoryModel> dataSource = Collections.EMPTY_LIST;
    private final LayoutInflater inflater;
    private Context context;
    int type;


    public GridAdapter(Context context, List<CategoryModel> dataSource, int layoutType){

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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(context, view, viewType);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
         CategoryViewHolder viewHolder = (CategoryViewHolder) holder;
            CategoryModel currentCard = dataSource.get(position);
            viewHolder.categoryName.setText(currentCard.categoryName);
            Glide.with(context).load(currentCard.categoryPhoto).into(viewHolder.categoryPhoto);

    }


    @Override
    public int getItemCount() {
        return dataSource.size();
    }


    class CategoryViewHolder extends RecyclerView.ViewHolder{
        TextView categoryName;
        private Context context;
        public ImageView categoryPhoto;



        public CategoryViewHolder(final Context context, View view, final int viewType){
            super(view);
            this.context = context;
            categoryName = (TextView) itemView.findViewById(R.id.category_name);
            categoryPhoto = (ImageView) itemView.findViewById(R.id.category_image);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(viewType == 1) {
                        Toast.makeText(context, "Clicked " + Integer.toString(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, EventlistActivity.class);
                        context.startActivity(intent);
                    }
                    else if(viewType == 2){
                        Toast.makeText(context, "Clicked " + Integer.toString(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, ActivitylistActivity.class);
                        context.startActivity(intent);
                    }
                    else if(viewType ==3){
                        Toast.makeText(context, "Clicked " + Integer.toString(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, DineoutlistActivity.class);
                        context.startActivity(intent);
                    }
                }
            });

        }
    }
}
