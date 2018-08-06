package com.suntechnologies.cabbie.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.suntechnologies.cabbie.Adapters.FacilityAdapter;
import com.suntechnologies.cabbie.DataHolders.FacilityDataContainer;
import com.suntechnologies.cabbie.HelperMethods;
import com.suntechnologies.cabbie.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import dmax.dialog.SpotsDialog;

/**
 * Created by hareeshs on 02-07-2018.
 */

public class FacilityFragment extends Fragment
{

    FacilityAdapter facilityAdapter;
    private DatabaseReference mDatabase;
    ArrayList<FacilityDataContainer> cabRequestList;
    private RecyclerView facilityRecyclerView;
    private Dialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.facility_layout, container, false);
        facilityRecyclerView = (RecyclerView) rootView.findViewById(R.id.facilityRecyclerView);
        final ImageView sadSmiley = (ImageView) rootView.findViewById(R.id.sadSmiley);
        loadingDialog = new SpotsDialog(getActivity(), "Logging...");
        loadingDialog.show();
        String year = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
        String month = new SimpleDateFormat("MMMM", Locale.getDefault()).format(new Date());
        String day = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
        cabRequestList = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("RequestCab").child(year).child(month).child(day);
        mDatabase.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot uniqueSnapshot : dataSnapshot.getChildren())
                {
                    cabRequestList.clear();
                    for (DataSnapshot employeeId : uniqueSnapshot.getChildren())
                    {
                        FacilityDataContainer dataContainer = employeeId.getValue(FacilityDataContainer.class);
                        if (dataContainer != null)
                        {
                            if (Boolean.parseBoolean(dataContainer.facility_status) && Boolean.parseBoolean(dataContainer.manager_status))
                            {
                                sadSmiley.setVisibility(View.VISIBLE);
                            } else
                            {
                                sadSmiley.setVisibility(View.GONE);
                                cabRequestList.add(new FacilityDataContainer(dataContainer.uid, dataContainer.date, dataContainer.employee_desitnation, dataContainer.employee_id,
                                        dataContainer.employee_manger_name, dataContainer.employee_name, dataContainer.facility_status, dataContainer.manager_status,
                                        dataContainer.pickuptime, dataContainer.registrationToken
                                ));
                            }

                        }

                    }
                }
                facilityAdapter = new FacilityAdapter(cabRequestList, FacilityFragment.this);
                facilityRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                facilityRecyclerView.setHasFixedSize(true);
                facilityRecyclerView.addItemDecoration(new DividerItemDecoration(facilityRecyclerView.getContext(), LinearLayoutManager.VERTICAL));
                facilityRecyclerView.setAdapter(facilityAdapter);
                loadingDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        return rootView;
    }
}
