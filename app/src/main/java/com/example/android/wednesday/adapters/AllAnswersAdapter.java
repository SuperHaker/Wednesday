package com.example.android.wednesday.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.wednesday.R;
import com.example.android.wednesday.models.AnswerModel;

import java.util.List;

/**
 * Created by hp pc on 3/24/2017.
 */


public class AllAnswersAdapter extends RecyclerView.Adapter<AllAnswersAdapter.AllAnswersViewHolder> {

    LayoutInflater inflater;
    Context context;
    List<AnswerModel> list;

    public AllAnswersAdapter(Context context, List<AnswerModel> list){
        inflater = LayoutInflater.from(context);
        this.context = context;
//        if(map != null) {
//            list = new ArrayList<AnswerModel>(map.values());
//        }

        this.list = list;


    }


    @Override
    public AllAnswersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.answer_layout, parent, false);
        return new AllAnswersViewHolder(view);

    }

    @Override
    public void onBindViewHolder(AllAnswersViewHolder holder, int position) {
        if(!list.isEmpty() || list!=null) {
            AnswerModel model = list.get(position);
            holder.ans.setText(model.answer);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

     class AllAnswersViewHolder extends RecyclerView.ViewHolder{

        TextView ans;

        public AllAnswersViewHolder(View itemView) {
            super(itemView);
            ans = (TextView) itemView.findViewById(R.id.answer);
        }
    }
}
