package com.projectmad.bordima;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private static final String Tag = "RecyclerView";
    private Context mContext;
    private ArrayList<Promotions> promotionsList;

    public RecyclerAdapter(Context mContext, ArrayList<Promotions> promotionsList) {
        this.mContext = mContext;
        this.promotionsList = promotionsList;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.promotion_item, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //TextView
        holder.textView.setText(promotionsList.get(position).getName());
        //ImageView:Glide Library
        Glide.with(mContext)
                .load(promotionsList.get(position).getImageUrl())
                .into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        return promotionsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //widgets
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textViewy);
        }
    }

}
