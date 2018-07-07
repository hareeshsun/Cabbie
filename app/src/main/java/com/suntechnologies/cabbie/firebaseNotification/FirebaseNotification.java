package com.suntechnologies.cabbie.firebaseNotification;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

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

/**
 * Created by mithulalr on 7/7/2018.
 */

public class FirebaseNotification
{
    public  static  void  addNotificationKey(
            String registrationId, Context  Context) {
        /*Post data*/
        final RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(Context);
        String URL = "https://fcm.googleapis.com/fcm/send";

        JSONObject data = new JSONObject();
        try
        {
            data.put("to", registrationId);
            Map<String, String> jsonParams = new HashMap<String, String>();

            jsonParams.put("title", "RequestFormanagerCab");
            jsonParams.put("body", "Managerisaaprval ");
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
}