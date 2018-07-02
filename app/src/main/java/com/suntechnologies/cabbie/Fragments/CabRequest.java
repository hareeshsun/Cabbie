package com.suntechnologies.cabbie.Fragments;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.suntechnologies.cabbie.Adapters.DesignationAdapter;
import com.suntechnologies.cabbie.Model.User;
import com.suntechnologies.cabbie.R;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by hareeshs on 25-06-2018.
 */

public class CabRequest extends Fragment {

     TextView employeeName,employeeEmail,employeeID, pickUpTime,employeeNumber,employeeAddress;
     Spinner managerName;
    private String USER_TOKEN_KEY = "USERTOKEN";
    private String USER_UID = "USERUID";
    private DatabaseReference mDatabase;
    private User userData;
    String reportTo;

    private ArrayList<String> reportingManagerList = new ArrayList<>();
    public CabRequest() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View cabRequestView = inflater.inflate(R.layout.cab_request_layout,container,false);

        employeeName = (TextView) cabRequestView.findViewById(R.id.employee_name);
        employeeID = (TextView) cabRequestView.findViewById(R.id.employeeID);
        employeeEmail = (TextView) cabRequestView.findViewById(R.id.employeeEmail);
        employeeNumber = (TextView) cabRequestView.findViewById(R.id.employeeNumber);
        employeeAddress = (TextView) cabRequestView.findViewById(R.id.employeeAddress);
        pickUpTime = (TextView) cabRequestView.findViewById(R.id.pickUpTime);
        managerName = (Spinner) cabRequestView.findViewById(R.id.managerName);

        SharedPreferences preferences = getContext().getSharedPreferences(USER_TOKEN_KEY,MODE_PRIVATE);
        String uid =  preferences.getString(USER_UID, null);

        reportingManagerList.add("Select Reporting Manager");
        reportingManagerList.add("Bala");
        reportingManagerList.add("Tahir");
        reportingManagerList.add("Vasu");
        reportingManagerList.add("Sunil");
        reportingManagerList.add("Syed");
        reportingManagerList.add("Jaydeep");

        mDatabase = FirebaseDatabase.getInstance().getReference("usersData/"+uid);
        mDatabase.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                //Log.d("dss",dataSnapshot.getValue().toString());
                userData = dataSnapshot.getValue(User.class);
                if (userData != null) {
                    employeeName.setText(userData.firstName + " " +userData.lastName);
                    employeeID.setText(userData.emplyoeeId);
                    employeeEmail.setText(userData.emailAddress);
                    employeeNumber.setText(userData.phoneNumber);
                    employeeAddress.setText(userData.address + ", " + userData.currentAddress + ", " + userData.landmark);

                    for(int i=0; i<reportingManagerList.size();i++){
                        if(reportingManagerList.get(i).equalsIgnoreCase(userData.reportingManger)){
                            reportTo = userData.reportingManger;
                            managerName.setSelection(i);
                        }
                    }
                }

                managerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {
                        reportTo = managerName.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent)
                    {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        DesignationAdapter reportingManagerAdapter = new DesignationAdapter(getActivity(),android.R.layout.simple_spinner_item,reportingManagerList);
        managerName.setAdapter(reportingManagerAdapter);

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        pickUpTime.setText(today.format("%k:%M"));
        pickUpTime.setPaintFlags(pickUpTime.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        pickUpTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        pickUpTime.setText(selectedHour + ":" + selectedMinute);
                        pickUpTime.setPaintFlags(pickUpTime.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);;
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        return  cabRequestView;
    }
}