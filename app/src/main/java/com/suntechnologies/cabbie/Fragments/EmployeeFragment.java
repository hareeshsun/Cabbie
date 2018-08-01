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
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.suntechnologies.cabbie.Adapters.EmployeeAdapter;
import com.suntechnologies.cabbie.DataHolders.EmployeeViewHolder;
import com.suntechnologies.cabbie.Model.Employee;
import com.suntechnologies.cabbie.R;
import com.suntechnologies.cabbie.firebaseNotification.FirebaseNotification;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hareeshs on 25-06-2018.
 */

public class EmployeeFragment extends Fragment
{

    RecyclerView rv_cycle;
    EmployeeAdapter employeeAdapter;

    String requestId;
    String requestDate;
    String uid;
    private DatabaseReference mDatabase;
    Context context;
    String employeeID;
    ImageView sadSmiley;
    ArrayList<Employee> employeeCabRequestList = new ArrayList<>();

    public EmployeeFragment()
    {
    }

    public EmployeeFragment(String uid, String employeeID)
    {
        this.uid = uid;
        this.employeeID = employeeID;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.employee_home_page, container, false);
        rv_cycle = (RecyclerView) rootView.findViewById(R.id.rv_cycle);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv_cycle.setLayoutManager(llm);
        sadSmiley = (ImageView) rootView.findViewById(R.id.sadSmiley);
        reLoadData();

        return rootView;
    }

    public void reLoadData()
    {
        String year = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
        String month = new SimpleDateFormat("MMMM", Locale.getDefault()).format(new Date());
        String day = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());

        mDatabase = FirebaseDatabase.getInstance().getReference("RequestCab" + "/" + year + "/" + month + "/" + day + "/" + uid + "/" + employeeID);
        mDatabase.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                employeeCabRequestList.clear();
                Employee employee = dataSnapshot.getValue(Employee.class);
                if (employee != null)
                {
                    System.out.print("employee data" + employee);
                    employeeCabRequestList.add(new Employee(employee.employee_name, employee.employee_id, employee.employee_manger_name, employee.employee_desitnation,
                            employee.manager_status, employee.facility_status, employee.pickuptime, employee.date, employee.registrationToken, employee.uid, "", ""));

                    if (employeeCabRequestList.size() > 0)
                    {
                        sadSmiley.setVisibility(View.GONE);
                        if (null == employeeAdapter)
                        {
                            employeeAdapter = new EmployeeAdapter(employeeCabRequestList, EmployeeFragment.this);
                            rv_cycle.setAdapter(employeeAdapter);
                        } else
                        {
                            employeeAdapter.setCardSets(employeeCabRequestList);
                            employeeAdapter.notifyDataSetChanged();
                        }
                    }

                } else
                {
                    sadSmiley.setVisibility(View.VISIBLE);

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Log.d("Employee Status", String.valueOf(databaseError));
            }
        });

    }


}
