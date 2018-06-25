package com.suntechnologies.cabbie.Fragments;

import android.support.v4.app.Fragment;;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suntechnologies.cabbie.R;

/**
 * Created by hareeshs on 25-06-2018.
 */

public class EmployeeFragment extends Fragment {

    public EmployeeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.employee_home_page,container, false);
        return rootView;
    }
}
