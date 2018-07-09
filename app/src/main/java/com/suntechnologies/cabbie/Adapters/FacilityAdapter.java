package com.suntechnologies.cabbie.Adapters;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suntechnologies.cabbie.DataHolders.FacilityDataContainer;
import com.suntechnologies.cabbie.Fragments.ApproveCab;
import com.suntechnologies.cabbie.Fragments.FacilityFragment;
import com.suntechnologies.cabbie.Fragments.RejectCab;
import com.suntechnologies.cabbie.HelperMethods;
import com.suntechnologies.cabbie.MainActivity;
import com.suntechnologies.cabbie.R;
import com.suntechnologies.cabbie.SunCabbie;
import com.suntechnologies.cabbie.ViewHolder.FacilityViewHolder;

import java.util.ArrayList;

/**
 * Created by hareeshs on 07-07-2018.
 */

public class FacilityAdapter extends RecyclerView.Adapter<FacilityViewHolder> {

    ArrayList<FacilityDataContainer> facilityDataList;
    FacilityFragment fragment;

    public FacilityAdapter(ArrayList<FacilityDataContainer> list, FacilityFragment fragment) {
        this.facilityDataList = list;
        this.fragment = fragment;
    }


    @Override
    public FacilityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewGroup mainGroup = (ViewGroup) layoutInflater.inflate(R.layout.faclity_home_item_list, parent, false);
        return new FacilityViewHolder(mainGroup);
    }

    @Override
    public void onBindViewHolder(final FacilityViewHolder holder, int position) {
       final FacilityDataContainer cabRequestList = facilityDataList.get(position);
        holder.employeeName.setText(cabRequestList.employee_name);
        holder.employeeID.setText(cabRequestList.employee_id);
        holder.destination.setText(cabRequestList.employee_desitnation);
        String formattedManager = "by %s";
        holder.approvedBy.setText(String.format(formattedManager,cabRequestList.employee_manger_name));

        if(cabRequestList.manager_status != null && cabRequestList.manager_status.equalsIgnoreCase("true")){
            holder.stamp.setImageResource(R.drawable.ic_approved);
        }
        else {
            holder.stamp.setImageResource(R.drawable.ic_pending);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.facilityOptionslayout.getVisibility() == View.GONE){
                    holder.facilityOptionslayout.setVisibility(View.VISIBLE);
                }
                else
                    holder.facilityOptionslayout.setVisibility(View.GONE);
            }
        });

        holder.approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                HelperMethods.replaceFragment(fragment.getActivity(), MainActivity.frameLayout.getId(), new ApproveCab(cabRequestList.employee_desitnation,cabRequestList.uid,cabRequestList.employee_id), true);

            }
        });

        holder.rejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelperMethods.replaceFragment(fragment.getActivity(), MainActivity.frameLayout.getId(), new RejectCab(), true);
            }
        });

        holder.cabDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return facilityDataList != null ? facilityDataList.size():0;
    }
}
