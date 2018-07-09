package com.suntechnologies.cabbie.Model;

/**
 * Created by mithulalr on 6/21/2018.
 */

public class User {


    public String firstName;
    public String lastName;
    public String phoneNumber;
    public String designation;
    public String reportingManager;
    public String emailAddress;
    public String employeeId;
    public String address;
    public String currentAddress;
    public String landmark;
public  String registrationToken;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String employeeId, String firstName, String lastName, String phoneNumber, String designation, String reportingManager , String emailAddress, String address, String currentAddress, String landmark,String registrationToken ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber =phoneNumber;
        this.designation = designation;
        this.reportingManager = reportingManager;
        this.emailAddress = emailAddress;
        this.employeeId = employeeId;
        this.address = address;
        this.currentAddress = currentAddress;
        this.landmark = landmark;
        this.registrationToken = registrationToken;

    }

}
