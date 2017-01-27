package com.example.android.wednesday.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.wednesday.R;
import com.example.android.wednesday.models.CategoryModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by hp pc on 1/27/2017.
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.CategoryViewHolder> {

    private List<CategoryModel> dataSource = Collections.EMPTY_LIST;
    private final LayoutInflater inflater;


    public GridAdapter(Context context, List<CategoryModel> dataSource){

        inflater = LayoutInflater.from(context);
        this.dataSource = dataSource;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.category_item, parent, false);
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        CategoryModel currentCard = dataSource.get(position);
        holder.categoryName.setText(currentCard.categoryName);

    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }


    class CategoryViewHolder extends RecyclerView.ViewHolder{
        TextView categoryName;


        public CategoryViewHolder(View view){
            super(view);

            categoryName = (TextView) itemView.findViewById(R.id.category_name);


        }
    }

}
