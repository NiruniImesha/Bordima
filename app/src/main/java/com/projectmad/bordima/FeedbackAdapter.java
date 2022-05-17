package com.projectmad.bordima;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.projectmad.bordima.Model.Feedback;

import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {

    Context context;
    ArrayList<Feedback> FeedbackArrayList;

    //constructor
    public FeedbackAdapter(Context context, ArrayList<Feedback> feedbackArrayList) {
        this.context = context;
        FeedbackArrayList = feedbackArrayList;
    }

    @NonNull
    @Override
    public FeedbackAdapter.FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.activity_feedback_layout,parent,false);


        return new FeedbackViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackAdapter.FeedbackViewHolder holder, int position) {
        Feedback feedback = FeedbackArrayList.get(position);

        holder.review.setText(feedback.getReview());
        holder.date.setText(feedback.getData());
        holder.rating.setRating(feedback.getRating());

    }

    @Override
    public int getItemCount() {
        return FeedbackArrayList.size();
    }

    public static class FeedbackViewHolder extends RecyclerView.ViewHolder{

        TextView username, review, date;
        RatingBar rating;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);

            //username = (TextView) itemView.findViewById(R.id.usernameTv);
            review = (TextView) itemView.findViewById(R.id.reviewTv);
            date = (TextView) itemView.findViewById(R.id.dateTv);
            rating = (RatingBar) itemView.findViewById(R.id.ratingBar);
        }
    }
}
