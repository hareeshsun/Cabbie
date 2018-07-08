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
import com.suntechnologies.cabbie.DataHolders.FacilityDataContainer;
import com.suntechnologies.cabbie.HelperMethods;
import com.suntechnologies.cabbie.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hareeshs on 02-07-2018.
 */

public class FacilityFragment extends Fragment {

    FacilityAdapter facilityAdapter;
    private DatabaseReference mDatabase;
    ArrayList<FacilityDataContainer> cabRequestList = new ArrayList<>();
    private RecyclerView facilityRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.facility_layout, container, false);
        facilityRecyclerView = (RecyclerView) rootView.findViewById(R.id.facilityRecyclerView);

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String formattedYear = Integer.toString(year);
        String formattedMonth = HelperMethods.getStringFormattedMonth(month);
        String formattedDay = Integer.toString(day);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("RequestCab").child(formattedYear).child(formattedMonth).child("08");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
/*                System.out.println("Drunken Monkey : " + dataSnapshot.toString());
                FacilityDataContainer dataContainer = dataSnapshot.getValue(FacilityDataContainer.class);
                if(dataContainer != null)
                cabRequestList.add(new FacilityDataContainer(dataContainer.date, dataContainer.destination, dataContainer.employeeID,
                                dataContainer.employeeManager, dataContainer.employeeName, dataContainer.facilityStatus, dataContainer.managerStatus,
                                dataContainer.pickUpTime));*/

                for (DataSnapshot uniqueSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot employeeId : uniqueSnapshot.getChildren()) {
                        FacilityDataContainer dataContainer = employeeId.getValue(FacilityDataContainer.class);
                        if (dataContainer != null)
                            cabRequestList.add(new FacilityDataContainer(dataContainer.date, dataContainer.employee_desitnation, dataContainer.employee_id,
                                    dataContainer.employee_manger_name, dataContainer.employee_name, dataContainer.facility_status, dataContainer.manager_status,
                                    dataContainer.pickuptime
                            ));
                    }
                }

/*                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    FacilityDataContainer dataContainer = dataSnapshot1.getValue(FacilityDataContainer.class);
                    if(dataContainer != null)
                    cabRequestList.add(new FacilityDataContainer(dataContainer.date, dataContainer.destination, dataContainer.employeeID,
                            dataContainer.employeeManager, dataContainer.employeeName, dataContainer.facilityStatus, dataContainer.managerStatus,
                            dataContainer.pickUpTime));
                }*/


                facilityAdapter = new FacilityAdapter(cabRequestList);
                facilityRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                facilityRecyclerView.setHasFixedSize(true);
                facilityRecyclerView.addItemDecoration(new DividerItemDecoration(facilityRecyclerView.getContext(), LinearLayoutManager.VERTICAL));
                facilityRecyclerView.setAdapter(facilityAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return rootView;
    }
}
