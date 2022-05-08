package com.projectmad.bordima;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PromotionRVAdapter extends RecyclerView.Adapter<PromotionRVAdapter.ViewHolder> {
    private ArrayList<PromotionRVModal> promotionRVModalArrayList;
    private Context context;
    int lastPos = -1;
    private PromotionClickInterface promotionClickInterface;

    public PromotionRVAdapter(ArrayList<PromotionRVModal> promotionRVModalArrayList, Context context, PromotionClickInterface promotionClickInterface) {
        this.promotionRVModalArrayList = promotionRVModalArrayList;
        this.context = context;
        this.promotionClickInterface = promotionClickInterface;
    }

    @NonNull
    @Override
    public PromotionRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.promotion_rv_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromotionRVAdapter.ViewHolder holder, int position) {
        PromotionRVModal promotionRVModal = promotionRVModalArrayList.get(position);
        holder.promotionNameTV.setText(promotionRVModal.getPromotionName());
        Picasso.get().load(promotionRVModal.getPromotionImgLink()).into(holder.promotionIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promotionClickInterface.onPromotionClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return promotionRVModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView promotionNameTV;
        private ImageView promotionIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            promotionNameTV = itemView.findViewById(R.id.idTVPromotionName);
            promotionIV = itemView.findViewById(R.id.idIVPromotion);
        }
    }
    public  interface PromotionClickInterface{
        void onPromotionClick(int position);
    }
}
