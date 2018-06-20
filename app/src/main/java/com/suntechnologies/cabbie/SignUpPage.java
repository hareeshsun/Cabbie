package com.suntechnologies.cabbie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.suntechnologies.cabbie.Adapters.DesignationAdapter;

import java.util.ArrayList;

/**
 * Created by hareeshs on 20-06-2018.
 */

public class SignUpPage extends AppCompatActivity {

    private ArrayList<String> designationList = new ArrayList<>();
    private ArrayList<String> reportingManagerList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sign_up);

        final EditText firstName = (EditText) findViewById(R.id.firstName);
        final EditText lastName = (EditText) findViewById(R.id.lastName);
        EditText emailAddress = (EditText) findViewById(R.id.emailAddress);
        EditText mobileNumber = (EditText) findViewById(R.id.mobileNumber);
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
                //validation(lastName,null,null,null,null,null);
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
