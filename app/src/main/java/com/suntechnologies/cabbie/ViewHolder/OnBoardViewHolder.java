package com.suntechnologies.cabbie.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.suntechnologies.cabbie.R;

/**
 * Created by hareeshs on 09-07-2018.
 */

public class OnBoardViewHolder extends RecyclerView.ViewHolder {

    public TextView destination;
    public TextView riders;
    public TextView vehicleDetails;
    public TextView driverDetails;

    public OnBoardViewHolder(View itemView) {
        super(itemView);
        destination = (TextView) itemView.findViewById(R.id.destination);
        riders = (TextView) itemView.findViewById(R.id.riders);
        vehicleDetails = (TextView) itemView.findViewById(R.id.vehicleDetails);
        driverDetails = (TextView) itemView.findViewById(R.id.details);
    }
}
