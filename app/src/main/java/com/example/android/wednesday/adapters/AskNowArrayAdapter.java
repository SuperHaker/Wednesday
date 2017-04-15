package com.example.android.wednesday.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.wednesday.R;
import com.example.android.wednesday.models.AskQuestionModel;

import java.util.List;

/**
 * Created by hp pc on 4/13/2017.
 */

public class AskNowArrayAdapter extends ArrayAdapter<AskQuestionModel> {

    private static class ViewHolder{
        private TextView itemView;

    }

    List<AskQuestionModel> items;

    public AskNowArrayAdapter(Context context, int textViewResourceId, List<AskQuestionModel> items) {
        super(context, textViewResourceId, items);
        this.items = items;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.dropdown_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.itemView = (TextView) convertView.findViewById(R.id.textView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        AskQuestionModel item = items.get(position);
        if (item!= null) {
            // My layout has only one TextView
            // do whatever you want with your string and long
            viewHolder.itemView.setText(item.question);
        }

        return convertView;
    }
}