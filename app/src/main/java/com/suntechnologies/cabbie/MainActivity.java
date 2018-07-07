package com.suntechnologies.cabbie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.suntechnologies.cabbie.DataHolders.UserData;
import com.suntechnologies.cabbie.Fragments.AccountDetails;
import com.suntechnologies.cabbie.Fragments.CabRequest;
import com.suntechnologies.cabbie.Fragments.EmergencyDetails;
import com.suntechnologies.cabbie.Fragments.EmployeeFragment;
import com.suntechnologies.cabbie.Fragments.FacilityFragment;
import com.suntechnologies.cabbie.Fragments.Notification;
import com.suntechnologies.cabbie.Fragments.OnBoarding;
import com.suntechnologies.cabbie.Fragments.PreviousRideDetails;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener, NavigationView.OnNavigationItemSelectedListener {

    public static FrameLayout frameLayout;
    public static boolean isNotifyCountVisible = false;
    public static TextView quantityBadge;
    private UserData userData;
    private String uid, registrationToken;
    private FirebaseAuth auth;
    DatabaseReference mDatabase;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.header_name);

        ImageView notification = (ImageView) findViewById(R.id.notification);
        ImageView requestCab = (ImageView) findViewById(R.id.cabRequest);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        quantityBadge = (TextView) findViewById(R.id.quantityBadge);

        auth = FirebaseAuth.getInstance();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        String USER_TOKEN_KEY = "USERTOKEN";
        SharedPreferences preferences = getSharedPreferences(USER_TOKEN_KEY, MODE_PRIVATE);
        String USER_UID = "USERUID";
        uid = preferences.getString(USER_UID, null);

        String REGISTRATION_KEY = "REGISTRATION_KEY";
        String REGISTRATION_VALUE = "REGISTRATION_VALUE";
        SharedPreferences preferences1 = getSharedPreferences(REGISTRATION_KEY, MODE_PRIVATE);
        registrationToken = preferences1.getString(REGISTRATION_VALUE, null);

        mDatabase = FirebaseDatabase.getInstance().getReference("usersData/" + uid);
        database = FirebaseDatabase.getInstance();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    userData = dataSnapshot.getValue(UserData.class);
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    HelperMethods.replaceFragment(MainActivity.this, frameLayout.getId(), new EmployeeFragment(), false);
                } else {
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    if (firebaseUser != null) {
                        database.getReference("admin").child("registrationToken").setValue(registrationToken);
                    }
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    HelperMethods.replaceFragment(MainActivity.this, frameLayout.getId(), new FacilityFragment(), true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        requestCab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                HelperMethods.replaceFragment(MainActivity.this, frameLayout.getId(), new CabRequest(), true);
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                HelperMethods.replaceFragment(MainActivity.this, frameLayout.getId(), new Notification(), true);
            }
        });

    }

    @Override
    public void onBackStackChanged() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_account) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            HelperMethods.replaceFragment(MainActivity.this, frameLayout.getId(), new AccountDetails(userData, uid), true);
        } else if (id == R.id.nav_boarding) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            HelperMethods.replaceFragment(MainActivity.this, frameLayout.getId(), new OnBoarding(), true);
        } else if (id == R.id.nav_previous) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            HelperMethods.replaceFragment(MainActivity.this, frameLayout.getId(), new PreviousRideDetails(), true);

        } else if (id == R.id.nav_emergency) {

            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            HelperMethods.replaceFragment(MainActivity.this, frameLayout.getId(), new EmergencyDetails(), true);
        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(MainActivity.this, LoginPage.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_about_us) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
