package com.aisisea.oa.system.employee.web.controller;

import com.aisisea.oa.commons.domain.ReturnObject;
import com.aisisea.oa.commons.utils.DefaultReturnObject;
import com.aisisea.oa.system.employee.domain.Employee;
import com.aisisea.oa.system.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @ResponseBody
    @RequestMapping(value = "/system/employee", method = RequestMethod.GET)
    public Object employee() {
        List<Employee> employees = employeeService.queryAllEmployees();

        Map<String, Object> map = new HashMap<>();
        map.put("employees", employees);

        ReturnObject returnObject = DefaultReturnObject.getSuccessReturnObject(map);
        return returnObject;
    }

}
