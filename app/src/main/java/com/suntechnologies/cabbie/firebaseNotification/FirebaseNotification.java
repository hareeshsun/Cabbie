package com.suntechnologies.cabbie.firebaseNotification;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.suntechnologies.cabbie.Fragments.EmployeeFragment;
import com.suntechnologies.cabbie.HelperMethods;
import com.suntechnologies.cabbie.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import dmax.dialog.SpotsDialog;

/**
 * Created by mithulalr on 7/7/2018.
 */

public class FirebaseNotification
{
    private static  String test = "";
   // static Dialog loadingDialog;

    public  static  void  addNotificationKey(
            String registrationId, Context  Context,String title,String body) {
        /*Post data*/
        final RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(Context);
        String URL = "https://fcm.googleapis.com/fcm/send";

        JSONObject data = new JSONObject();
        try
        {
            data.put("to", registrationId);
            Map<String, String> jsonParams = new HashMap<String, String>();

            jsonParams.put("title", title);
            jsonParams.put("body", body);
            // data.put(new JSONObject(jsonParams));
            data.put("notification", new JSONObject(jsonParams));

        }catch (Exception e){

        }

        JsonObjectRequest postRequest = new JsonObjectRequest( Request.Method.POST, URL, data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //    String notification = response.optString("notification_key");
                        //  NotificationUser(notification);
                        Log.d("response",String.valueOf(response));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   Handle Error
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "key=AAAAu6EGWkg:APA91bEcnb_u_xmi_0KngqirNqOMCrCj94X71pUbgZg6cWG7cM5tdKxegVzapl8uUm8ULPVf9BVzqmFIaNjRwYR3nUyQxURgXrD2PqDu3apsL__eLJw-fl_lJEFah3hGoQtWM78JxJAyksO2Fr2KwZ0mJShk_K_AYQ");
             //   headers.put("Content-Type", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        requestQueue.add(postRequest);
    }

    public  static String not(final String notificationKey, String registrationToken, final Context context)
    {
    /*Post data*/
     //   loadingDialog = new SpotsDialog(context,"Logging...");
        final String notkey;
        final RequestQueue requestQueue;
        notkey = "";
        requestQueue = Volley.newRequestQueue(context);
     //   loadingDialog.show();
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

                        if (notification != null)
                        {
                   //         loadingDialog.dismiss();
                            test = notification;
                          //  addNotificationKey(notification,context);
                            //  NotificationUser(notification);
                            // NotificationUser("APA91bEzZ2cSZMOiBOsol5_rCWnwqkvtjZjbo_x8FoC4Nmn6eLPybJjbbO1144zPRtzqBXqzRUmyRuJe5FUWKW5eWxPcvZU0N0ymYA5iQjeqLhdar90agCSuKfXdEG-1iLyHHPlFSPXT63bunMUBZzjzzOlFDMX5oQ");
                           // getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                           // HelperMethods.replaceFragment(mContext, MainActivity.frameLayout.getId(), new EmployeeFragment(notification), true);

                            //  FirebaseNotification.addNotificationKey(notification,mContext);

                        }

                        Log.d("test", String.valueOf(notification));
                        /*Post data*/

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                //        loadingDialog.dismiss();
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

    }
}