package com.aisisea.oa.system.department.service;

import com.aisisea.oa.system.department.domain.Department;

import java.util.List;
import java.util.Map;

public interface DepartmentService {

    List<Department> queryDepartmentsByKey(Map<String, Object> map);

    List<Map<String, Object>> queryDepartmentsNames();

    int saveDepartments(List<Department> departments);

    int queryDepartmentsIsExist(List<String> deptNames);

    int deleteDepartments(List<String> deptIds);

}
