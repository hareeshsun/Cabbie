package com.suntechnologies.cabbie;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.suntechnologies.cabbie.Fragments.CabRequest;
import com.suntechnologies.cabbie.Fragments.EmployeeFragment;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    Toolbar coordinatorLayout;
    FrameLayout frameLayout;
    ImageView mainMenu, notificaiton, requestCab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = (Toolbar) findViewById(R.id.toolbar);
        mainMenu = (ImageView) coordinatorLayout.findViewById(R.id.mainMenu);
        notificaiton = (ImageView) coordinatorLayout.findViewById(R.id.notifications);
        requestCab = (ImageView) coordinatorLayout.findViewById(R.id.requestCab);

        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);

        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        HelperMethods.replaceFragment(this,frameLayout.getId(),new EmployeeFragment(),true);

        requestCab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                HelperMethods.replaceFragment(MainActivity.this,frameLayout.getId(),new CabRequest(),true);
            }
        });

    }

    @Override
    public void onBackStackChanged() {
        super.onBackPressed();
    }
}
