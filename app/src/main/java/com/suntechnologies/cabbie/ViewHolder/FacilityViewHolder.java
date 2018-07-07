package com.suntechnologies.cabbie.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.suntechnologies.cabbie.R;

/**
 * Created by hareeshs on 07-07-2018.
 */

public class FacilityViewHolder extends RecyclerView.ViewHolder {

    public TextView employeeName;
    public TextView employeeID;
    public TextView manager;
    public TextView destination;
    public TextView approvedBy;
    public ImageView stamp;

    public FacilityViewHolder(View itemView) {
        super(itemView);

        employeeName = (TextView) itemView.findViewById(R.id.empName);
        employeeID = (TextView) itemView.findViewById(R.id.empID);
        manager = (TextView) itemView.findViewById(R.id.managerName);
        destination = (TextView) itemView.findViewById(R.id.designation);
        approvedBy = (TextView) itemView.findViewById(R.id.approvedBy);
        stamp =(ImageView) itemView.findViewById(R.id.stamp);

    }
}
