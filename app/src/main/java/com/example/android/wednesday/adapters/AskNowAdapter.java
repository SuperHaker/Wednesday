package com.example.android.wednesday.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.wednesday.R;
import com.example.android.wednesday.activities.AllAnswersActivity;
import com.example.android.wednesday.activities.WriteAnswerActivity;
import com.example.android.wednesday.models.AnswerModel;
import com.example.android.wednesday.models.AskQuestionModel;
import com.example.android.wednesday.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robertlevonyan.views.chip.Chip;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hp pc on 3/6/2017.
 */

public class AskNowAdapter extends RecyclerView.Adapter<AskNowAdapter.AskNowViewHolder> {

    private final LayoutInflater inflater;
    public Context context;
    List<AskQuestionModel> list = Collections.EMPTY_LIST;
    View ll;
    String key;
    DatabaseReference userReference;
    FirebaseUser  myUser;



    public AskNowAdapter(Context context, List<AskQuestionModel> list){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
        userReference = FirebaseDatabase.getInstance().getReference().child("users");
        myUser = FirebaseAuth.getInstance().getCurrentUser();
//        setHasStableIds(true);
    }

//    @Override
//    public long getItemId(int position) {
//        return position;
//    }

    @Override
    public AskNowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.ask_now_layout, parent, false);

//        tv.setBackgroundColor(context.getResources().getColor(R.color.orange500));

//        chip.changeBackgroundColor(R.color.orange500);
        return new AskNowViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(final AskNowViewHolder holder, int position) {
//        holder.addExtraTextView();
        holder.answererInfo.setVisibility(View.GONE);
//        holder.chip.setChipText("Tag");
        holder.tagHolder.removeAllViews();
        holder.myUser.setImageResource(R.drawable.user);
        AskQuestionModel currentCard = list.get(position);
//        Collection<AnswerModel> collections = currentCard.map.values();
//        List<AnswerModel> list = new ArrayList<>();
//        list.addAll(collections);
        if(!currentCard.map.isEmpty()) {
            Map.Entry<String, AnswerModel> entry = currentCard.map.entrySet().iterator().next();
            key = entry.getKey();
            final AnswerModel model = entry.getValue();

            userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user =  dataSnapshot.child(model.answererUid).getValue(User.class);
                    String myPhotoUrl = dataSnapshot.child(myUser.getUid()).child("userPhoto").getValue(String.class);
                    Glide.with(context)
                            .load(user.userPhoto)
                            .into(holder.userImage);
                    holder.userName.setText(user.username);

                    Glide.with(context)
                            .load(myPhotoUrl)
                            .into(holder.myUser);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            holder.answererInfo.setVisibility(View.VISIBLE);
            holder.topAnswer.setText(model.answer);
            holder.votes.setText(Long.toString(model.upvotes.number));
            holder.timestamp.setText(convertTime(model.time.get("timestamp").toString()));
        }
        for(int i = 0;i < currentCard.tags.size(); i++){
            String text = currentCard.tags.get(i);
            ll = holder.itemView.findViewById(R.id.tags_holder);
            if(!text.isEmpty())
                makeChip(text);
        }
        holder.question.setText(currentCard.question);
//        String s = ;
//        if(currentCard.answersList != null) {
//
//        }

    }

    public String convertTime(String time){
        Date date = new Date(Long.parseLong(time));
        SimpleDateFormat sdf;
        if((System.currentTimeMillis() - Long.parseLong(time))/3600000 >= 24){
            sdf = new SimpleDateFormat("dd MMM");
        }
//        else if((System.currentTimeMillis() - Long.parseLong(time))/(long)(3600000*24*365) >= 1){
//            sdf = new SimpleDateFormat("dd|MM|yy");
//        }
        else{
            sdf = new SimpleDateFormat("HH:mm");
        }
        // the format of your date
        sdf.setTimeZone(Calendar.getInstance().getTimeZone()); // give a timezone reference for formating (see comment at the bottom
        return sdf.format(date);
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    class AskNowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        LinearLayout tagHolder;
        Context context;
        TextView topAnswer, votes, timestamp, userName;
        View v;
        CircleImageView userImage, myUser;
        Chip chip;
        LayoutInflater vi;
        View answererInfo;
        TextView question;
        View upvoter, downvoter, seeMoreAnswers;

         public AskNowViewHolder(View itemView, Context context){
             super(itemView);
             this.context = context;
             tagHolder = (LinearLayout) itemView.findViewById(R.id.tags_holder);
//              vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             question  = (TextView) itemView.findViewById(R.id.question);
             topAnswer = (TextView) itemView.findViewById(R.id.top_answer);
             userImage = (CircleImageView) itemView.findViewById(R.id.user_image);
             userName = (TextView) itemView.findViewById(R.id.user_name);
             myUser = (CircleImageView) itemView.findViewById(R.id.my_user);
             v = itemView.findViewById(R.id.write_answer);
             v.setOnClickListener(this);
             answererInfo = itemView.findViewById(R.id.first_answer_info);
             timestamp = (TextView) itemView.findViewById(R.id.timestamp);
             upvoter = itemView.findViewById(R.id.upvote_answer);
             downvoter = itemView.findViewById(R.id.downvote_answer);
             votes = (TextView) itemView.findViewById(R.id.votes);
             upvoter.setOnClickListener(this);
             downvoter.setOnClickListener(this);
             seeMoreAnswers = itemView.findViewById(R.id.see_more_answers);
             seeMoreAnswers.setOnClickListener(this);


         }

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.write_answer){
                Intent intent = new Intent(context, WriteAnswerActivity.class);
                AskQuestionModel model = list.get(getAdapterPosition());
                intent.putExtra("question", question.getText().toString());
                intent.putExtra("userId", model.userId);
                intent.putExtra("quesId", model.quesId);
                context.startActivity(intent);
            }

            if(view == upvoter){

            }

            if(view == seeMoreAnswers){
                Intent intent = new Intent(context, AllAnswersActivity.class);
                intent.putExtra("question", question.getText().toString());
                AskQuestionModel model = list.get(getAdapterPosition());
                intent.putExtra("userId", model.userId);
                intent.putExtra("quesId", model.quesId);
                context.startActivity(intent);
            }
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
