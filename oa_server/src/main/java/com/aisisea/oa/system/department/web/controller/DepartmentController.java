package com.aisisea.oa.system.department.web.controller;

import com.aisisea.oa.commons.contants.KeyNameConstants;
import com.aisisea.oa.commons.utils.DefaultReturnObject;
import com.aisisea.oa.system.department.domain.Department;
import com.aisisea.oa.system.department.service.DepartmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @ResponseBody
    @RequestMapping(value = "/system/dept/get", method = RequestMethod.POST)
    public Object queryDepartmentsByKeyAndPage(@RequestBody Map<String, Object> map) {
        if (!checkOffsetAndCount(map))
            return DefaultReturnObject.getErrorParamReturnObject(null);

        Integer offset = (Integer) map.get("offset");
        Integer count = (Integer) map.get("count");

        PageHelper.startPage(offset, count);
        List<Department> departments = departmentService.queryDepartmentsByKey(map);
        PageInfo pageInfo = new PageInfo(departments);
        long total = pageInfo.getTotal();

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("departments", departments);
        returnMap.put("total", total);
        return DefaultReturnObject.getSuccessReturnObject(returnMap);
    }

    private boolean checkOffsetAndCount(Map<String, Object> map) {
        String index = (String) map.get("keyName");
        if (index != null && !index.equals("")) {
            try {
                int i = Integer.parseInt(index);
                if (i >= 0 && i < KeyNameConstants.deptKeyName.length)
                    map.put("keyName", KeyNameConstants.deptKeyName[i]);
                else return false;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return false;
            }
        }

        Integer offset = (Integer) map.get("offset");
        Integer count = (Integer) map.get("count");

        if (count == null || offset == null)
            return false;
        else if (offset < 0 || count <= 0)
            return false;
        return true;
    }

    @ResponseBody
    @RequestMapping(value = "/system/dept/get/names", method = RequestMethod.POST)
    public Object queryDepartmentsNames() {
        List<Map<String, Object>> deptNames = departmentService.queryDepartmentsNames();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("deptNames", deptNames);
        return DefaultReturnObject.getSuccessReturnObject(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "/system/dept/post", method = RequestMethod.POST)
    public Object saveDepartments(@RequestBody List<Department> departments) {
        if (!checkDepartments(departments))
            return DefaultReturnObject.getErrorParamReturnObject(null);
        int count = departmentService.saveDepartments(departments);
        if (count > 0) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("count", count);
            return DefaultReturnObject.getSuccessReturnObject(resultMap);
        }
        else return DefaultReturnObject.getErrorParamReturnObject(null);
    }

    private boolean checkDepartments(List<Department> departments) {
        List<String> deptNames = new ArrayList<>();
        for (int i = 0; i < departments.size(); i++) {
            Department department = departments.get(i);
            String deptParent = department.getDeptParent();
            if (deptParent == null || deptParent.equals(""))
                department.setDeptParent("1000");
            String deptName = department.getDeptName();
            if (deptName == null || deptName.equals(""))
                return false;
            else deptNames.add(deptName);
        }

        int count = departmentService.queryDepartmentsIsExist(deptNames);
        if (count > 0)
            return false;

        return true;
    }

    @ResponseBody
    @RequestMapping(value = "/system/dept/get/exist", method = RequestMethod.POST)
    public Object queryDepartmentsIsExist(@RequestBody Map<String, Object> map) {
        String deptName = (String) map.get("deptName");
        if (deptName == null || deptName.equals(""))
            return DefaultReturnObject.getErrorParamReturnObject(null);

        List<String> deptNames = new ArrayList<>();
        deptNames.add(deptName);
        int count = departmentService.queryDepartmentsIsExist(deptNames);

        Map<String, Object> resultMap = new HashMap<>();
        if (count > 0)
            resultMap.put("isExist", true);
        else resultMap.put("isExist", false);
        return DefaultReturnObject.getSuccessReturnObject(resultMap);
    }

}
