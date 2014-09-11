package com.lsimberg.smartzone.ble;

import java.util.Set;

/**
 * Created by leo on 6/20/14.
 */
public interface ScannerListener {

    /**
     * Called when the scan stop
     * @param devices List of found devices
     *
     */
    public void scanStopped(Set<Device> devices);


    public void deviceFound(Device device);
}
