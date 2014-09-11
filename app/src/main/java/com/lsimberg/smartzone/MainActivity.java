package com.lsimberg.smartzone;

//
//  Created by Leo Simberg on 9/10/14.
//  Copyright (c) 2014 Leo Simberg. All rights reserved.
//
//  * This code is a Proof of concept and it does not verify all ble fail conditions, for instance
//  when the bluetooth is disable
//
//  * This code doesn't have battery optimization like variable scan time


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends Activity {
    private static final String TAG = "MonitorService";
    private MonitorService monitorService;
    private ImageView ivQuietZone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ivQuietZone = (ImageView) findViewById(R.id.ivQuietZone);

        Intent i= new Intent(this, MonitorService.class);
        this.startService(i);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Intent intent= new Intent(this, MonitorService.class);
        bindService(intent, mConnection,
                Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(mConnection);
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className,
                                       IBinder binder) {
            MonitorService.MyBinder b = (MonitorService.MyBinder) binder;
            monitorService = b.getService();
            //Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
            setImage();
        }

        public void onServiceDisconnected(ComponentName className) {
            monitorService = null;
        }
    };

    private void setImage(){
        if (monitorService.isActive()){
            ivQuietZone.setImageResource(R.drawable.quiet_zone_color);
        } else {
            ivQuietZone.setImageResource(R.drawable.quiet_zone_gray);
        }
    }

    public void onEnable(View view){
        monitorService.setActive(!monitorService.isActive());
        setImage();
    }
}
