package com.aisisea.oa.login.domain;

public class Account {
    private String acUsername;
    private String acPassword;
    private String acAuthority;
    private String acRemarks;
    private int empId;

    public Account() {
    }

    public String getAcUsername() {
        return acUsername;
    }

    public void setAcUsername(String acUsername) {
        this.acUsername = acUsername;
    }

    public String getAcPassword() {
        return acPassword;
    }

    public void setAcPassword(String acPassword) {
        this.acPassword = acPassword;
    }

    public String getAcAuthority() {
        return acAuthority;
    }

    public void setAcAuthority(String acAuthority) {
        this.acAuthority = acAuthority;
    }

    public String getAcRemarks() {
        return acRemarks;
    }

    public void setAcRemarks(String acRemarks) {
        this.acRemarks = acRemarks;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "acUsername='" + acUsername + '\'' +
                ", acPassword='" + acPassword + '\'' +
                ", acAuthority='" + acAuthority + '\'' +
                ", acRemarks='" + acRemarks + '\'' +
                ", empId='" + empId + '\'' +
                '}';
    }
}
