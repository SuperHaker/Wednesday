package com.example.android.wednesday.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.wednesday.R;
import com.example.android.wednesday.models.AskQuestionModel;
import com.robertlevonyan.views.chip.Chip;

import java.util.Collections;
import java.util.List;

/**
 * Created by hp pc on 3/6/2017.
 */

public class AskNowAdapter extends RecyclerView.Adapter<AskNowAdapter.AskNowViewHolder> {

    private final LayoutInflater inflater;
    public Context context;
    List<AskQuestionModel> list = Collections.EMPTY_LIST;
    View ll;

    public AskNowAdapter(Context context, List<AskQuestionModel> list){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }

    @Override
    public AskNowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.ask_now_layout, parent, false);
         ll = view.findViewById(R.id.tags_holder);

//        tv.setBackgroundColor(context.getResources().getColor(R.color.orange500));

//        chip.changeBackgroundColor(R.color.orange500);
        return new AskNowViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(AskNowViewHolder holder, int position) {
//        holder.addExtraTextView();
//        holder.chip.setChipText("Tag");

        AskQuestionModel currentCard = list.get(position);
        for(int i = 0;i < currentCard.tags.size(); i++){
            String text = currentCard.tags.get(i);
            makeChip(text);
        }
        holder.question.setText(currentCard.question);


    }

    @Override
    public void onViewRecycled(AskNowViewHolder holder) {
        super.onViewRecycled(holder);
        holder.tagHolder.removeAllViews();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class AskNowViewHolder extends RecyclerView.ViewHolder {
        LinearLayout tagHolder;
        Context context;
        View v;
        Chip chip;
        LayoutInflater vi;

        TextView question;

         public AskNowViewHolder(View itemView, Context context){
             super(itemView);
             this.context = context;
             tagHolder = (LinearLayout) itemView.findViewById(R.id.tags_holder);
//              vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             question  = (TextView) itemView.findViewById(R.id.question);


         }

//        public void addExtraTextView() {
//            v = vi.inflate(R.layout.question_tag, null);
//            chip = (Chip) v.findViewById(chip);
//            chip.setChipText("Tag");
//            ((ViewGroup)tagHolder).addView(chip);
//            chip.setChipText("Tag");
//        }
//
        public void removeExtraTextView() {
            ((ViewGroup)tagHolder).removeView(chip);

        }



     }

     private void makeChip(String text){
         TextView tv = new TextView(context);
         Drawable drawable = context.getResources().getDrawable(R.drawable.tags_background);
         tv.setLayoutParams(new ViewGroup.LayoutParams(
                 ViewGroup.LayoutParams.WRAP_CONTENT,
                 ViewGroup.LayoutParams.WRAP_CONTENT));

//        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View v = vi.inflate(R.layout.question_tag, null);
//        Chip chip = (Chip) v.findViewById(R.id.chip);
//        chip.setChipText("Tag");
         ((ViewGroup) ll).addView(tv);
         tv.setText(text);
         tv.setTextColor(context.getResources().getColor(R.color.colorPrimary));
         tv.setBackground(drawable);
         ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tv.getLayoutParams();

         layoutParams.setMargins(8,5,5,8);
     }


}
