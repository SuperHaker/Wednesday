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
import com.example.android.wednesday.activities.EventlistActivity;
import com.example.android.wednesday.models.CategoryModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by hp pc on 1/27/2017.
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.CategoryViewHolder> {


    private List<CategoryModel> dataSource = Collections.EMPTY_LIST;
    private final LayoutInflater inflater;
    private Context context;



    public GridAdapter(Context context, List<CategoryModel> dataSource){

        inflater = LayoutInflater.from(context);
        this.dataSource = dataSource;
        this.context = context;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.category_item, parent, false);
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(context, view);

        return categoryViewHolder;
    }



    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        CategoryModel currentCard = dataSource.get(position);
        holder.categoryName.setText(currentCard.categoryName);
        Glide.with(context).load(currentCard.categoryPhoto).into(holder.categoryPhoto);

    }


    @Override
    public int getItemCount() {
        return dataSource.size();
    }


    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView categoryName;
        private Context context;
        public ImageView categoryPhoto;



        public CategoryViewHolder(Context context, View view){
            super(view);
            this.context = context;
            categoryName = (TextView) itemView.findViewById(R.id.category_name);
            categoryPhoto = (ImageView) itemView.findViewById(R.id.category_image);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, "Clicked " + Integer.toString(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, EventlistActivity.class);
            context.startActivity(intent);
        }
    }


}
