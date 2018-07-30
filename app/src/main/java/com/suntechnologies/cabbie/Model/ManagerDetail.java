package com.suntechnologies.cabbie.Model;

/**
 * Created by mithulalr on 7/27/2018.
 */

public class ManagerDetail {
    public String name;

    public String empId;
    public String empEmailId;
    public String department;

    ManagerDetail(){}
    public ManagerDetail(String name,String empId,String empEmailId,String department){
        this.name = name;
        this.empId = empId;
        this.empEmailId = empEmailId;
        this.department = department;



    }

}
