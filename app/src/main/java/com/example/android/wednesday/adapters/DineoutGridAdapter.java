package com.example.android.wednesday.adapters;

/**
 * Created by hp pc on 1/30/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.wednesday.R;
import com.example.android.wednesday.activities.DineoutlistActivity;
import com.example.android.wednesday.models.CategoryModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by hp pc on 1/27/2017.
 */

public class DineoutGridAdapter extends RecyclerView.Adapter<DineoutGridAdapter.DineoutCategoryViewHolder> {

    private List<CategoryModel> dataSource = Collections.EMPTY_LIST;
    private final LayoutInflater inflater;
    private Context context;



    public DineoutGridAdapter(Context context, List<CategoryModel> dataSource){

        inflater = LayoutInflater.from(context);
        this.dataSource = dataSource;
        this.context = context;
    }

    @Override
    public DineoutCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.category_card_dineout, parent, false);
        DineoutCategoryViewHolder categoryViewHolder = new DineoutCategoryViewHolder(context, view);
        return categoryViewHolder;
    }



    @Override
    public void onBindViewHolder(DineoutCategoryViewHolder holder, int position) {
        CategoryModel currentCard = dataSource.get(position);
        holder.categoryName.setText(currentCard.categoryName + " " + Integer.toString(position));

    }


    @Override
    public int getItemCount() {
        return dataSource.size();
    }


    class DineoutCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView categoryName;
        private Context context;


        public DineoutCategoryViewHolder(Context context, View view){
            super(view);
            this.context = context;
            categoryName = (TextView) itemView.findViewById(R.id.category_name);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, "Clicked " + Integer.toString(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, DineoutlistActivity.class);
            context.startActivity(intent);
        }
    }


}
