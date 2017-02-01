package com.example.android.wednesday.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.wednesday.R;

/**
 * Created by hp pc on 2/1/2017.
 */

public class MultipleImagesDineoutAdapter extends RecyclerView.Adapter<MultipleImagesDineoutAdapter.MyViewHolder>   {

    private final LayoutInflater inflater;
    //    List<CardModel> data = Collections.EMPTY_LIST;
    private Context context;


    public MultipleImagesDineoutAdapter(Context context){
        inflater = LayoutInflater.from(context);
//        this.data = dataSource;
        this.context = context;
    }

    @Override
    public MultipleImagesDineoutAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.multiple_images_dineout, parent, false);
        MultipleImagesDineoutAdapter.MyViewHolder myViewHolder = new MultipleImagesDineoutAdapter.MyViewHolder(context, view);
        return myViewHolder;    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(Context context, View view){
            super(view);

        }


    }
}
