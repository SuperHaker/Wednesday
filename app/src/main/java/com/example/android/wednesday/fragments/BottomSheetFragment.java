package com.example.android.wednesday.fragments;

import android.app.Dialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.wednesday.R;

/**
 * Created by hp pc on 2/9/2017.
 */

public class BottomSheetFragment extends BottomSheetDialogFragment {


    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.popup, null);
        dialog.setContentView(contentView);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();


        Button increasePeople = (Button) contentView.findViewById(R.id.increase_people);
        final Button decreasePeople = (Button) contentView.findViewById(R.id.decrease_people);
        final TextView numberOfPeople = (TextView) contentView.findViewById(R.id.number_of_people);
        decreasePeople.setClickable(false);

        increasePeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int people = Integer.parseInt(numberOfPeople.getText().toString());
                people++;
                numberOfPeople.setText(Integer.toString(people));
                if(people == 2){
                    decreasePeople.setClickable(true);
                }

            }
        });

        decreasePeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int people = Integer.parseInt(numberOfPeople.getText().toString());
                people--;
                numberOfPeople.setText(Integer.toString(people));
                if (people == 1){
                    decreasePeople.setClickable(false);
                }
            }
        });



    }
}
