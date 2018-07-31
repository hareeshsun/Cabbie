package com.suntechnologies.cabbie.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.suntechnologies.cabbie.Adapters.SpinnerAdapter;
import com.suntechnologies.cabbie.HelperMethods;
import com.suntechnologies.cabbie.MainActivity;
import com.suntechnologies.cabbie.R;
import com.suntechnologies.cabbie.firebaseNotification.FirebaseNotification;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import dmax.dialog.SpotsDialog;

/**
 * Created by hareeshs on 09-07-2018.
 */

public class RejectCab extends Fragment
{

    ArrayList<String> rejectedReason;
    String rejectedReasonValue;
    SpinnerAdapter rejectedReasonAdapter;
    Spinner rejectedSpinner;
    String registrationToken;
    String uid;
    String id;
    private Dialog loadingDialog;
    private DatabaseReference mDatabase;

    public RejectCab(String registrationToken, String uid, String id)
    {
        this.registrationToken = registrationToken;
        this.uid = uid;
        this.id = id;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rejectReasonView = inflater.inflate(R.layout.reject_cab_request, container, false);

        rejectedSpinner = (Spinner) rejectReasonView.findViewById(R.id.rejectReason);
        final EditText rejectOptional = (EditText) rejectReasonView.findViewById(R.id.rejectOptional);
        Button btnReject = (Button) rejectReasonView.findViewById(R.id.btnReject);
        loadingDialog = new SpotsDialog(getActivity(), "Cab is rejected ...");
        rejectedReason = new ArrayList<>();
        rejectedReason.add("Cabs are not available");
        rejectedReason.add("Book OLA or UBER");
        rejectedReason.add("Drivers are busy");
        rejectedReason.add("Cabs are reserved for other people");
        rejectedReason.add("Permissions Denied");
        rejectedReason.add("Manager Rejected");
        rejectedReason.add("Cabs are not provided at requested time");
        rejectedReasonAdapter = new SpinnerAdapter(getActivity(), android.R.layout.simple_spinner_item, rejectedReason);
        rejectedSpinner.setAdapter(rejectedReasonAdapter);
        rejectedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                rejectedReasonValue = rejectedSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        btnReject.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                loadingDialog.show();
                String notificationUniqueKey = FirebaseNotification.not(String.valueOf(gen()), registrationToken, getActivity());
                if (notificationUniqueKey.length() > 0)
                {
                    loadingDialog.dismiss();
                    String year = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
                    String month = new SimpleDateFormat("MMMM", Locale.getDefault()).format(new Date());
                    String day = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("RequestCab").child(year).child(month).child(day).child(uid).child(id);
                    mDatabase.child("facility_status").setValue("true");
                    mDatabase.child("facilityDecision").setValue("false");
                    notificationUser(notificationUniqueKey, "Cab is Rejected", rejectedReasonValue + " " + rejectOptional.getText().toString(), getActivity());
                } else
                {
                    loadingDialog.dismiss();
                    HelperMethods.showDialog(getActivity(), "Error", "FCM server is down please try again later...");
                }
            }
        });

        return rejectReasonView;
    }

    public int gen()
    {
        Random r = new Random(System.currentTimeMillis());
        return 10000 + r.nextInt(20000);
    }

    void notificationUser(String notification, String title, String body, Context mContext)
    {
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
