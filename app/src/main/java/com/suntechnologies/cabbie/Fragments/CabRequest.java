package com.suntechnologies.cabbie.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suntechnologies.cabbie.R;

/**
 * Created by hareeshs on 25-06-2018.
 */

public class CabRequest extends Fragment {

    public CabRequest() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View cabRequestView = inflater.inflate(R.layout.cab_request_layout,container,false);
        return cabRequestView;
    }
}
