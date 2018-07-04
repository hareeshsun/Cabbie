package com.suntechnologies.cabbie.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.suntechnologies.cabbie.Adapters.DesignationAdapter;
import com.suntechnologies.cabbie.DataHolders.UserData;
import com.suntechnologies.cabbie.HelperMethods;
import com.suntechnologies.cabbie.R;
import com.suntechnologies.cabbie.SunCabbie;

import java.util.ArrayList;

/**
 * Created by hareeshs on 02-07-2018.
 */

public class AccountDetails extends Fragment {

    private UserData userData;
    private String uid;
    private ArrayList<String> reportingManagerList = new ArrayList<>();
    private FirebaseDatabase database;

    public AccountDetails(UserData userData, String uid) {
        this.userData =  userData;
        this.uid = uid;
        System.out.println("Drunken Monkey : " + userData.reportingManager);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View accountDetailsView = inflater.inflate(R.layout.account_details,container,false);
        database = FirebaseDatabase.getInstance();
        database = FirebaseDatabase.getInstance();
        TextView name = (TextView) accountDetailsView.findViewById(R.id.employee_name);
        TextView id = (TextView) accountDetailsView.findViewById(R.id.employeeID);
        TextView email = (TextView) accountDetailsView.findViewById(R.id.employeeEmail);
        TextView mobileNumber = (TextView) accountDetailsView.findViewById(R.id.employeeNumber);
        TextView designation = (TextView) accountDetailsView.findViewById(R.id.designation);
        TextView address = (TextView) accountDetailsView.findViewById(R.id.completeAddress);
        final Spinner manager = (Spinner) accountDetailsView.findViewById(R.id.managerName);
        Button update = (Button) accountDetailsView.findViewById(R.id.update);

        if(userData != null) {
            name.setText(String.format("%s %s", userData.firstName, userData.lastName));
            id.setText(userData.employeeId);
            email.setText(userData.emailAddress);
            mobileNumber.setText(userData.phoneNumber);
            designation.setText(userData.designation);
            address.setText(String.format("%s, %s, nearby %s.",userData.address, userData.currentAddress, userData.landmark));

            reportingManagerList.add("Select Reporting Manager");
            reportingManagerList.add("Bala");
            reportingManagerList.add("Tahir");
            reportingManagerList.add("Vasu");
            reportingManagerList.add("Sunil");
            reportingManagerList.add("Syed");
            reportingManagerList.add("Jaydeep");
            DesignationAdapter reportingManagerAdapter = new DesignationAdapter(getContext(),android.R.layout.simple_spinner_item,reportingManagerList);
            manager.setAdapter(reportingManagerAdapter);

            for(int i=0; i<reportingManagerList.size();i++){
                if(reportingManagerList.get(i).equalsIgnoreCase(userData.reportingManager)){
                    manager.setSelection(i);
                }
            }

        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userData != null){
                    database.getReference().child("usersData").child(uid).child("reportingManager").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            dataSnapshot.getRef().setValue(manager.getSelectedItem().toString());
                            userData.reportingManager = manager.getSelectedItem().toString();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else {
                    HelperMethods.showDialog(getContext(),"Error","Something went wrong! Please try again later.");
                }
            }
        });

        return accountDetailsView;
    }
}
