package com.suntechnologies.cabbie.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.suntechnologies.cabbie.Adapters.FacilityAdapter;
import com.suntechnologies.cabbie.Adapters.NotificationAdapter;
import com.suntechnologies.cabbie.HelperMethods;
import com.suntechnologies.cabbie.MainActivity;
import com.suntechnologies.cabbie.Model.Employee;
import com.suntechnologies.cabbie.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hareeshs on 02-07-2018.
 */

public class Notification extends Fragment {

    NotificationAdapter notificationAdapter;

    ArrayList<String> userId;
    private ArrayList<Employee> employeesaArrayList = new ArrayList<>();
    DatabaseReference notificationData;

    public Notification(ArrayList<String> userId) {
        this.userId = userId;

    }
    public Notification() {


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View notificationView = inflater.inflate(R.layout.notification_layout, container, false);
        final RecyclerView recyclerView = (RecyclerView) notificationView.findViewById(R.id.rvNotification);

        final String year = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
        final String month = new SimpleDateFormat("MMMM", Locale.getDefault()).format(new Date());
        final String day = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());

        for (int i = 0; i < userId.size(); i++) {
            notificationData = FirebaseDatabase.getInstance().getReference("RequestCab" + "/" + year + "/" + month + "/" + day + "/" + userId.get(i));
            notificationData.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot employeeID : dataSnapshot.getChildren()) {
                        Employee employee = employeeID.getValue(Employee.class);
                        if (employee != null)
                            employeesaArrayList.add(new Employee(employee.employee_name, employee.employee_id, employee.employee_manger_name,
                                    employee.employee_desitnation, employee.manager_status, employee.facility_status, employee.pickuptime,
                                    employee.date, employee.registrationToken, employee.uid));

                    }

                    notificationAdapter = new NotificationAdapter(employeesaArrayList, Notification.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(notificationAdapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        return notificationView;
    }

}
