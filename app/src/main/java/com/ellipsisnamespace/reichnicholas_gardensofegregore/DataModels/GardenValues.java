/*
 * Nicholas Reich
 * Gardens Of Egregore
 * GardenValues.java
 * */
package com.ellipsisnamespace.reichnicholas_gardensofegregore.DataModels;

import android.util.Log;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.io.IOException;
import java.util.Objects;

public class GardenValues {

    //contract values
    public static final String LIGHTS_PIN = "BCM15";
    public static final String PUMP_PIN = "BCM23";
    public static final String FAN_PIN = "BCM24";
    public static final String UUID_KEY = "_UUID";
    public static final String DAY_TIME_BOOL = "DAY_TIME_BOOL";
    public static final String NIGHT_TIME_BOOL = "NIGHT_TIME_BOOL";
    public static final String NIGHT_TIME = "NIGHT_TIME";
    public static final String DAY_TIME = "DAY_TIME";
    public static final String LIGHTS = "LIGHTS";
    public static final String PREFS_NAME = "MyPrefs";
    public static final String START_DATE = "START_DATE";

    private static final String TAG = "GardenValues";
    private DatabaseReference mCurrentStatusRef;
    private FirebaseDatabase mDatabase;

    private Gpio mPin;

    public GardenValues(){

    }

    public void isOn(String pinName,String uuid){
        PeripheralManager peripheralManagerService = PeripheralManager.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mCurrentStatusRef = mDatabase.getReference(Objects.requireNonNull(uuid)).child("currentStatus");
        try {
            //get pin
            mPin = peripheralManagerService.openGpio(pinName);
            //set direction
            Log.e(TAG,"ON");
            mPin.setDirection(Gpio.DIRECTION_OUT_INITIALLY_HIGH);
            mPin.setActiveType(Gpio.ACTIVE_LOW);
            //set pin value
            mPin.setValue(true);
            mPin.close();
            //change firebase
            mCurrentStatusRef.child(pinName).setValue(true);
            Log.e(TAG,mPin.getName());
        } catch (IOException e) {
            Log.e(TAG,"An error has ocurred");
        }

    }

    public void isOff(String pinName,String uuid){
        PeripheralManager peripheralManagerService = PeripheralManager.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mCurrentStatusRef = mDatabase.getReference(Objects.requireNonNull(uuid)).child("currentStatus");
        try {
            //get pin
            mPin = peripheralManagerService.openGpio(pinName);
            //set direction
            Log.e(TAG,"OFF");
            mPin.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            mPin.setActiveType(Gpio.ACTIVE_LOW);
            //set pin value
            mPin.setValue(false);
            mPin.close();
            //change firebase
            mCurrentStatusRef.child(pinName).setValue(false);
            Log.e(TAG,mPin.getName());
        } catch (IOException e) {
            Log.e(TAG,"An error has ocurred");
        }

    }
}
