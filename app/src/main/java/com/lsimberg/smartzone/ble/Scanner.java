package com.lsimberg.smartzone.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by leo on 6/19/14.
 */
public class Scanner {
    private static final String TAG = "BLE.SCANNER";
    private static Scanner scanner = null;

    private Context ctx;
    private ScannerListener scannerListener;
    private BluetoothAdapter bleAdapter = null;
    private long scanPeriod = 0;
    private Handler mHandler = new Handler();
    private boolean scanning = false;
    private Set<Device> devices = new HashSet<Device>();


    private Scanner(Context ctx, long scanPeriod, ScannerListener scannerListener){
        this.ctx = ctx;
        this.scannerListener = scannerListener;
        this.scanPeriod = scanPeriod;
        final BluetoothManager bluetoothManager =
                (BluetoothManager) ctx.getSystemService(Context.BLUETOOTH_SERVICE);
        bleAdapter = bluetoothManager.getAdapter();
        scanning = false;
    }

    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice bleDevice, int rssi,
                                     byte[] scanRecord) {
                    //Log.d(TAG, "Device found: " + bleDevice);

                    Device device = new Device(bleDevice, rssi, scanRecord);
                    devices.add(device);
                    if (scannerListener != null) scannerListener.deviceFound(device);
                }
            };

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            if (scanning) return; //if it is already scanning, return

            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scanning = false;
                    bleAdapter.stopLeScan(mLeScanCallback);
                    if (scannerListener != null) scannerListener.scanStopped(devices);
                }
            }, scanPeriod);

            scanning = true;
            devices = new HashSet<Device>();
            bleAdapter.startLeScan(mLeScanCallback);
        } else {
            scanning = false;
            bleAdapter.stopLeScan(mLeScanCallback);
            if (scannerListener != null) scannerListener.scanStopped(devices);
        }
    }

    public static void startScanner(Context context, long scanPeriod, ScannerListener scannerListener){
        if (scanner == null) {
            scanner = new Scanner(context, scanPeriod, scannerListener);
        }
        scanner.scanLeDevice(true);
    }

    public static void stopScanner(){
        if (scanner == null) return;
        scanner.scanLeDevice(false);
    }
}
