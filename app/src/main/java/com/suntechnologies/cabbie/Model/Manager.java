package com.suntechnologies.cabbie.Model;

/**
 * Created by hareeshs on 07-07-2018.
 */

public class Manager {

    public String managerName;
    public String managerID;
    public String userID;
    public String registrationToken;

    public Manager(String managerName, String managerID, String userID, String registrationToken) {
        this.managerName = managerName;
        this.managerID = managerID;
        this.userID = userID;
        this.registrationToken = registrationToken;
    }
}
