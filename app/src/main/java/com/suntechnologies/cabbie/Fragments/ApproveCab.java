package com.suntechnologies.cabbie.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.suntechnologies.cabbie.DataHolders.DriverDetails;
import com.suntechnologies.cabbie.R;

import java.util.ArrayList;

/**
 * Created by hareeshs on 09-07-2018.
 */

public class ApproveCab extends Fragment {

    private ArrayList<DriverDetails> driverlist;
    private ArrayList<DriverDetails> selectedDriver;
    private EditText vehicleNumber, vehicleModel, driverNumber, driverName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View cabApprovalView = inflater.inflate(R.layout.approve_cab_details,container,false);

        vehicleNumber = (EditText) cabApprovalView.findViewById(R.id.vehicleNumber);
        vehicleModel = (EditText) cabApprovalView.findViewById(R.id.vehicleModel);
        driverNumber = (EditText) cabApprovalView.findViewById(R.id.driverNumber);
        driverName = (EditText) cabApprovalView.findViewById(R.id.driverName);

        driverlist = new ArrayList<>();
        driverlist.add(new DriverDetails("KA 03 BM 8055","TATA ZEST - Blue","S Hareesh","9493707194"));
        driverlist.add(new DriverDetails("KA 51 BM 1234","Maruthi Swift - White","R Mithu Lal","7010354978"));
        driverlist.add(new DriverDetails("KA 03 BM 7777","TATA INDICA - Silver","S Hareesh","7382108907"));
        driverlist.add(new DriverDetails("KA 03 BM 9999","TATA TIAGO - Orange","R Mithu Lal","9154241107"));

        vehicleNumber.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence inputString, int arg1, int arg2, int arg3)
            {
                filter(inputString.toString());
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3)
            {
            }

            @Override
            public void afterTextChanged(Editable arg0)
            {
            }
        });

        return cabApprovalView;
    }



    void filter(String text) {

/*        for(DriverDetails driverDetailses : driverlist){
            if(driverDetailses.getVehicleNumber().contains(text)){
                selectedDriver = new ArrayList<>();
                selectedDriver.add(driverDetailses);
            }
        }

        if(selectedDriver != null && selectedDriver.size() > 0){
            vehicleNumber.setText(selectedDriver.get(0).getVehicleNumber());
            vehicleModel.setText(selectedDriver.get(0).getVehicleModel());
            driverName.setText(selectedDriver.get(0).getDriverName());
            driverNumber.setText(selectedDriver.get(0).getDriverNumber());
        }*/
    }
}
