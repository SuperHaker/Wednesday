package com.example.android.wednesday.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.wednesday.R;
import com.robertlevonyan.views.chip.Chip;

/**
 * Created by hp pc on 3/12/2017.
 */


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    private final LayoutInflater inflater;
    public Context context;
    public FeedAdapter(Context context){
        inflater = LayoutInflater.from(context);
        this.context = context;

    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.feed_layout, parent, false);

        return new FeedViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
//        holder.addExtraTextView();
//        holder.chip.setChipText("Tag");


    }
//
//    @Override
//    public void onViewRecycled(AskNowViewHolder holder) {
//        super.onViewRecycled(holder);
//        holder.removeExtraTextView();
//    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class FeedViewHolder extends RecyclerView.ViewHolder {
        View tagHolder;
        Context context;
        View v;
        Chip chip;
        LayoutInflater vi;

        public FeedViewHolder(View itemView, Context context){
            super(itemView);
            this.context = context;
//              vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        }

//        public void addExtraTextView() {
//            v = vi.inflate(R.layout.question_tag, null);
//            chip = (Chip) v.findViewById(chip);
//            chip.setChipText("Tag");
//            ((ViewGroup)tagHolder).addView(chip);
//            chip.setChipText("Tag");
//        }
//
//        public void removeExtraTextView() {
//            ((ViewGroup)tagHolder).removeView(chip);
//
//        }



    }


}
