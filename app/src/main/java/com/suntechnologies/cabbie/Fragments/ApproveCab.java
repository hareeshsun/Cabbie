package com.suntechnologies.cabbie.Fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.suntechnologies.cabbie.DataHolders.DriverDetails;
import com.suntechnologies.cabbie.HelperMethods;
import com.suntechnologies.cabbie.MainActivity;
import com.suntechnologies.cabbie.Model.RideEmployeeDetail;
import com.suntechnologies.cabbie.R;
import com.suntechnologies.cabbie.firebaseNotification.FirebaseNotification;
import com.suntechnologies.cabbie.firebaseNotification.NotificationListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import dmax.dialog.SpotsDialog;

/**
 * Created by hareeshs on 09-07-2018.
 */

public class ApproveCab extends Fragment
{

    private List<DriverDetails> driverlist;
    private ArrayList<DriverDetails> selectedDriver;
    EditText vehicleModel, destinationText, driverNumber, driverName;
    AutoCompleteTextView vehicleNumber;
    Spinner spinner;
    private Button provideCab;
    public String desination;
    String uid;
    String empId;
    private DatabaseReference mDatabase;
    private DatabaseReference msendcabDetailData;
    String selectedVehicleNumer;
    String vehicleModelNumber;
    String driverNa;
    String driverNo;
    String rideNumber;
    String rigstrationToken;
    private Dialog loadingDialog;
    TextView cabAvailableTime;
    String  pickupTime;
    public ApproveCab(String pickupTime, String desination, String uid, String empId, String rigstrationToken)
    {
        this.desination = desination;
        this.uid = uid;
        this.empId = empId;
        this.rigstrationToken = rigstrationToken;
        this.pickupTime = pickupTime;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View cabApprovalView = inflater.inflate(R.layout.approve_cab_details, container, false);
        loadingDialog = new SpotsDialog(getActivity(), "Cab is approval ...");
        vehicleNumber = (AutoCompleteTextView) cabApprovalView.findViewById(R.id.vehicleNumber);
        vehicleModel = (EditText) cabApprovalView.findViewById(R.id.vehicleModel);
        driverNumber = (EditText) cabApprovalView.findViewById(R.id.driverNumber);
        driverName = (EditText) cabApprovalView.findViewById(R.id.driverName);
        cabAvailableTime = (TextView) cabApprovalView.findViewById(R.id.cabAvailableTime);
        spinner = (Spinner) cabApprovalView.findViewById(R.id.riders);


        List<String> rideNumberArrayList = new ArrayList<String>();
        rideNumberArrayList.add("1");
        rideNumberArrayList.add("2");
        rideNumberArrayList.add("3");
        rideNumberArrayList.add("4");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, rideNumberArrayList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                rideNumber = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        destinationText = (EditText) cabApprovalView.findViewById(R.id.destination);
        provideCab = (Button) cabApprovalView.findViewById(R.id.provideCab);
        destinationText.setText(desination);

        driverlist = new ArrayList<>();
        driverlist.add(new DriverDetails("KA 03 BM 8055", "TATA ZEST - Blue", "S Hareesh", "9493707194"));
        driverlist.add(new DriverDetails("KA 51 BM 1234", "Maruthi Swift - White", "R Mithu Lal", "7010354978"));
        driverlist.add(new DriverDetails("KA 03 BM 7777", "TATA INDICA - Silver", "S Hareesh", "7382108907"));
        driverlist.add(new DriverDetails("KA 03 BM 9999", "TATA TIAGO - Orange", "R Mithu Lal", "9154241107"));


        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < driverlist.size(); i++)
        {
            list.add(driverlist.get(i).vehicleNumber);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, list);

        vehicleNumber.setThreshold(2);
        vehicleNumber.setAdapter(adapter);

        vehicleNumber.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3)
            {
                selectedVehicleNumer = (String) arg0.getAdapter().getItem(arg2);

                for (int i = 0; i < driverlist.size(); i++)
                {
                    if (driverlist.get(i).vehicleNumber.equalsIgnoreCase(selectedVehicleNumer))
                    {
                        vehicleModel.setText(driverlist.get(i).getVehicleModel());
                        vehicleModelNumber = driverlist.get(i).getVehicleModel();
                        driverName.setText(driverlist.get(i).driverName);
                        driverNa = driverlist.get(i).driverName;
                        driverNumber.setText(driverlist.get(i).driverNumber);
                        driverNo = driverlist.get(i).driverNumber;
                        desination = destinationText.getText().toString();
                        cabAvailableTime.setText(pickupTime);
                    }
                }
            }
        });

        cabAvailableTime.setOnClickListener(new View.OnClickListener()
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
                        cabAvailableTime.setText(pickupTime);
                        cabAvailableTime.setPaintFlags(cabAvailableTime.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                        ;
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        provideCab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (rideNumber != null  && selectedVehicleNumer != null )
                {
                    loadingDialog.show();
                    writeNewPost(uid, empId, selectedVehicleNumer, vehicleModelNumber, driverNa, driverNo, desination, rideNumber);
                }
            }
        });
        return cabApprovalView;
    }


    private void writeNewPost(String uid, String id, String selectedVehicleNumer, String vehicleModelNumber, final String driverNa, String driverNo, String desination, String rideNumber)
    {
        String year = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
        String month = new SimpleDateFormat("MMMM", Locale.getDefault()).format(new Date());
        String day = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());

        mDatabase = FirebaseDatabase.getInstance().getReference().child("RequestCab").child(year).child(month).child(day).child(uid).child(id);
        mDatabase.child("facility_status").setValue("true");
        mDatabase.child("facilityDecision").setValue("true");

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currentDateandTime = sdf.format(new Date());
        System.out.println("current time date" + currentDateandTime);
        msendcabDetailData = FirebaseDatabase.getInstance().getReference().child("CabDetails").child(day);
        RideEmployeeDetail rideEmployeeDetail = new RideEmployeeDetail(selectedVehicleNumer, driverNa, vehicleModelNumber, driverNo, rideNumber, desination, pickupTime);
        msendcabDetailData.child(id).setValue(rideEmployeeDetail);
      FirebaseNotification.notificationSingleRequest(String.valueOf(gen()), rigstrationToken, getActivity(), new NotificationListener()
      {
          @Override
          public void notificationKey(JSONObject notificationValue)
          {
                       String notificationKey = notificationValue.optString("notification_key") ;
              if (notificationKey.length() > 0)
              {

                  notificationUser(notificationKey, "Cab is Approval", "Cab will start at at " + pickupTime + " and will pickup by driver " + driverNa, getActivity());
                  loadingDialog.dismiss();
              } else
              {
                  loadingDialog.dismiss();
                  HelperMethods.showDialog(getActivity(), " Sorry", "Problem connection to the server. Please try again later...");
              }
          }

          @Override
          public void error(String error)
          {
              loadingDialog.dismiss();
              HelperMethods.showDialog(getActivity(), " Sorry", "Problem connection to the server. Please try again later...");
          }
      });

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
                            HelperMethods.replaceFragment(getActivity(), MainActivity.frameLayout.getId(), new FacilityFragment(), true);

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
