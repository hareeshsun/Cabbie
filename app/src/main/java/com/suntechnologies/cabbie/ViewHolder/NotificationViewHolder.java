package com.suntechnologies.cabbie.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.suntechnologies.cabbie.R;

/**
 * Created by hareeshs on 09-07-2018.
 */

public class NotificationViewHolder extends RecyclerView.ViewHolder {
    public TextView employeeName;
    public TextView pickUpTime;
    public Button approve;
    public Button rejected;
    public LinearLayout notificaitonLayout;


    public NotificationViewHolder(View itemView) {
        super(itemView);

        employeeName =(TextView) itemView.findViewById(R.id.employeeName);
        pickUpTime =(TextView) itemView.findViewById(R.id.pickUpTime);
        approve =(Button) itemView.findViewById(R.id.managerApproval);
        rejected =(Button) itemView.findViewById(R.id.managerRejection);
        notificaitonLayout = (LinearLayout) itemView.findViewById(R.id.notificaitonLayout);
    }
}
