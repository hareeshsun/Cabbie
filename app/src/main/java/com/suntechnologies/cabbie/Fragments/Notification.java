package com.suntechnologies.cabbie.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.suntechnologies.cabbie.MainActivity;
import com.suntechnologies.cabbie.R;

/**
 * Created by hareeshs on 02-07-2018.
 */

public class Notification extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View notificationView = inflater.inflate(R.layout.notification_layout,container,false);

        final Button notifyBadge = (Button)  notificationView.findViewById(R.id.notifyButton);

        notifyBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!MainActivity.isNotifyCountVisible){
                    MainActivity.isNotifyCountVisible = true;
                    MainActivity.quantityBadge.setVisibility(View.VISIBLE);
                }
                else {
                    MainActivity.isNotifyCountVisible = false;
                    MainActivity.quantityBadge.setVisibility(View.GONE);
                }
            }
        });

        return notificationView;
    }

}
