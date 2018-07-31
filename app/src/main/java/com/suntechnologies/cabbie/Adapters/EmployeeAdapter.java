package com.suntechnologies.cabbie.Adapters;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suntechnologies.cabbie.DataHolders.EmployeeViewHolder;
import com.suntechnologies.cabbie.Fragments.ApprovedCabDetails;
import com.suntechnologies.cabbie.Fragments.EmployeeFragment;
import com.suntechnologies.cabbie.HelperMethods;
import com.suntechnologies.cabbie.MainActivity;
import com.suntechnologies.cabbie.Model.Employee;
import com.suntechnologies.cabbie.R;

import java.util.ArrayList;

/**
 * Created by mithulalr on 6/26/2018.
 */

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeViewHolder> {

    ArrayList<Employee> employees;
    EmployeeFragment employeeFragment;

    public EmployeeAdapter(ArrayList<Employee> employees, EmployeeFragment employeeFragment) {
        this.employees = employees;
        this.employeeFragment = employeeFragment;
    }

    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewGroup mainGroup = (ViewGroup) layoutInflater.inflate(R.layout.item, parent, false);
        return new EmployeeViewHolder(mainGroup);
    }

    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, int position) {
        final Employee employee = employees.get(position);
        holder.tvName.setText(employee.employee_name);
        String formattedString = "by %s";
        holder.mangerName.setText(String.format(formattedString,employee.employee_manger_name));
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


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(employee.facility_status.equalsIgnoreCase("true")){
                    employeeFragment.getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    HelperMethods.replaceFragment(employeeFragment.getActivity(), MainActivity.frameLayout.getId(), new ApprovedCabDetails(employee.employee_id), true);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return employees.size();
    }
    public void setCardSets(ArrayList<Employee> cardSets) {
        employees = cardSets;
    }
}
