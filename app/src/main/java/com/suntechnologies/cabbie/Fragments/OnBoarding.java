package com.suntechnologies.cabbie.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.suntechnologies.cabbie.Adapters.FacilityAdapter;
import com.suntechnologies.cabbie.Adapters.OnBoardingAdapter;
import com.suntechnologies.cabbie.Model.RideEmployeeDetail;
import com.suntechnologies.cabbie.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hareeshs on 02-07-2018.
 */

public class OnBoarding extends Fragment {

    DatabaseReference reference;
    ArrayList<RideEmployeeDetail> cabDetailses;
    OnBoardingAdapter onBoardingAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View onBoarding = inflater.inflate(R.layout.on_boarding_layout,container,false);

        final RecyclerView rvOnBoarding = (RecyclerView) onBoarding.findViewById(R.id.rvOnBoarding);
        cabDetailses = new ArrayList<>();
        String day = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
        reference = FirebaseDatabase.getInstance().getReference("CabDetails").child(day);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot employeeID : dataSnapshot.getChildren()){
                    RideEmployeeDetail approvedCabDetails = employeeID.getValue(RideEmployeeDetail.class);
                    if(approvedCabDetails != null){
                        cabDetailses.add(new RideEmployeeDetail(approvedCabDetails.vehicleNumber, approvedCabDetails.driverName,
                                approvedCabDetails.vehicleModel, approvedCabDetails.driverNumber, approvedCabDetails.rideNumber,
                                approvedCabDetails.destination,approvedCabDetails.pickupTime));
                    }

                    onBoardingAdapter = new OnBoardingAdapter(cabDetailses);
                    rvOnBoarding.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    rvOnBoarding.setHasFixedSize(true);
                    rvOnBoarding.setAdapter(onBoardingAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return onBoarding;
    }
}
