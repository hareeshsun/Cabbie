package com.suntechnologies.cabbie.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.suntechnologies.cabbie.Fragments.Notification;
import com.suntechnologies.cabbie.Model.Employee;
import com.suntechnologies.cabbie.R;
import com.suntechnologies.cabbie.ViewHolder.NotificationViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hareeshs on 09-07-2018.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {

    private ArrayList<Employee> employeeArrayList;
    private Notification fragment;
    DatabaseReference reference;
    private String year, month, day;

    public NotificationAdapter(ArrayList<Employee> employeeArrayList, Notification fragment) {
        this.employeeArrayList = new ArrayList<>();
        this.employeeArrayList = employeeArrayList;
        this.fragment = fragment;
        this.year = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
        this.month = new SimpleDateFormat("MMMM", Locale.getDefault()).format(new Date());
        this.day = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewGroup mainGroup = (ViewGroup) layoutInflater.inflate(R.layout.notification_item, parent, false);
        return new NotificationViewHolder(mainGroup);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, final int position) {

        final Employee employee = employeeArrayList.get(position);
        if(employee.manager_status.equalsIgnoreCase("false")) {
            holder.notificaitonLayout.setVisibility(View.VISIBLE);
            holder.employeeName.setText(employee.employee_name);
            holder.pickUpTime.setText(employee.pickuptime);

            holder.approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reference = FirebaseDatabase.getInstance().getReference("RequestCab" + "/" + year + "/" + month + "/" + day + "/" + employee.uid + "/" + employee.employee_id);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            reference.child("manager_status").setValue("true");
                            employeeArrayList.get(position).manager_status = "true";
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });

            holder.rejected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        else {
            holder.notificaitonLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return employeeArrayList != null ? employeeArrayList.size():0;
    }
}
