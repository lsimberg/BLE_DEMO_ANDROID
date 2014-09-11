package com.lsimberg.smartzone;

//
//  Created by Leo Simberg on 9/10/14.
//  Copyright (c) 2014 Leo Simberg. All rights reserved.
//
//  * This code is a Proof of concept and it does not verify all ble fail conditions, for instance
//  when the bluetooth is disable
//
//  * This code doesn't have battery optimization like variable scan time

import android.content.Context;
import android.location.Criteria;
import android.os.Handler;
import android.util.Log;

import com.lsimberg.smartzone.ble.Device;
import com.lsimberg.smartzone.ble.Scanner;
import com.lsimberg.smartzone.ble.ScannerListener;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by leo on 8/26/14.
 */
public class DetectTags implements ScannerListener{
    private static final String TAG = "DetectTags";

    private static final long DEFAULT_SCAN_PERIOD = 3000; //This value is low for test
    private static final long DEFAULT_WATCH_PERIOD = 5000;
    private static final long DEFAULT_WAITING_PERIOD = 1000; //This value is low for test
    private static final int RSSI_LIMIT_MIN = -70; //the range is very short for test

    private Context ctx;
    private long scanPeriod = DEFAULT_SCAN_PERIOD; //TODO: Create a variable scan period
    private long waitingPeriod = DEFAULT_WAITING_PERIOD; //TODO: Create a variable waiting period
    private long watchPeriod = DEFAULT_WATCH_PERIOD;
    private Handler hWatch = new Handler();
    private static Set<String> tags = new HashSet<String>();
    private static boolean silenceMode = false;
    private static boolean activeRun = false;

    Criteria criteria = new Criteria();

    static{
        tags.add("E0:C7:9D:8D:53:39"); //TODO: Create a admin to registry tags
    }

    public DetectTags(Context ctx){
        this.ctx = ctx;
    }

    public void start(){
        activeRun = true;
        Scanner.startScanner(ctx, scanPeriod, this);
    }

    public void stop(){
        activeRun = false;
        Scanner.stopScanner();
        silenceMode = false;
        RingerMode.setRinger2Normal(ctx);
    }

    @Override
    public void scanStopped(Set<Device> devices) {
        Log.d(TAG, "BLE Devices: \n" + devices);
        Handler hd = new Handler();
        if (activeRun) hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                Scanner.startScanner(ctx, scanPeriod, DetectTags.this);
            }
        }, waitingPeriod);
    }

    public void deviceFound(Device device) {
        Log.d(TAG, "BLE Found: \n" + device);

        if (device == null) {
            Log.e(TAG, "Device is NULL");
            return;
        }

        String id = device.getAddress().toUpperCase();
        if (tags.contains(id)){
            int r = device.getRssi();
            //Log.d(TAG, "------>>>> R: " + r + " rssi: " + device.getRssi());
            if (r > RSSI_LIMIT_MIN) {
                if (!silenceMode) {
                    silenceMode = true;
                    Log.d(TAG, "Vibrate mode");
                    RingerMode.setRinger2Vibrate(ctx);
                }
                watch();
            }
        }
    }

    /**
     *  If not detect the tag or the signal is so weak, turn bach to normal mode
     */
    public void watch(){
        hWatch.removeCallbacksAndMessages(null);

        Runnable run = new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Normal mode");
                silenceMode = false;
                RingerMode.setRinger2Normal(ctx);
            }
        };
        hWatch.postDelayed(run, watchPeriod);
    }
}
