package com.suntechnologies.cabbie.DataHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.suntechnologies.cabbie.R;

/**
 * Created by mithulalr on 6/26/2018.
 */

public class EmployeeViewHolder extends RecyclerView.ViewHolder {

public TextView tvName;
public TextView tvStatus;
        public TextView designation;
        public TextView mangerName;
        public TextView employeeID;

public EmployeeViewHolder(View itemView) {
        super(itemView);
        tvName = (TextView) itemView.findViewById(R.id.employeeName);
        tvStatus = (TextView) itemView.findViewById(R.id.status);
        designation = (TextView) itemView.findViewById(R.id.designation);
        mangerName = (TextView) itemView.findViewById(R.id.mangerName);
        employeeID = (TextView) itemView.findViewById(R.id.employeeID);


        }

        }
