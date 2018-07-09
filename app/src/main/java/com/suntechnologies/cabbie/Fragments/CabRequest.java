package com.suntechnologies.cabbie.Fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.suntechnologies.cabbie.Adapters.SpinnerAdapter;
import com.suntechnologies.cabbie.HelperMethods;
import com.suntechnologies.cabbie.MainActivity;
import com.suntechnologies.cabbie.Model.Employee;
import com.suntechnologies.cabbie.Model.Status;
import com.suntechnologies.cabbie.Model.User;
import com.suntechnologies.cabbie.R;
import com.suntechnologies.cabbie.firebaseNotification.FirebaseNotification;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import dmax.dialog.SpotsDialog;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by hareeshs on 25-06-2018.
 */

public class CabRequest extends Fragment
{

    TextView employeeName, employeeEmail, employeeID, pickUpTime, employeeNumber, employeeAddress;
    Spinner managerName;
    Button cabRequest;
    private String USER_TOKEN_KEY = "USERTOKEN";
    private String USER_UID = "USERUID";
    private DatabaseReference mDatabase,admindata,managerData;
    private User userData;
    String reportTo;
    String pickupTime;
    String uid;
    Context mContext;
    String adminToken,managerToken;
    private ArrayList<String> reportingManagerList = new ArrayList<>();
    private Dialog loadingDialog;

