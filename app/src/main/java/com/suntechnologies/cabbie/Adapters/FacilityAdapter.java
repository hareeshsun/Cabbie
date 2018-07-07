package com.suntechnologies.cabbie.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.suntechnologies.cabbie.DataHolders.FacilityDataContainer;
import com.suntechnologies.cabbie.R;
import com.suntechnologies.cabbie.ViewHolder.FacilityViewHolder;

import java.util.ArrayList;

/**
 * Created by hareeshs on 07-07-2018.
 */

public class FacilityAdapter extends RecyclerView.Adapter<FacilityViewHolder> {

    ArrayList<FacilityDataContainer> facilityDataList;

    public FacilityAdapter(ArrayList<FacilityDataContainer> list) {
        this.facilityDataList = list;
    }

    @Override
    public FacilityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewGroup mainGroup = (ViewGroup) layoutInflater.inflate(R.layout.faclity_home_item_list, parent, false);
        return new FacilityViewHolder(mainGroup);
    }

    @Override
    public void onBindViewHolder(FacilityViewHolder holder, int position) {
        FacilityDataContainer cabRequestList = facilityDataList.get(position);
        holder.employeeName.setText(cabRequestList.employeeName);
        holder.employeeID.setText(cabRequestList.employeeID);
        holder.manager.setText(cabRequestList.employeeManager);
        holder.destination.setText(cabRequestList.destination);
        holder.approvedBy.setText(cabRequestList.managerStatus);

        if(cabRequestList.managerStatus.equalsIgnoreCase("true")){
            holder.stamp.setImageResource(R.drawable.ic_approved);
        }
        else {
            holder.stamp.setImageResource(R.drawable.ic_pending);
        }
    }

    @Override
    public int getItemCount() {
        return facilityDataList != null ? facilityDataList.size():0;
    }
}
