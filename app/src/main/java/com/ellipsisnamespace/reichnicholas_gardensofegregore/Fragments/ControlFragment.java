/*
* Nicholas Reich
* Gardens Of Egregore
* ControlFragment.java
* */
package com.ellipsisnamespace.reichnicholas_gardensofegregore.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.ellipsisnamespace.reichnicholas_gardensofegregore.DataModels.GardenValues;
import com.ellipsisnamespace.reichnicholas_gardensofegregore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class ControlFragment extends Fragment {

    private static final String PAGE_NUMBER = "PAGE_NUMBER";
    private static final String PAGE_TITLE = "PAGE_TITLE";
//    private static final String LIGHTS_PIN = "BCM15";
//    private static final String PUMP_PIN = "BCM23";
//    private static final String FAN_PIN = "BCM24";
   // private static final String UUID_KEY = "_UUID";


//    private DatabaseReference mCurrentStatusRef;
    private static final String TAG = "ControlFragment";

    public ControlFragment(){

    }

    public static ControlFragment newInstance(int page, String title, String UUID) {

        Bundle args = new Bundle();
        args.putInt(PAGE_NUMBER, page);
        args.putString(PAGE_TITLE, title);
        args.putString(GardenValues.UUID_KEY, UUID);
        ControlFragment fragment = new ControlFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.control_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final GardenValues pin = new GardenValues();
        Bundle bundle = getArguments();
        if(bundle != null){
            final String uuid = bundle.getString(GardenValues.UUID_KEY);
           // getValues(uuid);
            //firebase
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            mCurrentStatusRef = database.getReference(Objects.requireNonNull(uuid)).child("currentStatus");
            //switch on/off save true/false to database by pin name
            Switch lights =  Objects.requireNonNull(getView()).findViewById(R.id.lights_on);
            lights.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        pin.isOn(GardenValues.LIGHTS_PIN,uuid);

                    } else {
                        pin.isOff(GardenValues.LIGHTS_PIN,uuid);
                       // mCurrentStatusRef.child(LIGHTS_PIN).setValue(false);
                    }

                }
            });
            Switch water =  getView().findViewById(R.id.water_on);
            water.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        pin.isOn(GardenValues.PUMP_PIN,uuid);
                     //   mCurrentStatusRef.child(PUMP_PIN).setValue(true);
                    } else {
                        pin.isOff(GardenValues.PUMP_PIN,uuid);
                     //   mCurrentStatusRef.child(PUMP_PIN).setValue(false);
                    }
                }
            });
            Switch fan =  getView().findViewById(R.id.fan_on);
            fan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        pin.isOn(GardenValues.FAN_PIN,uuid);
                      //  mCurrentStatusRef.child(FAN_PIN).setValue(true);
                    } else {
                        pin.isOff(GardenValues.FAN_PIN,uuid);
                      //  mCurrentStatusRef.child(FAN_PIN).setValue(false);
                    }
                }
            });

        }

    }
    //get pin values from database for updating ui
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){
          Log.e(TAG,"Control");
          Bundle bundle = getArguments();
          if(getView()!=null&&bundle!=null){
              getValues(bundle.getString(GardenValues.UUID_KEY));
          }

        }
    }
    private void getValues(String uuid){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final ArrayList values = new ArrayList();
        database.getReference(uuid).child("currentStatus").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Object b = data.getValue();
                    values.add(b);
                    Log.e(TAG,Objects.requireNonNull(b).toString());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }



}
