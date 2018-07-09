package com.suntechnologies.cabbie.DataHolders;

import java.util.ArrayList;

/**
 * Created by hareeshs on 09-07-2018.
 */

public class DriverDetails {

    public String vehicleNumber;
    public String vehicleModel;
    public String driverName;
    public String driverNumber;

    public DriverDetails() {
    }

    public DriverDetails(String vehicleNumber, String vehicleModel, String driverName, String driverNumber) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleModel = vehicleModel;
        this.driverName = driverName;
        this.driverNumber = driverNumber;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getDriverNumber() {
        return driverNumber;
    }
}
