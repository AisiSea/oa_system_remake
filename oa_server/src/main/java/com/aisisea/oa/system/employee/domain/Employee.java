package com.aisisea.oa.system.employee.domain;

public class Employee {

    int empId;
    String empName;
    String empSex;
    String empBir;
    int idId;
    String idNum;
    String empPhone;
    String empEmail;
    String deptId;
    String empPost;
    String empRemarks;

    public Employee() {
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpSex() {
        return empSex;
    }

    public void setEmpSex(String empSex) {
        this.empSex = empSex;
    }

    public String getEmpBir() {
        return empBir;
    }

    public void setEmpBir(String empBir) {
        this.empBir = empBir;
    }

    public int getIdId() {
        return idId;
    }

    public void setIdId(int idId) {
        this.idId = idId;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
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

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", empSex='" + empSex + '\'' +
                ", empBir='" + empBir + '\'' +
                ", idId=" + idId +
                ", idNum='" + idNum + '\'' +
                ", empPhone='" + empPhone + '\'' +
                ", empEmail='" + empEmail + '\'' +
                ", deptId='" + deptId + '\'' +
                ", empPost='" + empPost + '\'' +
                ", empRemarks='" + empRemarks + '\'' +
                '}';
    }
}
