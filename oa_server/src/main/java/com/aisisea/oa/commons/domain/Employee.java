package com.aisisea.oa.commons.domain;

import java.sql.Timestamp;

public class Employee {

    private int idId;
    private int empId;
    private String idNum;
    private String empSex;
    private String empName;
    private String empPhone;
    private String empEmail;
    private String deptId;
    private String empPost;
    private String empRemarks;
    private Timestamp empBir;

    public Employee() {
    }

    public int getIdId() {
        return idId;
    }

    public void setIdId(int idId) {
        this.idId = idId;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getEmpSex() {
        return empSex;
    }

    public void setEmpSex(String empSex) {
        this.empSex = empSex;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(String empPhone) {
        this.empPhone = empPhone;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getEmpPost() {
        return empPost;
    }

    public void setEmpPost(String empPost) {
        this.empPost = empPost;
    }

    public String getEmpRemarks() {
        return empRemarks;
    }

    public void setEmpRemarks(String empRemarks) {
        this.empRemarks = empRemarks;
    }

    public Timestamp getEmpBir() {
        return empBir;
    }

    public void setEmpBir(Timestamp empBir) {
        this.empBir = empBir;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "idId=" + idId +
                ", empId=" + empId +
                ", idNum='" + idNum + '\'' +
                ", empSex='" + empSex + '\'' +
                ", empName='" + empName + '\'' +
                ", empPhone='" + empPhone + '\'' +
                ", empEmail='" + empEmail + '\'' +
                ", deptId='" + deptId + '\'' +
                ", empPost='" + empPost + '\'' +
                ", empRemarks='" + empRemarks + '\'' +
                ", empBir=" + empBir +
                '}';
    }
}
