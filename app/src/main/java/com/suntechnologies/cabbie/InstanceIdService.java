package com.suntechnologies.cabbie;

/**
 * Created by mithulalr on 7/4/2018.
 */
import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class InstanceIdService extends FirebaseInstanceIdService {
    public InstanceIdService() {
        super();
    }

    SharedPreferences.Editor editor;
    private String REGISTRATION_KEY = "REGISTRATION_KEY";
    private String REGISTRATION_VALUE = "REGISTRATION_VALUE";
    SharedPreferences preferences;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();

        preferences = getSharedPreferences(REGISTRATION_KEY, Context.MODE_PRIVATE);
        editor =preferences.edit();
        editor.putString(REGISTRATION_VALUE,token);
        editor.apply();

        //sends this token to the server
        sendToServer(token);
    }

    private void sendToServer(String token) {

        try {
            URL url = new URL("https://www.whatsthatlambda.com/store");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoOutput(true);
            connection.setDoInput(true);

            connection.setRequestMethod("POST");

            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());

            dos.writeBytes("token=" + token);

            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Do whatever you want after the
                // token is successfully stored on the server
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}