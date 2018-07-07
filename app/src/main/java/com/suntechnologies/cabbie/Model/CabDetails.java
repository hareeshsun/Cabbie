package com.suntechnologies.cabbie.Model;

/**
 * Created by hareeshs on 07-07-2018.
 */

public class CabDetails {

    public String vehicleNumber;
    public String driverName;
    public String vehicleModel;
    public String driverNumber;

    public CabDetails(String vehicleNumber, String driverName, String vehicleModel, String driverNumber) {
        this.vehicleNumber = vehicleNumber;
        this.driverName = driverName;
        this.vehicleModel = vehicleModel;
        this.driverNumber = driverNumber;
    }
}
