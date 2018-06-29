package com.suntechnologies.cabbie.Model;

/**
 * Created by mithulalr on 6/26/2018.
 */

public class Employee
{
    public String employee_name;
    public String employee_id;
    public String empyloyee_manger_name;
    public String employee_desitnation;
    public String employee_status;

    Employee(){

    }
    public  Employee(String employee_name, String employee_id, String empyloyee_manger_name, String employee_desitnation, String employee_status){
        this.employee_name = employee_name;
        this.employee_id = employee_id;
        this.empyloyee_manger_name = empyloyee_manger_name;
        this.employee_desitnation = employee_desitnation;
        this.employee_status = employee_status;

    }
}
