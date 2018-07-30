package com.suntechnologies.cabbie.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.suntechnologies.cabbie.HelperMethods;
import com.suntechnologies.cabbie.MainActivity;
import com.suntechnologies.cabbie.R;

/**
 * Created by mithulalr on 7/26/2018.
 */

public class ManagerDetails extends Fragment
{
    ImageButton addNewManager;

    public  ManagerDetails(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.manager_details, container, false);

            addNewManager = (ImageButton) view.findViewById(R.id.addNewManager);
        addNewManager.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                HelperMethods.replaceFragment(getActivity(), MainActivity.frameLayout.getId(), new AddNewManager(), true);
            }
        });

        return view;
    }

}
