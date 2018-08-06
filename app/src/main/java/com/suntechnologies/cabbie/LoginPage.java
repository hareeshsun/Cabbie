package com.suntechnologies.cabbie;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.suntechnologies.cabbie.firebaseNotification.FirebaseNotification;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import dmax.dialog.SpotsDialog;

public class LoginPage extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private String TAG = "LoginPaGE";
    SharedPreferences.Editor editor;
    private String USER_DATA = "USERDATA";
    private String USER_UID = "USERUID";
    SharedPreferences preferences;
    private Dialog loadingDialog;
    FirebaseDatabase database;
    private String USER_DESIGNATION_KEY = "USERDESIGNATION";
    private String USER_DESIGNATION_VALUE = "USERDESIGNATION";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_page);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        final EditText email = (EditText) findViewById(R.id.email);
        final EditText password = (EditText) findViewById(R.id.password);
        final Button login = (Button) findViewById(R.id.login);
        TextView signUp = (TextView) findViewById(R.id.signUpText);

        email.setText("vasubr@suntechnologies.com");
        password.setText("Reset123");

        loadingDialog = new SpotsDialog(this, "Logging...");
        preferences = getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        editor = preferences.edit();

        signUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), SignUpPage.class);
                startActivity(intent);
                finish();
            }
        });

        email.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view)
            {
                email.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.white_background_with_black_border, null));
            }
        });

        password.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view)
            {
                password.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.white_background_with_black_border, null));
            }
        });

        login.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view)
            {
                if (email.getText().toString().trim().length() > 0 && password.getText().toString().trim().length() > 0)
                {
                    if (!HelperMethods.isValidEmaillId(email.getText().toString().trim()))
                    {
                        email.setError("Email is notificationSingleRequest valid");
                    } else
                    {
                        loadingDialog.show();
                        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (loadingDialog != null && loadingDialog.isShowing())
                                {

                                }
                                if (task.isSuccessful())
                                {
                                    database.getReference("usersData/").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener()
                                    {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                        {
                                            String address = (String) dataSnapshot.child("address").getValue();
                                            String currentAddress = (String) dataSnapshot.child("currentAddress").getValue();
                                            String designation = (String) dataSnapshot.child("designation").getValue();
                                            String emailAddress = (String) dataSnapshot.child("emailAddress").getValue();
                                            String employeeId = (String) dataSnapshot.child("employeeId").getValue();
                                            String firstName = (String) dataSnapshot.child("firstName").getValue();
                                            String landmark = (String) dataSnapshot.child("landmark").getValue();
                                            String lastName = (String) dataSnapshot.child("lastName").getValue();
                                            String phoneNumber = (String) dataSnapshot.child("phoneNumber").getValue();
                                            String registrationToken = (String) dataSnapshot.child("registrationToken").getValue();
                                            String reportingManager = (String) dataSnapshot.child("reportingManager").getValue();
                                            String uid = (String) dataSnapshot.child("uid").getValue();

                                            editor.putString(USER_UID, mAuth.getCurrentUser().getUid());
                                            editor.putString("ADDRESS", address);
                                            editor.putString("CURRENT ADDRESS", currentAddress);
                                            editor.putString(String.valueOf(R.string.fetch_designation), designation);
                                            editor.putString("EMAIL ADDRESSS", emailAddress);
                                            editor.putString("EMPLOYEE ID", employeeId);
                                            editor.putString("FIRST NAME", firstName);
                                            editor.putString("LAST NAME", lastName);
                                            editor.putString("LANDMARK", landmark);
                                            editor.putString("PHONE NUMBER", phoneNumber);
                                            editor.putString("REGISTRATION TOKEN", registrationToken);
                                            editor.putString("REPORTING MANAGER", reportingManager);
                                            editor.putString("UID", uid);
                                            editor.apply();
                                            loadingDialog.dismiss();
                                            Intent intent = new Intent(LoginPage.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError)
                                        {

                                        }
                                    });

                                } else
                                {
                                    try
                                    {
                                        throw task.getException();
                                    } catch (FirebaseAuthWeakPasswordException e)
                                    {

                                        HelperMethods.showDialog(LoginPage.this, "Error", "Password is week");

                                    } catch (FirebaseAuthInvalidCredentialsException e)
                                    {
                                        HelperMethods.showDialog(LoginPage.this, "Error", "this credential is notificationSingleRequest available!");

                                    } catch (FirebaseAuthUserCollisionException e)
                                    {
                                        HelperMethods.showDialog(LoginPage.this, "Error", "This email is already is registered !");

                                    } catch (Exception e)
                                    {
                                        Log.e(TAG, e.getMessage());
                                    }
                                    // If sign in fails, display a message to the user.
                                    HelperMethods.showDialog(LoginPage.this, "signInWithEmail:failure", task.getException().toString());
                                }
                            }
                        });
                    }
                } else
                {
                    HelperMethods.showDialog(LoginPage.this, "Alert", "Email or Password filed cannot be empty! ");
                }
            }
        });

    }

}
