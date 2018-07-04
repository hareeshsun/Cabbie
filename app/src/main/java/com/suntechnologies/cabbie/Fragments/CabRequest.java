package com.suntechnologies.cabbie.Fragments;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.suntechnologies.cabbie.Adapters.DesignationAdapter;
import com.suntechnologies.cabbie.HelperMethods;
import com.suntechnologies.cabbie.MainActivity;
import com.suntechnologies.cabbie.Model.Employee;
import com.suntechnologies.cabbie.Model.Status;
import com.suntechnologies.cabbie.Model.User;
import com.suntechnologies.cabbie.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by hareeshs on 25-06-2018.
 */

public class CabRequest extends Fragment {

     TextView employeeName,employeeEmail,employeeID, pickUpTime,employeeNumber,employeeAddress;
     Spinner managerName;
    Button cabRequest;
    private String USER_TOKEN_KEY = "USERTOKEN";
    private String USER_UID = "USERUID";
    private DatabaseReference mDatabase;
    private User userData;
    String reportTo;
  String pickupTime;
    String uid;


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
        cabRequest = (Button) cabRequestView.findViewById(R.id.btnRequest);

        SharedPreferences preferences = getContext().getSharedPreferences(USER_TOKEN_KEY,MODE_PRIVATE);
         uid =  preferences.getString(USER_UID, null);

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
                    employeeID.setText(userData.employeeId);
                    employeeEmail.setText(userData.emailAddress);
                    employeeNumber.setText(userData.phoneNumber);
                    employeeAddress.setText(userData.address + ", " + userData.currentAddress + ", " + userData.landmark);

                    for(int i=0; i<reportingManagerList.size();i++){
                        if(reportingManagerList.get(i).equalsIgnoreCase(userData.reportingManager)){
                            reportTo = userData.reportingManager;
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
        pickupTime =today.format("%k:%M");
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
                        pickupTime = selectedHour+":"+selectedMinute;
                        pickUpTime.setPaintFlags(pickUpTime.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);;
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        cabRequest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String requestNmuber = String.valueOf(gen());


                        String employeeName = userData.firstName + " " +userData.lastName;
                        String destination = userData.address + " "+ userData.currentAddress + " " +userData.lastName;
                       ArrayList<Status>statuse = new ArrayList<Status>();
                        statuse.add(new Status("false","false"));
                 String date = new SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault()).format(new Date());
                        writeNewUser(requestNmuber,uid,employeeName,userData.employeeId, destination,userData.reportingManager,pickupTime,date);


            }
        });
        return  cabRequestView;
    }

    private void writeNewUser(String requestNmuber,String uid, String employeeName, String emplyoeeId, String destination,String reportingManger,String pickupTime,String date) {
        mDatabase = FirebaseDatabase.getInstance().getReference("RequestCab/"+uid );
        Employee employee = new Employee(employeeName, emplyoeeId,reportingManger,destination,pickupTime, "false","false",date);
        mDatabase.child(requestNmuber).child(date).setValue(employee);
        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        HelperMethods.replaceFragment(getActivity(),MainActivity.frameLayout.getId(),new EmployeeFragment(requestNmuber,date,uid),true);
    }
    public int gen() {
        Random r = new Random( System.currentTimeMillis() );
        return 10000 + r.nextInt(20000);
    }
}