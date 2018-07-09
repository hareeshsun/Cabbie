package com.suntechnologies.cabbie.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.AutoText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.suntechnologies.cabbie.Adapters.FacilityAdapter;
import com.suntechnologies.cabbie.DataHolders.DriverDetails;
import com.suntechnologies.cabbie.DataHolders.FacilityDataContainer;
import com.suntechnologies.cabbie.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by hareeshs on 09-07-2018.
 */
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
public class ApproveCab extends Fragment {

    private List<DriverDetails> driverlist;
    private ArrayList<DriverDetails> selectedDriver;
     EditText  vehicleModel,destinationText, driverNumber,riders, driverName;
    AutoCompleteTextView vehicleNumber;
    private Button provideCab;
    String desination;
    String uid;
    String empId;
    private DatabaseReference mDatabase;
  public ApproveCab(String desination, String uid, String empId){
      this.desination = desination;
      this.uid = uid;
      this.empId= empId;
  }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View cabApprovalView = inflater.inflate(R.layout.approve_cab_details,container,false);

        vehicleNumber = (AutoCompleteTextView) cabApprovalView.findViewById(R.id.vehicleNumber);
        vehicleModel = (EditText) cabApprovalView.findViewById(R.id.vehicleModel);
        driverNumber = (EditText) cabApprovalView.findViewById(R.id.driverNumber);
        driverName = (EditText) cabApprovalView.findViewById(R.id.driverName);
        riders = (EditText) cabApprovalView.findViewById(R.id.riders);
      //  riders.setText("3");
        destinationText = (EditText) cabApprovalView.findViewById(R.id.destination);
        provideCab = (Button) cabApprovalView.findViewById(R.id.provideCab);
        destinationText.setText(desination);
        driverlist = new ArrayList<>();
        driverlist.add(new DriverDetails("KA 03 BM 8055","TATA ZEST - Blue","S Hareesh","9493707194"));
        driverlist.add(new DriverDetails("KA 51 BM 1234","Maruthi Swift - White","R Mithu Lal","7010354978"));
        driverlist.add(new DriverDetails("KA 03 BM 7777","TATA INDICA - Silver","S Hareesh","7382108907"));
        driverlist.add(new DriverDetails("KA 03 BM 9999","TATA TIAGO - Orange","R Mithu Lal","9154241107"));


        ArrayList<String > list = new ArrayList<>();
        for(int i=0;i<driverlist.size();i++){
            list.add(driverlist.get(i).vehicleNumber);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.select_dialog_item, list);

        vehicleNumber.setThreshold(2);
        vehicleNumber.setAdapter(adapter);

        vehicleNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                String selected = (String) arg0.getAdapter().getItem(arg2);

                for(int i=0;i<driverlist.size();i++){
                   if(driverlist.get(i).vehicleNumber.equalsIgnoreCase(selected)){
                       vehicleModel.setText(driverlist.get(i).getVehicleModel());
                       driverName.setText(driverlist.get(i).driverName);
                       driverNumber.setText(driverlist.get(i).driverNumber);


                   }
                }
            }
        });


        provideCab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                writeNewPost(uid,empId);

            }
        });

        return cabApprovalView;
    }


    private void writeNewPost(String uid, String id) {
        String year = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
        String month = new SimpleDateFormat("MMMM", Locale.getDefault()).format(new Date());
        String day = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());

        mDatabase = FirebaseDatabase.getInstance().getReference().child("RequestCab").child(year).child(month).child(day).child(uid).child(id);
        mDatabase.child("facility_status").setValue("true");




    }

}
