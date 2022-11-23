package com.projectmad.bordima;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.projectmad.bordima.Model.Service;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

    private static final String Tag = "RecyclerView";
    private Context context;
    private ArrayList<Service> serviceArrayList;

    public ServiceAdapter(Context context, ArrayList<Service> serviceArrayList) {
        this.context = context;
        this.serviceArrayList = serviceArrayList;
    }

    @NonNull
    @Override
    public ServiceAdapter.ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_card_view,parent,false);
        return new ServiceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapter.ServiceViewHolder holder, int position) {

        holder.ServiceName.setText(serviceArrayList.get(position).getSname());
        holder.ServiceLocation.setText(serviceArrayList.get(position).getLocation());
        holder.ServiceSprice.setText(serviceArrayList.get(position).getPrice());

        Glide.with(context)
                .load(serviceArrayList.get(position).getSimage())
                .into(holder.ServiceImage);

    }

    @Override
    public int getItemCount() {
        return serviceArrayList.size();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder{

        TextView ServiceName;
        TextView ServiceLocation;
        TextView ServiceSprice;
        ImageView ServiceImage;


        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            ServiceName = itemView.findViewById(R.id.serviceNameView);
            ServiceLocation = itemView.findViewById(R.id.serviceLocationView);
            ServiceSprice = itemView.findViewById(R.id.servicePriceView);
            ServiceImage = itemView.findViewById(R.id.serviceImage);

        }


    }


}
