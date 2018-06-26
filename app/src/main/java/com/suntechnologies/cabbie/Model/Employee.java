package com.suntechnologies.cabbie.Model;

/**
 * Created by mithulalr on 6/26/2018.
 */

public class Employee
{
    public String employeeName;
    public String employeeId;
    public String empyloyeeMangerName;
    public String employeeDesitnation;
    public String employeeStatus;

    Employee(){

    }
    public  Employee(  String employeeName, String employeeId, String empyloyeeMangerName, String employeeDesitnation, String employeeStatus){
        this.employeeName =employeeName;
        this.employeeId =employeeId;
        this.empyloyeeMangerName =empyloyeeMangerName;
        this.employeeDesitnation =employeeDesitnation;
        this.employeeStatus =employeeStatus;

    }
}
