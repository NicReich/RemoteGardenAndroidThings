/*
 * Nicholas Reich
 * Gardens Of Egregore
 * MainActivity.java
 * */
package com.ellipsisnamespace.reichnicholas_gardensofegregore.Activities;


import android.app.AlarmManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ellipsisnamespace.reichnicholas_gardensofegregore.Adapters.GardenPageAdapter;

import com.ellipsisnamespace.reichnicholas_gardensofegregore.R;
import com.ellipsisnamespace.reichnicholas_gardensofegregore.Services.GardenJobScheduler;

import java.util.UUID;

/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */
public class MainActivity extends AppCompatActivity  {

    private static final String UUID_KEY = "_UUID";
    private static final String PREFS_NAME = "MyPrefs";


    private FragmentPagerAdapter adapterViewPager;

    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        JobScheduler jobScheduler = (JobScheduler)getApplicationContext()
//                .getSystemService(JOB_SCHEDULER_SERVICE);
//        ComponentName componentName = new ComponentName(this,
//                GardenJobScheduler.class);
//        PersistableBundle pb = new PersistableBundle();
//                 pb.putString(UUID_KEY, getDeviceId());
//        JobInfo jobInfoObj = new JobInfo.Builder(1, componentName)
//                .setExtras(pb)
//                .setPeriodic(30000)
//                .build();
//        JobInfo jobInfo = new JobInfo.Builder(1, componentName)
//                .setExtras(pb)
//                .setMinimumLatency(1)
//                .setOverrideDeadline(1)
//                .build();
//
//        jobScheduler.schedule(jobInfoObj);

        //viewpager
        final ViewPager vpPager = findViewById(R.id.vpPager);

        adapterViewPager = new GardenPageAdapter(getSupportFragmentManager(),getDeviceId());
        vpPager.setAdapter(adapterViewPager);

    }
    private String getDeviceId() {
        //create user id for Firebase
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
        if(!prefs.contains(UUID_KEY)) {
            prefs.edit().putString(UUID_KEY, UUID.randomUUID().toString()).apply();
            Log.e(TAG, UUID.randomUUID().toString());
        }
        Log.e(TAG,prefs.getString(UUID_KEY, UUID.randomUUID().toString()));
        return prefs.getString(UUID_KEY, UUID.randomUUID().toString());
    }

}
