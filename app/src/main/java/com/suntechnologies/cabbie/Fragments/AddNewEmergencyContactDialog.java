package com.suntechnologies.cabbie.Fragments;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.suntechnologies.cabbie.Adapters.SpinnerAdapter;
import com.suntechnologies.cabbie.DataHolders.EmergencyData;
import com.suntechnologies.cabbie.R;

import java.security.cert.PolicyNode;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by hareeshs on 01-08-2018.
 */

public class AddNewEmergencyContactDialog extends DialogFragment {

    private Dialog addNewEmergencyDialog;
    private String selectedRelation;
    private String USER_TOKEN_KEY = "USERDATA";
    private String USER_UID = "USERUID";
    private String userID;
    private String name, relationText, number;

    public AddNewEmergencyContactDialog(String name,String relation,String number) {
        this.name = name;
        this.relationText = relation;
        this.number = number;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        addNewEmergencyDialog = super.onCreateDialog(savedInstanceState);
        addNewEmergencyDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        addNewEmergencyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addNewEmergencyDialog.setCanceledOnTouchOutside(false);
        return addNewEmergencyDialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        addNewEmergencyDialog.getWindow().setLayout(1000, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.add_emergency_details,container,false);

        ImageButton close = (ImageButton) dialogView.findViewById(R.id.close);
        final TextView errorMessage = (TextView) dialogView.findViewById(R.id.errorMessage);
        final EditText fullName = (EditText) dialogView.findViewById(R.id.emergencyName);
        final EditText mobileNumber = (EditText) dialogView.findViewById(R.id.number);
        final EditText otherRelation = (EditText) dialogView.findViewById(R.id.otherRelation);
        final LinearLayout otherRelationLayout = (LinearLayout) dialogView.findViewById(R.id.otherRelationLayout);
        final Spinner relation = (Spinner) dialogView.findViewById(R.id.relation);
        Button addContact = (Button) dialogView.findViewById(R.id.addEmergencyContact);

        if(name.length()>0){
            fullName.setText(name);
        }

        if(number.length() > 0){
            mobileNumber.setText(number);
        }

        ArrayList<String> relationList = new ArrayList<>();
        relationList.add("Select one");
        relationList.add("Parent");
        relationList.add("Guardian");
        relationList.add("Other");

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,relationList);
        relation.setAdapter(spinnerAdapter);

        for (int i=0; i<relationList.size(); i++){
            if(relationList.get(i).equalsIgnoreCase(relationText)){
                relation.setSelection(i);
            }
        }

        relation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(relation.getSelectedItem().toString().equalsIgnoreCase("Other")){
                    otherRelationLayout.setVisibility(View.VISIBLE);
                }
                else {
                    otherRelationLayout.setVisibility(View.GONE);
                }
                selectedRelation = relation.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(USER_TOKEN_KEY, MODE_PRIVATE);
        userID = sharedPreferences.getString(USER_UID, null);

        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedRelation.equalsIgnoreCase("other")){
                    selectedRelation = otherRelation.getText().toString();
                }
                if (fullName.getText().toString().length() > 2 && !selectedRelation.equalsIgnoreCase("select one") && mobileNumber.getText().length() == 10) {
                    errorMessage.setVisibility(View.GONE);

                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("usersData/" + userID);
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            reference.child("emergencyDetails").setValue(new EmergencyData(fullName.getText().toString(), selectedRelation, mobileNumber.getText().toString()));
                            addNewEmergencyDialog.dismiss();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                } else {
                    errorMessage.setVisibility(View.VISIBLE);
                }

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewEmergencyDialog.dismiss();
            }
        });

        return dialogView;
    }
}
