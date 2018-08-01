package com.suntechnologies.cabbie.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.suntechnologies.cabbie.Fragments.EmployeeFragment;
import com.suntechnologies.cabbie.Fragments.FacilityFragment;
import com.suntechnologies.cabbie.Fragments.Notification;
import com.suntechnologies.cabbie.HelperMethods;
import com.suntechnologies.cabbie.MainActivity;
import com.suntechnologies.cabbie.Model.Employee;
import com.suntechnologies.cabbie.R;
import com.suntechnologies.cabbie.ViewHolder.NotificationViewHolder;
import com.suntechnologies.cabbie.firebaseNotification.FirebaseNotification;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Timer;

import dmax.dialog.SpotsDialog;

/**
 * Created by hareeshs on 09-07-2018.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {

    private ArrayList<Employee> employeeArrayList;
    public Notification fragment;
    DatabaseReference reference;
    public String year, month, day;
    public String  employeeUid;
    public String  employeeId;
    NotificationAdapter notificationAdapter;
    Dialog loadingDialog;

    public NotificationAdapter(ArrayList<Employee> employeeArrayList, Notification fragment) {
        this.employeeArrayList = new ArrayList<>();
        this.employeeArrayList = employeeArrayList;
        this.fragment = fragment;
        this.year = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
        this.month = new SimpleDateFormat("MMMM", Locale.getDefault()).format(new Date());
        this.day = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());

    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewGroup mainGroup = (ViewGroup) layoutInflater.inflate(R.layout.notification_item, parent, false);
        loadingDialog = new SpotsDialog(fragment.getActivity(),"Logging...");
        return new NotificationViewHolder(mainGroup);
    }

    @Override
    public void onBindViewHolder(final NotificationViewHolder holder, final int position) {

        final Employee employee = employeeArrayList.get(position);
        if(employee.manager_status.equalsIgnoreCase("false")) {
            holder.notificaitonLayout.setVisibility(View.VISIBLE);
            holder.employeeName.setText(employee.employee_name);
            holder.pickUpTime.setText(employee.pickuptime);

            holder.approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                           final String notificationKey = FirebaseNotification.not(String.valueOf(gen()),employee.registrationToken,fragment.getActivity());
                    if(notificationKey.length()>0){
                        loadingDialog.setTitle("Approving...");
                        loadingDialog.show();
                        employeeArrayList.get(position).manager_status = "true";
                        employeeId = employee.employee_id;
                        employeeUid = employee.uid;

                        notificationUser(notificationKey,"Cab Approval","Manager is aaproval",fragment.getActivity(),true);
                    }
                    else {
                        Toast.makeText(fragment.getActivity(),"Something went wrong. Please try again!",Toast.LENGTH_SHORT).show();
                    }

                        }

            });

            holder.rejected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadingDialog.setTitle("Rejecting...");
                    loadingDialog.show();
                    String notificationKey = FirebaseNotification.not(String.valueOf(gen()),employee.registrationToken,fragment.getActivity());
                    if(notificationKey.length()>0){
                        employeeArrayList.get(position).manager_status = "true";
                        employeeId = employee.employee_id;
                        employeeUid = employee.uid;
                        notificationUser(notificationKey,"Cab Rejected","Manager is Rejected",fragment.getActivity(),false);
                    }
                    else {
                        HelperMethods.showDialog(fragment.getActivity(), " Sorry", "Problem connection to the server. Please try again later...");
                    }

                }
            });
        }
        else {
            holder.notificaitonLayout.setVisibility(View.VISIBLE);
        }
    }
    public int gen()
    {
        Random r = new Random(System.currentTimeMillis());
        return 10000 + r.nextInt(20000);
    }
    void notificationUser(final String notification, String title, String body, Context mContext,final boolean requestStatus)
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

                            Log.d("response", String.valueOf(responseId));
                            if (requestStatus){
                                reference = FirebaseDatabase.getInstance().getReference().child("RequestCab").child(year).child(month).child(day).child(employeeUid).child(employeeId);
                                reference.child("manager_status").setValue("true");
                                reference.child("managerDecision").setValue("true");


                                loadingDialog.dismiss();
                                fragment.loadData();
                            }else{
                                reference = FirebaseDatabase.getInstance().getReference().child("RequestCab").child(year).child(month).child(day).child(employeeUid).child(employeeId);
                                reference.child("manager_status").setValue("false");
                                reference.child("managerDecision").setValue("false");
                                loadingDialog.dismiss();
                                fragment.loadData();

                            }

/*                            fragment.getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            HelperMethods.replaceFragment(fragment.getActivity(), MainActivity.frameLayout.getId(), new Notification(), true);*/

                        }else{
                            loadingDialog.dismiss();
                            HelperMethods.showDialog(fragment.getActivity(), " Sorry", "Problem connection to the server. Please try again later...");
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

    @Override
    public int getItemCount() {
        return employeeArrayList != null ? employeeArrayList.size():0;
    }

    public String generateNotificationKey(String registrationToken){

        String notificationKey = FirebaseNotification.not(String.valueOf(gen()),registrationToken,fragment.getActivity());
        return notificationKey;
    }
}
