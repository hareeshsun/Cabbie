package com.suntechnologies.cabbie.firebaseNotification;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
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
import com.suntechnologies.cabbie.Model.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import dmax.dialog.SpotsDialog;

/**
 * Created by mithulalr on 7/7/2018.
 */

public class FirebaseNotification
{
    private static String test = "";
    public static String notificationSingleRequest(final String notificationKey, String registrationToken, final Context context,final NotificationListener notificationListener)
    {

        final RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(context);
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
                        notificationListener.notificationKey(response);

                        if (notification != null)
                        {
                            test = notification;
                        }

                        Log.d("test", String.valueOf(notification));


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.d("Error",String.valueOf(error));
                        notificationListener.error(error.getMessage());
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

    public static String notificationRequest(final String notificationKey, ArrayList<String> registrationToken, final Context context,final NotificationListener notificationListener)
    {
        final RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String URL = "https://fcm.googleapis.com/fcm/notification";
        JSONObject data = new JSONObject();
        try
        {
            data.put("operation", "create");
            data.put("notification_key_name", notificationKey);
            data.put("registration_ids", new JSONArray(registrationToken));
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
                        notificationListener.notificationKey(response);
                        if (notification != null)
                        {

                            test = notification;


                        }

                        Log.d("test", String.valueOf(notification));



                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        //        loadingDialog.dismiss();
                        //   Handle Error
                        notificationListener.error(error.getMessage());
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
    public static  void notificationRequest1(final String notificationKey, ArrayList<String> registrationToken, final Context context, final NotificationListener notificationListener)
    {
        final RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String URL = "https://fcm.googleapis.com/fcm/notification";
        JSONObject data = new JSONObject();
        try
        {
            data.put("operation", "create");
            data.put("notification_key_name", notificationKey);
            data.put("registration_ids", new JSONArray(registrationToken));
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

                        notificationListener.notificationKey(response);
                        String notification = response.optString("notification_key");

                        if (notification != null)
                        {

                            test = notification;


                        }

                        Log.d("test", String.valueOf(notification));



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

    }

}
