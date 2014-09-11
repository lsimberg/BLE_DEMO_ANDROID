package com.lsimberg.smartzone;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by leo on 9/10/14.
 */
public class MonitorService extends Service {
    private static final String TAG = "MonitorService";
    private static final String ACTIVATE_KEY = "activate_key";
    private final IBinder mBinder = new MyBinder();

    private boolean active = true;
    private DetectTags detectTags;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d(TAG, "Start Command");
        detectTags = new DetectTags(this);
        active = Persistency.loadBool(this, ACTIVATE_KEY);
        if (active) detectTags.start();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "On Destroy");
    }

    public class MyBinder extends Binder {
        MonitorService getService() {
            return MonitorService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public boolean isActive(){
        return active;
    }

    public void setActive(boolean value){
        active = value;
        Persistency.saveBool(this, ACTIVATE_KEY, value);
        if (active) {
            detectTags.start();
        } else {
            detectTags.stop();
        }

    }
}
