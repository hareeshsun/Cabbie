package com.suntechnologies.cabbie.Adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.suntechnologies.cabbie.Model.RideEmployeeDetail;
import com.suntechnologies.cabbie.R;
import com.suntechnologies.cabbie.ViewHolder.OnBoardViewHolder;

import java.util.ArrayList;

/**
 * Created by hareeshs on 09-07-2018.
 */

public class OnBoardingAdapter extends RecyclerView.Adapter<OnBoardViewHolder> {

    public ArrayList<RideEmployeeDetail> rideEmployeeDetails;

    public OnBoardingAdapter(ArrayList<RideEmployeeDetail> rideDetails) {
        this.rideEmployeeDetails = rideDetails;
    }

    @Override
    public OnBoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewGroup mainGroup = (ViewGroup) layoutInflater.inflate(R.layout.on_board_item, parent, false);
        return new OnBoardViewHolder(mainGroup);
    }

    @Override
    public void onBindViewHolder(OnBoardViewHolder holder, int position) {

        RideEmployeeDetail rideEmployeeDetail = rideEmployeeDetails.get(position);

        holder.destination.setText(rideEmployeeDetail.destination);
        holder.riders.setText("Riders : "+ rideEmployeeDetail.rideNumber);
        holder.driverDetails.setText(Html.fromHtml(rideEmployeeDetail.driverName + "\n"+ rideEmployeeDetail.driverNumber));
        holder.vehicleDetails.setText(Html.fromHtml(rideEmployeeDetail.vehicleModel + "\n"+ rideEmployeeDetail.vehicleNumber));

    }

    @Override
    public int getItemCount() {
        return rideEmployeeDetails != null? rideEmployeeDetails.size():0;
    }
}
