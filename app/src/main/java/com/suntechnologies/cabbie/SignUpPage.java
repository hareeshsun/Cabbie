package com.suntechnologies.cabbie;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.suntechnologies.cabbie.Adapters.DesignationAdapter;

import java.util.ArrayList;

/**
 * Created by hareeshs on 20-06-2018.
 */

public class SignUpPage extends AppCompatActivity {

    private ArrayList<String> designationList = new ArrayList<>();
    private ArrayList<String> reportingManagerList = new ArrayList<>();
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sign_up);
        mAuth = FirebaseAuth.getInstance();

        final EditText firstName = (EditText) findViewById(R.id.firstName);
        final EditText lastName = (EditText) findViewById(R.id.lastName);
        final EditText emailAddress = (EditText) findViewById(R.id.emailAddress);
        final EditText mobileNumber = (EditText) findViewById(R.id.mobileNumber);
        final EditText password = (EditText) findViewById(R.id.password);
        Spinner designation = (Spinner) findViewById(R.id.designationSpinner);
        Spinner reportTO = (Spinner) findViewById(R.id.reportTo);
        Button signUp = (Button) findViewById(R.id.signUp);

        designationList.add("Select Designation");
        designationList.add("Jr. Software Engg.");
        designationList.add("Software Engg.");
        designationList.add("Snr Software Engg.");
        designationList.add("Associates Manager");
        designationList.add("Team Lead");
        designationList.add("Project Manager");
        designationList.add("Manager");

        DesignationAdapter designationAdapter = new DesignationAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,designationList);
        designation.setAdapter(designationAdapter);

        reportingManagerList.add("Select Reporting Manager");
        reportingManagerList.add("Bala");
        reportingManagerList.add("Tahir");
        reportingManagerList.add("Vasu");
        reportingManagerList.add("Sunil");
        reportingManagerList.add("Syed");
        reportingManagerList.add("Jaydeep");

        DesignationAdapter reportingManagerAdapter = new DesignationAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,reportingManagerList);
        reportTO.setAdapter(reportingManagerAdapter);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.createUserWithEmailAndPassword(emailAddress.getText().toString().trim(), password.getText().toString().trim())
                        .addOnCompleteListener(SignUpPage.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignUpPage.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }

                            }
                        });
            }
        });
    }

    private boolean validation(EditText firstName,EditText lastName, EditText emailAddress,
                               EditText mobileNumber, String designation, String reportManager){
        if(firstName.getText().length() == 0){
            HelperMethods.showDialog("Please fill required fields","");
            return false;
        }
        return true;
    }
}