    public CabRequest()
    {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View cabRequestView = inflater.inflate(R.layout.cab_request_layout, container, false);
        mContext = getActivity();
        loadingDialog = new SpotsDialog(mContext,"Logging...");
        employeeName = (TextView) cabRequestView.findViewById(R.id.employee_name);
        employeeID = (TextView) cabRequestView.findViewById(R.id.employeeID);
        employeeEmail = (TextView) cabRequestView.findViewById(R.id.employeeEmail);
        employeeNumber = (TextView) cabRequestView.findViewById(R.id.employeeNumber);
        employeeAddress = (TextView) cabRequestView.findViewById(R.id.employeeAddress);
        pickUpTime = (TextView) cabRequestView.findViewById(R.id.pickUpTime);
        managerName = (Spinner) cabRequestView.findViewById(R.id.managerName);
        cabRequest = (Button) cabRequestView.findViewById(R.id.btnRequest);
        loadingDialog.show();
        SharedPreferences preferences = getContext().getSharedPreferences(USER_TOKEN_KEY, MODE_PRIVATE);
        uid = preferences.getString(USER_UID, null);

        reportingManagerList.add("Select Reporting Manager");
        reportingManagerList.add("Bala");
        reportingManagerList.add("Tahir");
        reportingManagerList.add("Vasu");
        reportingManagerList.add("Sunil");
        reportingManagerList.add("Syed");
        reportingManagerList.add("Jaydeep");




        mDatabase = FirebaseDatabase.getInstance().getReference("usersData/" + uid);
        admindata = FirebaseDatabase.getInstance().getReference("admin/");


        admindata.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                adminToken = dataSnapshot.child("registrationToken").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });


        mDatabase.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                //Log.d("dss",dataSnapshot.getValue().toString());
                userData = dataSnapshot.getValue(User.class);
                if (userData != null)
                {

                    loadingDialog.dismiss();
                    employeeName.setText(userData.firstName + " " + userData.lastName);
                    employeeID.setText(userData.employeeId);
                    employeeEmail.setText(userData.emailAddress);
                    employeeNumber.setText(userData.phoneNumber);
                    employeeAddress.setText(userData.address + ", " + userData.currentAddress + ", " + userData.landmark);

                    for (int i = 0; i < reportingManagerList.size(); i++)
                    {
                        if (reportingManagerList.get(i).equalsIgnoreCase(userData.reportingManager))
                        {
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
        managerData = FirebaseDatabase.getInstance().getReference("managerData/");
        managerData.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

              /*  if(dataSnapshot.child(reportTo).child("registrationToken").getValue().toString() != null)
                {
                    managerToken = dataSnapshot.child(reportTo).child("registrationToken").getValue().toString();
                }

*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        SpinnerAdapter reportingManagerAdapter = new SpinnerAdapter(getActivity(), android.R.layout.simple_spinner_item, reportingManagerList);
        managerName.setAdapter(reportingManagerAdapter);

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        pickUpTime.setText(today.format("%k:%M"));
        pickupTime = today.format("%k:%M");
        pickUpTime.setPaintFlags(pickUpTime.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        pickUpTime.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
                    {
                        pickUpTime.setText(selectedHour + ":" + selectedMinute);
                        pickupTime = selectedHour + ":" + selectedMinute;
                        pickUpTime.setPaintFlags(pickUpTime.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                        ;
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

                String employeeName = userData.firstName + " " + userData.lastName;
                String destination = userData.address + " " + userData.currentAddress + " " + userData.lastName;
                ArrayList<Status> statuse = new ArrayList<Status>();
                statuse.add(new Status("false", "false"));
                String year = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
                String month = new SimpleDateFormat("MMMM", Locale.getDefault()).format(new Date());
                String day = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
                String date = new SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault()).format(new Date());
                writeNewUser(year, month, day, requestNmuber, uid, employeeName, userData.employeeId, destination, userData.reportingManager, pickupTime, date);
            }
        });
        return cabRequestView;
    }

    private void writeNewUser(String year, String month, String day, String requestNmuber, String uid, String employeeName, String emplyoeeId, String destination, String reportingManger, String pickupTime, String date)
    {
        mDatabase = FirebaseDatabase.getInstance().getReference("RequestCab/" + year + "/" + month + "/" + day + "/" + uid);
        Employee employee = new Employee(employeeName, emplyoeeId,uid, reportingManger, destination, pickupTime, "false", "false", date,userData.registrationToken);
        mDatabase.child(emplyoeeId).setValue(employee);


       // Log.d("sdfsf",managerToken);
      String notificationKey =  FirebaseNotification.not(String.valueOf(gen()), adminToken,getActivity());
    //   Log.d("sdfsf",notificationKey);
       // FirebaseNotification.addNotificationKey(notificationKey,getActivity(),"Cab request","Manger and faclity Team");
        if(notificationKey !=null && notificationKey.length()>0){
            notificationUser(notificationKey, "Cab Reqest ","I need for cab ",getActivity());
        }else{
          //  HelperMethods.showDialog(getActivity(),"Error","Something went wrong...");

        }


    }

    public int gen()
    {
        Random r = new Random(System.currentTimeMillis());
        return 10000 + r.nextInt(20000);
    }

 /*   String not(final String notificationKey, String registrationToken)
    {
    *//*Post data*//*
    final String test = "";

        final RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getActivity());
        String URL = "https://fcm.googleapis.com/fcm/notification";

        JSONObject data = new JSONObject();
        try
        {
            data.put("operation", "create");
            data.put("notification_key_name", notificationKey);
            data.put("registration_ids", new JSONArray(Arrays.asList(registrationToken)));
        } catch (Exception e)
        {

        }

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, URL,

                data,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {

                        String notification = response.optString("notification_key");
                        test = notification;
                        if (notification != null)
                        {
                            //  NotificationUser(notification);
                            // NotificationUser("APA91bEzZ2cSZMOiBOsol5_rCWnwqkvtjZjbo_x8FoC4Nmn6eLPybJjbbO1144zPRtzqBXqzRUmyRuJe5FUWKW5eWxPcvZU0N0ymYA5iQjeqLhdar90agCSuKfXdEG-1iLyHHPlFSPXT63bunMUBZzjzzOlFDMX5oQ");


                            //  FirebaseNotification.addNotificationKey(notification,mContext);

                        }

                        Log.d("test", String.valueOf(notification));
                        *//*Post data*//*

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        //   Handle Error
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("project_id", "805860432456");
                headers.put("Authorization", "key=AAAAu6EGWkg:APA91bEcnb_u_xmi_0KngqirNqOMCrCj94X71pUbgZg6cWG7cM5tdKxegVzapl8uUm8ULPVf9BVzqmFIaNjRwYR3nUyQxURgXrD2PqDu3apsL__eLJw-fl_lJEFah3hGoQtWM78JxJAyksO2Fr2KwZ0mJShk_K_AYQ");
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");

                return headers;
            }
        };
        requestQueue.add(postRequest);
        return test;

    }*/

    void notificationUser(String notification,String title,String body,Context mContext)
    {
     /*Post data*/
        final RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(mContext);
        String URL = "https://fcm.googleapis.com/fcm/send";

        JSONObject data = new JSONObject();
        try
        {
            data.put("to", notification);
            Map<String, String> jsonParams = new HashMap<String, String>();

            jsonParams.put("title", title);
            jsonParams.put("body", body);
            // data.put(new JSONObject(jsonParams));
            data.put("notification", new JSONObject(jsonParams));

        } catch (Exception e)
        {

        }


        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, URL,

                data,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {


                            String responseId = response.optString("success");
                        //  NotificationUser(notification);
                        if(Integer.parseInt(responseId)>0){
                            getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            HelperMethods.replaceFragment(getActivity(), MainActivity.frameLayout.getId(), new EmployeeFragment(managerToken), true);

                            Log.d("response", String.valueOf(responseId));

                        }


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        //   Handle Error
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "key=AAAAu6EGWkg:APA91bEcnb_u_xmi_0KngqirNqOMCrCj94X71pUbgZg6cWG7cM5tdKxegVzapl8uUm8ULPVf9BVzqmFIaNjRwYR3nUyQxURgXrD2PqDu3apsL__eLJw-fl_lJEFah3hGoQtWM78JxJAyksO2Fr2KwZ0mJShk_K_AYQ");
                headers.put("Content-Type", "application/json");

                return headers;
            }
        };
        requestQueue.add(postRequest);
    }

}