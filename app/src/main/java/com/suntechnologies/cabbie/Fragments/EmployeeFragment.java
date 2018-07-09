package com.suntechnologies.cabbie.Fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.suntechnologies.cabbie.Adapters.EmployeeAdapter;
import com.suntechnologies.cabbie.Model.Employee;
import com.suntechnologies.cabbie.Model.Status;
import com.suntechnologies.cabbie.Model.User;
import com.suntechnologies.cabbie.R;
import com.suntechnologies.cabbie.SunCabbie;
import com.suntechnologies.cabbie.firebaseNotification.FirebaseNotification;

import java.util.ArrayList;

/**
 * Created by hareeshs on 25-06-2018.
 */

public class EmployeeFragment extends Fragment {

    RecyclerView rv_cycle;
    EmployeeAdapter employeeAdapter;
    ArrayList<Employee> customerClosedActionArrayList = new ArrayList<>();
    String requestId;
    String requestDate;
    String uid;
    private DatabaseReference mDatabase;
    boolean employeeflag = false;
   Context context;
    public EmployeeFragment() {
    }

    public EmployeeFragment(String requestId, String requestDate, String uid) {
        this.requestId = requestId;
        this.requestDate = requestDate;
        this.uid = uid;

    }
    public EmployeeFragment( String uid) {
        employeeflag = true;
        this.uid = uid;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.employee_home_page, container, false);
        rv_cycle = (RecyclerView) rootView.findViewById(R.id.rv_cycle);


        if (customerClosedActionArrayList != null && customerClosedActionArrayList.size() > 0) {
            customerClosedActionArrayList.clear();
        }
        if (employeeflag) {
            FirebaseNotification.addNotificationKey(uid, context, "test","Eating fool");

          /*  mDatabase = FirebaseDatabase.getInstance().getReference("RequestCab/" + "/" + uid + "/" + requestId + "/" + requestDate);
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("emplyoeestatus", String.valueOf(dataSnapshot));
                    Employee employee = dataSnapshot.getValue(Employee.class);
                    customerClosedActionArrayList.add(new Employee(employee.employee_name, employee.employee_id, employee.employee_manger_name, employee.employee_desitnation,
                            employee.pickuptime, employee.manager_status, employee.facility_status, employee.date));
                    employeeAdapter = new EmployeeAdapter(customerClosedActionArrayList);
                    rv_cycle.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    rv_cycle.setHasFixedSize(true);
                    rv_cycle.addItemDecoration(new DividerItemDecoration(rv_cycle.getContext(), LinearLayoutManager.VERTICAL));
                    rv_cycle.setAdapter(employeeAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("emplyoeestatus", String.valueOf(databaseError));
                }
            });*/

        }
        return rootView;
    }

}
