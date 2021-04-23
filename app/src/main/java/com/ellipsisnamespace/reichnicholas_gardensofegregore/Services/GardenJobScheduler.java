package com.ellipsisnamespace.reichnicholas_gardensofegregore.Services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.ellipsisnamespace.reichnicholas_gardensofegregore.DataModels.GardenValues;


public class GardenJobScheduler extends JobService {



    private static final String TAG = "GardenJobScheduler";

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e("GardenJobScheduler", "GardenJobScheduler started...");
        String uuid =params.getExtras().getString(GardenValues.UUID_KEY);
        LightTask task = new LightTask();
        task.execute(uuid);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.e("GardenJobScheduler", "GardenJobScheduler stopped...");
        return true;
    }
    private void getTime(String uuid){
        long time;
        GardenValues values = new GardenValues();
        //18 hours = 64800000
        //6 h = 21600000
        SharedPreferences prefs = getSharedPreferences(GardenValues.PREFS_NAME, 0);
        if(prefs.getBoolean(GardenValues.LIGHTS,false)) {
            if (prefs.getBoolean(GardenValues.DAY_TIME_BOOL,false)) {
                time = prefs.getLong(GardenValues.DAY_TIME, 0);
                if(time >= 64800000){
                    values.isOff(GardenValues.LIGHTS_PIN,uuid);
                    prefs.edit().putBoolean(GardenValues.DAY_TIME_BOOL,false).apply();
                    prefs.edit().putBoolean(GardenValues.NIGHT_TIME_BOOL,true).apply();
                }else{
                    return;
                }

            } else if (prefs.getBoolean(GardenValues.NIGHT_TIME_BOOL,false)) {
                time = prefs.getLong(GardenValues.NIGHT_TIME, 0);
                if(time >= 21600000){
                    values.isOn(GardenValues.LIGHTS_PIN,uuid);
                    prefs.edit().putBoolean(GardenValues.NIGHT_TIME_BOOL,false).apply();
                    prefs.edit().putBoolean(GardenValues.DAY_TIME_BOOL,true).apply();
                }else{
                    return;
                }
            }
        }
        Log.e(TAG,"NO LIGHTS");
    }
    private class LightTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            Log.e("LightTask", "Clean up the task here and call jobFinished...");
           // jobFinished(params, false);
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(String... params) {
            Log.e("LightTask", "Working here...");
            getTime(params[0]);
            return null;
        }
    }

}
