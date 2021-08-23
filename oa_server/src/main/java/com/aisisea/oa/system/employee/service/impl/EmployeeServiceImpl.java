package com.aisisea.oa.system.employee.service.impl;

import com.aisisea.oa.system.employee.domain.Employee;
import com.aisisea.oa.system.employee.mapper.EmployeeMapper;
import com.aisisea.oa.system.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    @Override
    public List<Employee> queryAllEmployees() {
        return employeeMapper.selectAllEmployees();
    }
}
