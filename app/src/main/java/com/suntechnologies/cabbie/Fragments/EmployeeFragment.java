package com.suntechnologies.cabbie.Fragments;

import android.support.v4.app.Fragment;;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suntechnologies.cabbie.Adapters.EmployeeAdapter;
import com.suntechnologies.cabbie.Model.Employee;
import com.suntechnologies.cabbie.R;

import java.util.ArrayList;

/**
 * Created by hareeshs on 25-06-2018.
 */

public class EmployeeFragment extends Fragment {
    RecyclerView rv_cycle;
    EmployeeAdapter employeeAdapter;
    ArrayList<Employee>customerClosedActionArrayList = new ArrayList<>();

    public EmployeeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.employee_home_page,container, false);
        rv_cycle = (RecyclerView) rootView.findViewById(R.id.rv_cycle);
        customerClosedActionArrayList.add(new Employee("Hareesh","2305","Tahir","HBR Layout 43rd cross","true"));
        customerClosedActionArrayList.add(new Employee("mithu","2305","Tahir","HBR Layout 43rd cross","true"));
        customerClosedActionArrayList.add(new Employee("vedant","2305","Tahir","HBR Layout 43rd cross","true"));
        customerClosedActionArrayList.add(new Employee("yogesh","2305","Tahir","HBR Layout 43rd cross","true"));
        employeeAdapter = new EmployeeAdapter(customerClosedActionArrayList);
        rv_cycle.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_cycle.setHasFixedSize(true);
        rv_cycle.addItemDecoration(new DividerItemDecoration(rv_cycle.getContext(), LinearLayoutManager.VERTICAL));
        rv_cycle.setAdapter(employeeAdapter);
        return rootView;
    }
}
