package com.suntechnologies.cabbie;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.suntechnologies.cabbie.Adapters.DesignationAdapter;
import com.suntechnologies.cabbie.Model.User;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

/**
 * Created by hareeshs on 20-06-2018.
 */

public class SignUpPage extends AppCompatActivity {
    Spinner designationSpinner,reportTO;
    EditText firstNameTxt, lastNameTxt, emailAddressTxt, mobileNumberTxt,addressTxt,currentAddressTxt,landmarkTxt, passwordTxt, employeeIdTxt,confirmPasswordTxt;
    Button signUp;
    TextView signIn;
    private Dialog loadingDialog;
    private ArrayList<String> designationList = new ArrayList<>();
    private ArrayList<String> reportingManagerList = new ArrayList<>();
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private DatabaseReference mDatabase;
    FirebaseDatabase database;
    String firstName, lastName, emailAddress, mobileNumber,address,currentAddress,landmark, password,confirmPassword, employeeId,designation,reportManagerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sign_up);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("usersData");

        firstNameTxt = (EditText) findViewById(R.id.firstName);
        lastNameTxt = (EditText) findViewById(R.id.lastName);
        emailAddressTxt = (EditText) findViewById(R.id.emailAddress);
        mobileNumberTxt = (EditText) findViewById(R.id.mobileNumber);
        employeeIdTxt = (EditText) findViewById(R.id.employeeID);
        passwordTxt = (EditText) findViewById(R.id.password);
        confirmPasswordTxt = (EditText) findViewById(R.id.confirmPassword);
        addressTxt = (EditText) findViewById(R.id.address);
        currentAddressTxt = (EditText) findViewById(R.id.currentAddress);
        landmarkTxt = (EditText) findViewById(R.id.landmark);
        signIn = (TextView) findViewById(R.id.signIn);

        designationSpinner = (Spinner) findViewById(R.id.designationSpinner);
        reportTO = (Spinner) findViewById(R.id.reportTo);
        signUp = (Button) findViewById(R.id.signUp);

        loadingDialog = new SpotsDialog(this,"Signing Up...");

        designationList.add("Select Designation");
        designationList.add("Jr. Software Engg.");
        designationList.add("Software Engg.");
        designationList.add("Snr Software Engg.");
        designationList.add("Associates Manager");
        designationList.add("Team Lead");
        designationList.add("Project Manager");
        designationList.add("Manager");

        DesignationAdapter designationAdapter = new DesignationAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,designationList);
        designationSpinner.setAdapter(designationAdapter);
        designationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                designation = designationSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        reportingManagerList.add("Select Reporting Manager");
        reportingManagerList.add("Bala");
        reportingManagerList.add("Tahir");
        reportingManagerList.add("Vasu");
        reportingManagerList.add("Sunil");
        reportingManagerList.add("Syed");
        reportingManagerList.add("Jaydeep");

        DesignationAdapter reportingManagerAdapter = new DesignationAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,reportingManagerList);
        reportTO.setAdapter(reportingManagerAdapter);

        reportTO.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                reportManagerId = reportTO.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firstName = firstNameTxt.getText().toString().trim();
                lastName = lastNameTxt.getText().toString().trim();
                emailAddress = emailAddressTxt.getText().toString().trim();
                employeeId = employeeIdTxt.getText().toString().trim();
                mobileNumber = mobileNumberTxt.getText().toString().trim();
                password = passwordTxt.getText().toString().trim();
                confirmPassword = confirmPasswordTxt.getText().toString().trim();
                address = addressTxt.getText().toString();
                currentAddress = currentAddressTxt.getText().toString();
                landmark = landmarkTxt.getText().toString();

                if ( firstName.length() > 0 && lastName.length()>0 && emailAddress.length()>0 && employeeId.length()>0 && mobileNumber.length()>0 && password.length()>0 && confirmPassword.length()>0 && address.length()>0
                        && currentAddress.length()>0 && landmark.length()>0 && !designation.equalsIgnoreCase("Select Designation") && !reportManagerId.equalsIgnoreCase("Select Reporting Manager")) {

                    if(!firstName.matches("[a-zA-Z]*")){

                        firstNameTxt.setError("Invalid name");
                    }else if(!lastName.matches("[a-zA-Z]*")){
                        lastNameTxt.setError("Invalid name");

                    }else if(!emailAddress.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")){
                        emailAddressTxt.setError("Invalid Email Address");

                    }
                    else if(mobileNumber.length()!=10){
                        mobileNumberTxt.setError("Invalid mobile number");
                    }else if(!password.equals(confirmPassword)){
                        Toast.makeText(SignUpPage.this,"password and confirmed password is not matching!",Toast.LENGTH_SHORT).show();
                    }else{
                        loadingDialog.show();
                        mAuth.createUserWithEmailAndPassword(emailAddress, password)
                                .addOnCompleteListener(SignUpPage.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(loadingDialog != null && loadingDialog.isShowing()){
                                            loadingDialog.dismiss();
                                        }
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d(TAG, "createUserWithEmail:success");
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            user.getEmail();
                                            user.getUid();

                                            writeNewUser(  user.getUid(),employeeId,user.getEmail(), firstName, lastName, mobileNumber,designation,reportManagerId,address,currentAddress,landmark);


                                        } else {
                                            // If sign in fails, display a message to the user.
                                                HelperMethods.showDialog(SignUpPage.this, "Error",task.getException().toString());
                                        }
                                    }
                                });
                    }

                }else{
                    HelperMethods.showDialog(SignUpPage.this,"Alert","Please fill all the filed!");
                }
            }
        });


        signIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SignUpPage.this, LoginPage.class);
                startActivity(intent);
            }
        });

    }

    private void writeNewUser(String uid,String employeeId,String emailId, String firstName ,String lastName,String phoneNumer,String designation ,String reportingManager,String address,String currentAddress,String landmark) {
        User user = new User(employeeId,firstName, lastName,phoneNumer,designation,reportingManager,emailId,address,currentAddress,landmark);
        mDatabase.child(uid).setValue(user);

        Intent intent = new Intent(SignUpPage.this, LoginPage.class);
        startActivity(intent);


    }


}
