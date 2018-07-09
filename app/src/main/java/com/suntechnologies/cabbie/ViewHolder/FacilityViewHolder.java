package com.suntechnologies.cabbie.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.suntechnologies.cabbie.R;

/**
 * Created by hareeshs on 07-07-2018.
 */

public class FacilityViewHolder extends RecyclerView.ViewHolder {

    public TextView employeeName;
    public TextView employeeID;
    public TextView destination;
    public TextView approvedBy;
    public ImageView stamp;
    public LinearLayout facilityOptionslayout;
    public Button approved, rejected, cabDetails;

    public FacilityViewHolder(View itemView) {
        super(itemView);

        employeeName = (TextView) itemView.findViewById(R.id.empName);
        employeeID = (TextView) itemView.findViewById(R.id.empID);
        destination = (TextView) itemView.findViewById(R.id.designation);
        approvedBy = (TextView) itemView.findViewById(R.id.approvedBy);
        stamp =(ImageView) itemView.findViewById(R.id.stamp);
        facilityOptionslayout = (LinearLayout) itemView.findViewById(R.id.facilityOptions);
        approved = (Button) itemView.findViewById(R.id.approved);
        rejected = (Button) itemView.findViewById(R.id.reject);
        cabDetails = (Button) itemView.findViewById(R.id.cabDetails);

    }
}
