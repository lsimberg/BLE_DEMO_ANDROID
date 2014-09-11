package com.lsimberg.smartzone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by leo on 9/11/14.
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent myIntent = new Intent(context, MonitorService.class);
        context.startService(myIntent);
    }
}
