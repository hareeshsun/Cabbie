package com.suntechnologies.cabbie.DataHolders;

import java.io.Serializable;

/**
 * Created by hareeshs on 07-07-2018.
 */

public class FacilityDataContainer implements Serializable {

    public String date;
    public String employee_desitnation;
    public String employee_id;
    public String employee_manger_name;
    public String employee_name;
    public String facility_status;
    public String manager_status;
    public String pickuptime;
    public String registrationToken;
    public String uid;

    public FacilityDataContainer() {
    }

    public FacilityDataContainer(String uid,String date, String employee_desitnation, String employee_id, String employee_manger_name, String employee_name, String facility_status, String manager_status, String pickuptime,String registrationToken) {
        this.date = date;
        this.employee_desitnation = employee_desitnation;
        this.employee_id = employee_id;
        this.employee_manger_name = employee_manger_name;
        this.employee_name = employee_name;
        this.facility_status = facility_status;
        this.manager_status = manager_status;
        this.pickuptime = pickuptime;
        this.registrationToken = registrationToken;
        this.uid = uid;
    }
}
