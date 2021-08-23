package com.aisisea.oa.system.department.service.impl;

import com.aisisea.oa.system.department.domain.Department;
import com.aisisea.oa.system.department.mapper.DepartmentMapper;
import com.aisisea.oa.system.department.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public List<Department> queryDepartmentsByKey(Map<String, Object> map) {
        return departmentMapper.selectDepartmentsByKey(map);
    }

    @Override
    public List<Map<String, Object>> queryDepartmentsNames() {
        return departmentMapper.selectDepartmentsNames();
    }

    @Override
    public int saveDepartments(List<Department> departments) {
        return departmentMapper.insertDepartments(departments);
    }

    @Override
    public int queryDepartmentsIsExist(List<String> deptNames) {
        return departmentMapper.selectDepartmentsIsExist(deptNames);
    }
}
