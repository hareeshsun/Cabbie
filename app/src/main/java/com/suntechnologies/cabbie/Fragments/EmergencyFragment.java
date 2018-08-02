package com.suntechnologies.cabbie.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.suntechnologies.cabbie.DataHolders.EmergencyData;
import com.suntechnologies.cabbie.R;

/**
 * Created by hareeshs on 02-07-2018.
 */

public class EmergencyFragment extends Fragment {

    private DatabaseReference reference;
    private SharedPreferences sharedPreferences;
    EmergencyData emergencyData;

    private String USER_TOKEN_KEY = "USERDATA";
    private String USER_UID = "USERUID";
    private String userID;
    private TextView emergencyName, emergencyRelation, emergencyNumber, companyNumber;
    private CardView employeeContact;
    private Button addNew;

    public EmergencyFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.emergency_layout,container,false);

        addNew = (Button) rootView.findViewById(R.id.buttonAdd);
        employeeContact = (CardView) rootView.findViewById(R.id.employeeEmergencyContact);
        emergencyName = (TextView) rootView.findViewById(R.id.emergencyName);
        emergencyRelation = (TextView) rootView.findViewById(R.id.relation);
        emergencyNumber = (TextView) rootView.findViewById(R.id.mobileNumber);
        companyNumber = (TextView) rootView.findViewById(R.id.companyNumber);

        sharedPreferences = getActivity().getSharedPreferences(USER_TOKEN_KEY, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(USER_UID,null);
        reference = FirebaseDatabase.getInstance().getReference("usersData/" + userID+ "/emergencyDetails");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                emergencyData = dataSnapshot.getValue(EmergencyData.class);
                if(emergencyData != null){
                    addNew.setText("Update");
                    employeeContact.setVisibility(View.VISIBLE);
                    emergencyName.setText(emergencyData.name);
                    emergencyRelation.setText(emergencyData.relation);
                    emergencyNumber.setText(emergencyData.number);
                }
                else {
                    addNew.setText("Add");
                    employeeContact.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final String COMPANY_NUMBER = "08025437587";
        companyNumber.setText(COMPANY_NUMBER);
        companyNumber.setPaintFlags(companyNumber.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        emergencyNumber.setPaintFlags(emergencyNumber.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        companyNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmergencyCall(COMPANY_NUMBER);
            }
        });

        emergencyNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emergencyNumber.getText().toString().length() == 10)
                EmergencyCall(emergencyNumber.getText().toString());
            }
        });

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = getActivity();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                AddNewEmergencyContactDialog contactDialog = new AddNewEmergencyContactDialog(emergencyName.getText().toString(),
                                                                                        emergencyRelation.getText().toString(),
                                                                                        emergencyNumber.getText().toString());
                contactDialog.show(fragmentManager, null);
            }
        });

        return rootView;
    }

    public void EmergencyCall(String number){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }

}
