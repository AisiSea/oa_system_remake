package com.aisisea.oa.system.department.mapper;

import com.aisisea.oa.system.department.domain.Department;

import java.util.List;
import java.util.Map;

public interface DepartmentMapper {

    List<Department> selectDepartmentsByKey(Map<String, Object> map);

    List<Map<String, Object>> selectDepartmentsNames();

    int insertDepartments(List<Department> departments);

    int selectDepartmentsIsExist(List<String> deptNames);

    int deleteDepartments(List<String> deptIds);

    int editDepartment(Department department);
}
