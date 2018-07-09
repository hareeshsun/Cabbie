package com.suntechnologies.cabbie.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.suntechnologies.cabbie.Model.Employee;
import com.suntechnologies.cabbie.Model.RideEmployeeDetail;
import com.suntechnologies.cabbie.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hareeshs on 09-07-2018.
 */

public class ApprovedCabDetails extends Fragment {

    private DatabaseReference reference;
    private String year, month, day, empID;
    private ArrayList<Employee> list = new ArrayList<>();

    public ApprovedCabDetails(String empID) {
        this.empID = empID;
        day = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View approvedCabDetails = inflater.inflate(R.layout.approved_cab_details, container, false);

        final TextView vehicleNumber = (TextView) approvedCabDetails.findViewById(R.id.vehicleNumber);
        final TextView vehicleModel = (TextView) approvedCabDetails.findViewById(R.id.vehicleModel);
        final TextView driverName = (TextView) approvedCabDetails.findViewById(R.id.driverName);
        final TextView driverNumber = (TextView) approvedCabDetails.findViewById(R.id.driverNumber);
        final TextView destination = (TextView) approvedCabDetails.findViewById(R.id.destination);
        final TextView pickUpTime = (TextView) approvedCabDetails.findViewById(R.id.pickUpTime);
        Button dismiss = (Button) approvedCabDetails.findViewById(R.id.dismiss);

        reference = FirebaseDatabase.getInstance().getReference("CabDetails" + "/" + day + "/" +empID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RideEmployeeDetail rideDetails = dataSnapshot.getValue(RideEmployeeDetail.class);
                if(rideDetails != null){
                    vehicleNumber.setText(rideDetails.vehicleNumber);
                    vehicleModel.setText(rideDetails.vehicleModel);
                    driverName.setText(rideDetails.driverName);
                    driverNumber.setText(rideDetails.driverNumber);
                    destination.setText(rideDetails.destination);
                    pickUpTime.setText(rideDetails.pickupTime);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getFragmentManager().popBackStack();
            }
        });

        return approvedCabDetails;
    }


}
