package com.softexper.cardetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.softexper.cardetails.Data.POJO.data;

import java.util.List;

public class CarsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<data> carsList;
    private Context context;

    public CarsAdapter(List<data> carsList, Context context) {
        this.carsList = carsList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item, parent, false);
        return new CarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CarHolder carHolder = (CarHolder) holder;
        if (!carsList.get(position).getImageUrl().isEmpty()) {
            Glide.with(context).load(carsList.get(position).getImageUrl()).centerCrop().into(((CarHolder) holder).carImage);
        }
        carHolder.carBrand.setText(carsList.get(position).getBrand());
        boolean isUsed = Boolean.parseBoolean(carsList.get(position).getIsUsed());
        if (isUsed){
            carHolder.carCondition.setText("Used");
        }else {
            carHolder.carCondition.setText("New");
        }
        carHolder.carYear.setText(carsList.get(position).getConstractionYear());
    }

    @Override
    public int getItemCount() {
        return carsList.size();
    }

    public void setCarsList(List<data> carsList) {
        this.carsList = carsList;
    }

    private class CarHolder extends RecyclerView.ViewHolder {

        TextView carBrand, carYear, carCondition;
        ImageView carImage;

        CarHolder(View view) {
            super(view);
            carBrand = view.findViewById(R.id.car_brand);
            carYear = view.findViewById(R.id.car_year);
            carCondition = view.findViewById(R.id.car_condition);
            carImage = view.findViewById(R.id.car_image);
        }
    }
}
