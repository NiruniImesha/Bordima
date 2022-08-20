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

public class BoardingAdapter extends RecyclerView.Adapter<BoardingAdapter.BoardingViewHolder> {

    private static final String Tag = "RecyclerView";
    private Context context;
    private ArrayList<Boarding> boardingArrayList;

    public BoardingAdapter(Context context, ArrayList<Boarding> boardingArrayList) {
        this.context = context;
        this.boardingArrayList = boardingArrayList;
    }

    @NonNull
    @Override
    public BoardingAdapter.BoardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.boarding_card_view,parent,false);
        return new BoardingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardingViewHolder holder, int position) {
        holder.BoardingTitle.setText(boardingArrayList.get(position).getBname());
        holder.BoardingLocation.setText(boardingArrayList.get(position).getLocation());
        holder.BoardingPrice.setText(boardingArrayList.get(position).getBprice());

        Glide.with(context)
                .load(boardingArrayList.get(position).getBimage())
                .into(holder.BoardingImage);
    }

    @Override
    public int getItemCount() {
        return boardingArrayList.size();
    }

    public class BoardingViewHolder extends RecyclerView.ViewHolder{

        TextView BoardingTitle;
        TextView BoardingLocation;
        TextView BoardingPrice;
        ImageView BoardingImage;

        public BoardingViewHolder(@NonNull View itemView) {
            super(itemView);
            BoardingTitle = itemView.findViewById(R.id.boardingTitleView);
            BoardingLocation = itemView.findViewById(R.id.boardingLocationView);
            BoardingPrice = itemView.findViewById(R.id.boardingPriceView);
            BoardingImage = itemView.findViewById(R.id.boardingImage);
        }
    }
}
