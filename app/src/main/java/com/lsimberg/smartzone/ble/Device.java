package com.lsimberg.smartzone.ble;

import android.bluetooth.BluetoothDevice;

/**
 * Created by leo on 6/20/14.
 */
public class Device {
    private BluetoothDevice bluetoothDevice;
    private int rssi;
    private byte[] scanRecord;

    private Device(){};

    public Device(BluetoothDevice bluetoothDevice, int rssi, byte[] scanRecord){
        this.bluetoothDevice = bluetoothDevice;
        this.rssi = rssi;
        this.scanRecord = scanRecord;
    }

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    public int getRssi() {
        return rssi;
    }

    public byte[] getScanRecord() {
        return scanRecord;
    }

    public String getAddress(){
        if (bluetoothDevice == null) return null;
        return bluetoothDevice.getAddress();
    }

    public String getName(){
        if (bluetoothDevice == null) return null;
        return bluetoothDevice.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device)) return false;
        Device device = (Device) o;
        if (!bluetoothDevice.getAddress().equalsIgnoreCase(device.bluetoothDevice.getAddress())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = bluetoothDevice != null ? bluetoothDevice.getAddress().hashCode() : 0;
        return result;
    }

    @Override
    public String toString() {
        return "Device{" +
                "bluetoothDevice=" + bluetoothDevice +
                ", rssi=" + rssi +
                ", scanRecord=" + new String(scanRecord) +
                '}';
    }
}
