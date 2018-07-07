package com.suntechnologies.cabbie.DataHolders;

import java.io.Serializable;

/**
 * Created by hareeshs on 07-07-2018.
 */

public class FacilityDataContainer implements Serializable {

    public String date;
    public String destination;
    public String employeeID;
    public String employeeManager;
    public String employeeName;
    public String facilityStatus;
    public String managerStatus;
    public String pickUpTime;

    public FacilityDataContainer(String date, String destination, String employeeID, String employeeManager, String employeeName, String facilityStatus, String managerStatus, String pickUpTime) {
        this.date = date;
        this.destination = destination;
        this.employeeID = employeeID;
        this.employeeManager = employeeManager;
        this.employeeName = employeeName;
        this.facilityStatus = facilityStatus;
        this.managerStatus = managerStatus;
        this.pickUpTime = pickUpTime;
    }
}
