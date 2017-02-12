package com.example.android.wednesday.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.android.wednesday.R;
import com.example.android.wednesday.adapters.MultipleImagesEventsAdapter;
import com.example.android.wednesday.fragments.BottomSheetFragment;

public class EventDetailsActivity extends AppCompatActivity {

    private MultipleImagesEventsAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView mapButton = (ImageView) findViewById(R.id.event_map);
        recyclerView = (RecyclerView) findViewById(R.id.images_for_an_event);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new MultipleImagesEventsAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);
        Button button = (Button) findViewById(R.id.book_now_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // custom dialog
//                final Dialog dialog = new Dialog(new ContextThemeWrapper(EventDetailsActivity.this, R.style.DialogSlideAnim));
//                dialog.setContentView(R.layout.popup);
//                dialog.setTitle("Title...");
//
//                // set the custom dialog components - text, image and button
//                TextView text = (TextView) dialog.findViewById(R.id.text);
//                text.setText("Android custom dialog example!");
//                ImageView image = (ImageView) dialog.findViewById(R.id.image);
//                image.setImageResource(R.mipmap.ic_launcher);
//                dialog.getWindow().setGravity(Gravity.BOTTOM);
//                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
//                // if button is clicked, close the custom dialog
//                dialogButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//
//                dialog.show();
                BottomSheetDialogFragment bottomSheetDialogFragment = new BottomSheetFragment();
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
//                AlertDialog.Builder builder = new AlertDialog.Builder(EventDetailsActivity.this);
//                LayoutInflater inflater = ( EventDetailsActivity.this).getLayoutInflater();
//                View dialogLayout = inflater.inflate(R.layout.popup,
//                        null);
//
//                final AlertDialog dialog = builder.create();
//                dialog.getWindow().setSoftInputMode(
//                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//                dialog.setView(dialogLayout, 0, 0, 0, 0);
//                dialog.setCanceledOnTouchOutside(true);
//                dialog.setCancelable(true);
//                WindowManager.LayoutParams wlmp = dialog.getWindow()
//                        .getAttributes();
//                wlmp.gravity = Gravity.BOTTOM;
//                builder.setView(dialogLayout);
//                dialog.show();
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri gmmIntentUri = Uri.parse("google.navigation:q=Rajouri+Garden, +New+Delhi");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {

                    startActivity(mapIntent);
                }
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
