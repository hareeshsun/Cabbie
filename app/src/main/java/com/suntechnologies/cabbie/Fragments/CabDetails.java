package com.suntechnologies.cabbie.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suntechnologies.cabbie.R;


/**
 * Created by mithulalr on 7/26/2018.
 */

public class CabDetails extends Fragment {

    public CabDetails(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View cabApprovalView = inflater.inflate(R.layout.cab_details, container, false);
        return cabApprovalView;
    }

    }
