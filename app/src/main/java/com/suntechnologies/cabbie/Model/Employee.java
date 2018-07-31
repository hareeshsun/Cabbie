package com.suntechnologies.cabbie.Model;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mithulalr on 6/26/2018.
 */

public class Employee implements Serializable {

    public String employee_name;
    public String employee_id;
    public String employee_manger_name;
    public String employee_desitnation;
    public String pickuptime;
    public String manager_status;
    public String facility_status;
    public String date;
    public String registrationToken;
    public String uid;
    public String managerDecision;
    public String facilityDecision;

    Employee(){

    }

    public Employee(String employee_name, String employee_id, String employee_manger_name,String employee_desitnation,
                    String manager_status, String facility_status, String pickuptime, String date, String registrationToken, String uid, String managerDecision, String facilityDecision) {
        this.employee_name = employee_name;
        this.employee_id = employee_id;
        this.employee_manger_name = employee_manger_name;
        this.employee_desitnation = employee_desitnation;
        this.manager_status = manager_status;
        this.facility_status = facility_status;
        this.pickuptime = pickuptime;
        this.date = date;
        this.registrationToken =registrationToken;
        this.uid =uid;
        this.managerDecision = managerDecision;
        this.facilityDecision = facilityDecision;

    }

    public String getDate() {
        return date;
    }


    public String getEmployee_desitnation() {
        return employee_desitnation;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public String getEmployee_manger_name() {
        return employee_manger_name;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public String getPickuptime() {
        return pickuptime;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEmployee_desitnation(String employee_desitnation) {
        this.employee_desitnation = employee_desitnation;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public void setEmployee_manger_name(String employee_manger_name) {
        this.employee_manger_name = employee_manger_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public void setPickuptime(String pickuptime) {
        this.pickuptime = pickuptime;
    }

}
