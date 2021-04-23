/*
 * Nicholas Reich
 * Gardens Of Egregore
 * GardenPageAdapter.java
 * */
package com.ellipsisnamespace.reichnicholas_gardensofegregore.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.ellipsisnamespace.reichnicholas_gardensofegregore.Fragments.ControlFragment;
import com.ellipsisnamespace.reichnicholas_gardensofegregore.Fragments.LightManagerFragment;


public class GardenPageAdapter extends FragmentPagerAdapter {

    private static String mUUID;
    private static final String TAG = "GardenPageAdapter";

    public GardenPageAdapter(FragmentManager fm,String mUUID) {
        super(fm);
        GardenPageAdapter.mUUID = mUUID;
    }

    //create fragments
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Log.e(TAG,"Master Controls");
                return ControlFragment.newInstance(1,"Master Controls",mUUID);
            case 1:
                Log.e(TAG,"Light Manager");
                return LightManagerFragment.newInstance(2, "Light Manager",mUUID);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {

        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return "Master Controls";
            case 1:
                return "Light Manager";
            default:
                return null;
        }


    }

}
