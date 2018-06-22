package com.suntechnologies.cabbie;

/**
 * Created by mithulalr on 6/21/2018.
 */

public class User {


    public String firstName;
    public String lastName;
    public String phoneNumber;
    public String designation;
    public String reportingManger;
    public String emailAddress;
    public String emplyoeeId;
    public String address;
    public String currentAddress;
    public String landmark;



    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String emplyoeeId,String firstName, String lastName,String phoneNumber,String designation,String reportingManger ,String emailAddress,String address,String currentAddress,String landmark ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber =phoneNumber;
        this.designation = designation;
        this.reportingManger = reportingManger;
        this.emailAddress = emailAddress;
        this.emplyoeeId = emplyoeeId;
        this.address = address;
        this.currentAddress = currentAddress;
        this.landmark = landmark;

    }

}
