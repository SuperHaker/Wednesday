package com.example.android.wednesday.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.android.wednesday.R;
import com.example.android.wednesday.models.ImageUriModel;
import com.robertlevonyan.views.chip.Chip;

import java.util.Collections;
import java.util.List;

/**
 * Created by hp pc on 3/12/2017.
 */


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    private final LayoutInflater inflater;
    public Context context;
    List<ImageUriModel> list = Collections.EMPTY_LIST;
    public FeedAdapter(Context context, List<ImageUriModel> list){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;

    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.feed_layout, parent, false);

        return new FeedViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
            ImageUriModel currentCard = list.get(position);
                Glide.with(context)
                .load(currentCard.link)
                .into(holder.imageView);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class FeedViewHolder extends RecyclerView.ViewHolder {
        View tagHolder;
        Context context;
        View v;
        Chip chip;
        LayoutInflater vi;
        ImageView imageView;

        public FeedViewHolder(View itemView, Context context){
            super(itemView);
            this.context = context;
            imageView = (ImageView) itemView.findViewById(R.id.feed_image);


        }

    }


}
