package com.aisisea.oa.system.employee.mapper;

import com.aisisea.oa.system.employee.domain.Employee;

import java.util.List;

public interface EmployeeMapper {

    List<Employee> selectAllEmployees();

}
