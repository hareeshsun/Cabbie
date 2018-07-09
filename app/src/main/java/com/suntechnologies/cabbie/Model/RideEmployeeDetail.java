package com.suntechnologies.cabbie.Model;

/**
 * Created by mithulalr on 7/9/2018.
 */

public class RideEmployeeDetail
{
    public String vehicleNumber;
    public String driverName;
    public String vehicleModel;
    public String driverNumber;
    public String rideNumber;
    public String  destination;
    public String  pickupTime;

    public RideEmployeeDetail(String vehicleNumber, String driverName, String vehicleModel, String driverNumber,String rideNumber,String destination,String pickupTime) {
        this.vehicleNumber = vehicleNumber;
        this.driverName = driverName;
        this.vehicleModel = vehicleModel;
        this.driverNumber = driverNumber;
        this.rideNumber = rideNumber;
        this.destination = destination;
        this.pickupTime = pickupTime;
    }

}
