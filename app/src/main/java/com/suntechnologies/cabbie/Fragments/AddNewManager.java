package com.suntechnologies.cabbie.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.suntechnologies.cabbie.Adapters.SpinnerAdapter;
import com.suntechnologies.cabbie.HelperMethods;
import com.suntechnologies.cabbie.LoginPage;
import com.suntechnologies.cabbie.Model.Manager;
import com.suntechnologies.cabbie.Model.ManagerDetail;
import com.suntechnologies.cabbie.Model.User;
import com.suntechnologies.cabbie.R;
import com.suntechnologies.cabbie.SignUpPage;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by mithulalr on 7/27/2018.
 */

public class AddNewManager extends Fragment {

    public EditText firstNameEditText;
    public EditText lastNameEditText;
    public EditText empIdEditText;
    public EditText emailIdEditText;
    public EditText otherDepartmentEditText;
    public Spinner departmentSpinner;
    public Button addNewManagerBtn;
    private ArrayList<String> departmentArrayList = new ArrayList<>();
    public String department;
    public String firstName;
    public String lastName;
    public String empId;
    public String emailId;
    public String otherDepartment;
    FirebaseDatabase database;
    boolean departmentFlag = false;
    public  AddNewManager(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_new_manager, container, false);
        database = FirebaseDatabase.getInstance();
        firstNameEditText = (EditText) view.findViewById(R.id.firstName);
        lastNameEditText = (EditText) view.findViewById(R.id.lastName);
        empIdEditText = (EditText) view.findViewById(R.id.empId);
        emailIdEditText = (EditText) view.findViewById(R.id.emailId);
        otherDepartmentEditText = (EditText) view.findViewById(R.id.otherDepartment);
        departmentSpinner = (Spinner) view.findViewById(R.id.departmentSpinner);
        addNewManagerBtn = (Button) view.findViewById(R.id.addNewManager);

        departmentArrayList.add("Select Department");
        departmentArrayList.add("Automation");
        departmentArrayList.add("Development");
        departmentArrayList.add("Testing");
        departmentArrayList.add("Game Developer");
        departmentArrayList.add("Game Tester");
        departmentArrayList.add("Other");
        SpinnerAdapter designationAdapter = new SpinnerAdapter(getActivity(),android.R.layout.simple_spinner_item,departmentArrayList);
        departmentSpinner.setAdapter(designationAdapter);
        departmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                if(departmentSpinner.getSelectedItem().toString().equalsIgnoreCase("Other")){
                   otherDepartmentEditText.setVisibility(View.VISIBLE);
                    departmentFlag = true;
               }else{
                        otherDepartmentEditText.setVisibility(View.GONE);
                        department = departmentSpinner.getSelectedItem().toString();
                    }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        if(departmentFlag)
        department = otherDepartmentEditText.getText().toString();


        addNewManagerBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(firstNameEditText.getText().toString().length()>0 && lastNameEditText.getText().toString().length() >0 && empIdEditText.getText().toString().length()>0 && emailIdEditText.getText().toString().length()>0 && department.length()>0)
                AddNewManager(firstNameEditText.getText().toString()+" "+lastNameEditText.getText().toString(),empIdEditText.getText().toString(),emailIdEditText.getText().toString(),department);
                else{
                    HelperMethods.showDialog(getActivity(),"Alert","cann't any textBox empty!");
                }
            }
        });




        return view;
    }
    private void AddNewManager(String name,String empId,String emailId,String department) {


         ArrayList<ManagerDetail>managerDetails = new ArrayList<>();
          ManagerDetail manager = new ManagerDetail(name,empId,emailId,department);
        managerDetails.add(manager);
            database.getReference("AddNewManger").child(emailId).setValue(manager);






    }
}