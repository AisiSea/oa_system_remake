package com.aisisea.oa.system.department.domain;

public class Department {
    /**
     * 	dept_id 	INT 	PRIMARY KEY 	AUTO_INCREMENT 	COMMENT '部门ID',
     * 	dept_name 	VARCHAR(63) 	NOT NULL	COMMENT '部门名称',
     * 	dept_phone 	VARCHAR(15)	COMMENT '部门电话',
     * 	dept_parent 	INT		COMMENT '父级部门ID',
     * 	dept_local 	VARCHAR(255)	COMMENT '部门地址',
     * 	dept_remarks 	VARCHAR(255)	COMMENT '部门备注'
     */

    private int deptId;
    private String deptName, deptPhone, deptLocal, deptRemarks, deptParent;

    public Department() {
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDeptParent() {
        return deptParent;
    }

    public void setDeptParent(String deptParent) {
        this.deptParent = deptParent;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptPhone() {
        return deptPhone;
    }

    public void setDeptPhone(String deptPhone) {
        this.deptPhone = deptPhone;
    }

    public String getDeptLocal() {
        return deptLocal;
    }

    public void setDeptLocal(String deptLocal) {
        this.deptLocal = deptLocal;
    }

    public String getDeptRemarks() {
        return deptRemarks;
    }

    public void setDeptRemarks(String deptRemarks) {
        this.deptRemarks = deptRemarks;
    }

    @Override
    public String toString() {
        return "Department{" +
                "deptId=" + deptId +
                ", deptParent=" + deptParent +
                ", deptName='" + deptName + '\'' +
                ", deptPhone='" + deptPhone + '\'' +
                ", deptLocal='" + deptLocal + '\'' +
                ", deptRemarks='" + deptRemarks + '\'' +
                '}';
    }
}
