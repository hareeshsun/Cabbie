package com.suntechnologies.cabbie.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
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

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by hareeshs on 02-07-2018.
 */

public class Notification extends Fragment {

    NotificationAdapter notificationAdapter;

    ArrayList<String> userId = new ArrayList<>();
    private ArrayList<Employee> employeesaArrayList = new ArrayList<>();

    String designation;
    RecyclerView recyclerView;

    public Notification(ArrayList<String> userId) {
        this.userId = userId;

    }
    public Notification() {


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getContext().getSharedPreferences("USERDATA", MODE_PRIVATE);
        designation = preferences.getString(String.valueOf(R.string.fetch_designation), null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View notificationView = inflater.inflate(R.layout.notification_layout, container, false);
        recyclerView = (RecyclerView) notificationView.findViewById(R.id.rvNotification);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        loadData();
        return notificationView;
    }

    public void loadData(){
        String year = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
        String month = new SimpleDateFormat("MMMM", Locale.getDefault()).format(new Date());
        String day = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
        DatabaseReference notificationData;
        employeesaArrayList.clear();
        if(designation.contains("Manager") || designation.contains("Admin") )
        {
            employeesaArrayList.clear();
          //  if(userId !=null &&userId.size()>0)
                /*for (int i = 0; i < userId.size(); i++)
                {*/
                    notificationData = FirebaseDatabase.getInstance().getReference("RequestCab" + "/" + year + "/" + month + "/" + day );
                    notificationData.addValueEventListener(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            if(dataSnapshot !=null) {
                            employeesaArrayList.clear();
                                for (DataSnapshot uniqueSnapshot : dataSnapshot.getChildren())
                                {

                            for (DataSnapshot employeeID : uniqueSnapshot.getChildren())
                            {
                                Employee employee;  employee = employeeID.getValue(Employee.class);
                                if (employee != null)
                                {
                                    if (employee.managerDecision.isEmpty() && employee.manager_status.equalsIgnoreCase("false"))
                                        employeesaArrayList.add(new Employee(employee.employee_name, employee.employee_id, employee.employee_manger_name,
                                                employee.employee_desitnation, employee.manager_status, employee.facility_status, employee.pickuptime,
                                                employee.date, employee.registrationToken, employee.uid, "", ""));
                                }

                            }}
                            if (employeesaArrayList.size() > 0)
                            {
                                if (null == notificationAdapter)
                                {
                                    notificationAdapter = new NotificationAdapter(employeesaArrayList, Notification.this);
                                    recyclerView.setAdapter(notificationAdapter);
                                } else
                                {
                                    notificationAdapter.setCardSets(employeesaArrayList);
                                    notificationAdapter.notifyDataSetChanged();

                                }
                            }
                        }

                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError)
                        {

                        }
                    });


                }
       // }
    }

}
