package com.suntechnologies.cabbie.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.suntechnologies.cabbie.DataHolders.EmployeeViewHolder;
import com.suntechnologies.cabbie.Model.Employee;
import com.suntechnologies.cabbie.R;

import java.util.ArrayList;

/**
 * Created by mithulalr on 6/26/2018.
 */

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeViewHolder> {

    ArrayList<Employee> employees;

    public EmployeeAdapter(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewGroup mainGroup = (ViewGroup) layoutInflater.inflate(R.layout.item, parent, false);
        return new EmployeeViewHolder(mainGroup);
    }

    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, int position) {
        Employee employee = employees.get(position);
        holder.tvName.setText(employee.employee_name);
        holder.mangerName.setText(employee.employee_manger_name);
        holder.designation.setText(employee.employee_desitnation);
        holder.employeeID.setText(employee.employee_id);

        if (Boolean.parseBoolean(employee.manager_status) && Boolean.parseBoolean(employee.facility_status)){
            holder.imageView.setBackgroundResource(R.drawable.ic_approved);
            holder.imageView.setImageResource(R.drawable.ic_approved);
        }

        else{
            holder.imageView.setBackgroundResource(R.drawable.ic_pending);
            holder.imageView.setImageResource(R.drawable.ic_pending);
        }

    }


    @Override
    public int getItemCount() {
        return (null != employees ? employees.size() : 0);
    }
}
