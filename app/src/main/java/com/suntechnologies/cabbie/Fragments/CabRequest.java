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
    private String USER_TOKEN_KEY = "USERDATA";
    private String USER_UID = "USERUID";
    private String EMPLOYEE_ID = "EMPLOYEE ID";
    private DatabaseReference mDatabase;
    private DatabaseReference managerRef;
    private DatabaseReference admindata, managerData;
    private User userData;
    String reportTo;
    String pickupTime;
    String uid, employeeId;
    Context mContext;
    String adminToken, managerToken;
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
        loadingDialog = new SpotsDialog(mContext, "Logging...");
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
        employeeId = preferences.getString(EMPLOYEE_ID, null);

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
        managerData = FirebaseDatabase.getInstance().getReference("managerData/Vasu");
        managerData.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot != null)
                {
                    managerToken = (String) dataSnapshot.child("registrationToken").getValue();
                     Log.d("testof data",managerToken);
                }
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
                        pickupTime=   String.format("%02d:%02d", selectedHour, selectedMinute);
                        pickUpTime.setText(pickupTime);
                        pickUpTime.setPaintFlags(pickUpTime.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
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

    private void writeNewUser(String year, String month, String day, String requestNmuber, final String uid, String employeeName, final String emplyoeeId, String destination, final String reportingManger, String pickupTime, String date)
    {
        mDatabase = FirebaseDatabase.getInstance().getReference("RequestCab/" + year + "/" + month + "/" + day + "/" + uid);
        Employee employee = new Employee(employeeName, emplyoeeId, reportingManger, destination, "false", "false", pickupTime, date, userData.registrationToken, uid, "", "");
        mDatabase.child(emplyoeeId).setValue(employee);

        managerRef = FirebaseDatabase.getInstance().getReference("managerData");
        managerRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot managerObject : dataSnapshot.getChildren())
                {
                    if (managerObject.getKey() != null)
                        if (managerObject.getKey().equalsIgnoreCase(reportingManger))
                        {
                            managerRef.child(managerObject.getKey()).child("employeeUID").child(emplyoeeId).child("employeeUserId").setValue(uid);
                        }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        // Log.d("sdfsf",managerToken);
        ArrayList<String> ids = new ArrayList<>();
        ids.add(adminToken);
        ids.add(managerToken);

       // String notificationKey = FirebaseNotification.notificationRequest(String.valueOf(gen()), ids, getActivity());
        String notificationKey = "";
try
{
    notificationKey=  FirebaseNotification.addNotificationKey(String.valueOf(gen()), ids, getActivity());
}catch (Exception e){

}


        if (notificationKey != null && notificationKey.length() > 0)
        {
            loadingDialog.show();
            notificationUser(notificationKey, "New Cab Request ", "I need to drop at " + destination, getActivity());
        } else
        {
            HelperMethods.showDialog(getActivity(), " Sorry", "Problem connection to the server. Please try again later...");
        }
    }

    public int gen()
    {
        Random r = new Random(System.currentTimeMillis());
        return 10000 + r.nextInt(20000);
    }

    void notificationUser(String notification, String title, String body, Context mContext)
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
                        if (Integer.parseInt(responseId) > 0)
                        {
                            getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            EmployeeFragment employeeFragment = new EmployeeFragment(uid, employeeId);
                            HelperMethods.replaceFragment(getActivity(), MainActivity.frameLayout.getId(), employeeFragment, true);

                            Log.d("response", String.valueOf(responseId));
                            loadingDialog.dismiss();

                        } else
                        {
                            loadingDialog.dismiss();
                            HelperMethods.showDialog(getActivity(), " Sorry", "Problem connection to the server. Please try again later...");
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