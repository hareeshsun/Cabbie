package com.suntechnologies.cabbie.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.suntechnologies.cabbie.Adapters.SpinnerAdapter;
import com.suntechnologies.cabbie.R;

import java.util.ArrayList;

/**
 * Created by hareeshs on 09-07-2018.
 */

public class RejectCab extends Fragment {

    ArrayList<String> rejectedReason;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rejectReasonView = inflater.inflate(R.layout.reject_cab_request,container,false);

        Spinner rejectedSpinner = (Spinner) rejectReasonView.findViewById(R.id.rejectReason);
        EditText rejectOptional = (EditText) rejectReasonView.findViewById(R.id.rejectOptional);
        Button btnReject = (Button) rejectReasonView.findViewById(R.id.btnReject);

        rejectedReason = new ArrayList<>();
        rejectedReason.add("Cabs are not available");
        rejectedReason.add("Book OLA or UBER");
        rejectedReason.add("Drivers are busy");
        rejectedReason.add("Cabs are reserved for other people");
        rejectedReason.add("Permissions Denied");
        rejectedReason.add("Manager Rejected");
        rejectedReason.add("Cabs are not provided at requested time");

        SpinnerAdapter rejectedReasonAdapter = new SpinnerAdapter(getActivity(), android.R.layout.simple_spinner_item, rejectedReason);
        rejectedSpinner.setAdapter(rejectedReasonAdapter);


        return rejectReasonView;
    }
}
