/*
 * Nicholas Reich
 * Gardens Of Egregore
 * LightManagerFragment.java
 * */
package com.ellipsisnamespace.reichnicholas_gardensofegregore.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.ellipsisnamespace.reichnicholas_gardensofegregore.DataModels.GardenValues;
import com.ellipsisnamespace.reichnicholas_gardensofegregore.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.Objects;

public class LightManagerFragment extends Fragment {

    private static final String PAGE_NUMBER = "PAGE_NUMBER";
    private static final String PAGE_TITLE = "PAGE_TITLE";
    private DatabaseReference mLightsStatusRef;

    public LightManagerFragment(){

    }

    public static LightManagerFragment newInstance(int page, String title, String UUID) {


        Bundle args = new Bundle();
        args.putInt(PAGE_NUMBER, page);
        args.putString(PAGE_TITLE, title);
        args.putString(GardenValues.UUID_KEY, UUID);
        LightManagerFragment fragment = new LightManagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return  inflater.inflate(R.layout.light_mngr_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null){
            final String uuid = bundle.getString(GardenValues.UUID_KEY);
            final GardenValues pin = new GardenValues();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            mLightsStatusRef = database.getReference(Objects.requireNonNull(uuid)).child("lightStatus");

            final Switch veg = Objects.requireNonNull(getView()).findViewById(R.id.veg_switch);
            final Switch flower = getView().findViewById(R.id.flower_switch);
            veg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        mLightsStatusRef.setValue(false);
                        pin.isOn(GardenValues.LIGHTS_PIN,uuid);
                        if(flower.isChecked()) {
                            flower.setChecked(false);
                        }
                    }else{

                        if(!flower.isChecked()){
                            pin.isOff(GardenValues.LIGHTS_PIN,uuid);
                        }
                    }

                }
            });
            flower.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        //save time to database for comparision
                        mLightsStatusRef.setValue(true);
                        mLightsStatusRef.child(GardenValues.START_DATE).setValue(new Date().getTime());

                        pin.isOn(GardenValues.LIGHTS_PIN,uuid);
                        if(veg.isChecked()) {
                            veg.setChecked(false);
                        }
                    }else{
                        mLightsStatusRef.setValue(false);
                        if(!veg.isChecked()){
                            pin.isOff(GardenValues.LIGHTS_PIN,uuid);
                        }
                    }

                }
            });
        }


    }
}
