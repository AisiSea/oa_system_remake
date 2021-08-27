package com.aisisea.oa.system.department.web.controller;

import com.aisisea.oa.commons.contants.KeyNameConstants;
import com.aisisea.oa.commons.contants.ParamTranslationMap;
import com.aisisea.oa.commons.utils.AsListUtils;
import com.aisisea.oa.commons.utils.DefaultReturnObject;
import com.aisisea.oa.system.department.domain.Department;
import com.aisisea.oa.system.department.service.DepartmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.*;

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
        String keyName = (String) map.get("keyName");
        if (keyName != null && !keyName.equals("")) {
            try {
                int i = Integer.parseInt(keyName);
                if (i >= 0 && i < KeyNameConstants.deptKeyName.length)
                    map.put("keyName", KeyNameConstants.deptKeyName[i]);
                else return false;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return false;
            }
        }

        String order = (String) map.get("orderTy");
        if (order == null || order.equals(""))
            map.put("orderTy", "desc");
        else {
            order = order.toLowerCase();
            if (!order.equals("asc") && !order.equals("desc"))
                map.put("orderTy", "desc");
        }

        String orderCol = (String) map.get("orderCol");
        if (orderCol != null && !orderCol.equals("")) {
            try {
                int i = Integer.parseInt(orderCol);
                if (i >= 0 && i < KeyNameConstants.deptKeyName.length)
                    map.put("orderCol", KeyNameConstants.deptKeyName[i]);
                else map.put("orderCol", KeyNameConstants.deptKeyName[0]);
            } catch (NumberFormatException e) { e.printStackTrace(); }
        } else map.put("orderCol", KeyNameConstants.deptKeyName[0]);

        Integer offset = (Integer) map.get("offset");
        Integer count = (Integer) map.get("count");

        if (count == null || offset == null)
            return false;
        else if (offset <= 0 || count <= 0)
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
        if (departments == null)
            return false;
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

    @ResponseBody
    @RequestMapping(value = "/system/dept/del", method = RequestMethod.POST)
    public Object deleteDepartments(@RequestBody List<String> deptIds) {
        if (deptIds.size() <= 0)
            return DefaultReturnObject.getErrorParamReturnObject(null);
        if (departmentService.queryChildDepartmentCount(deptIds) > 0)
            return DefaultReturnObject.getErrorParamReturnObject("选中部门存在子部门，删除失败", null);

        int count = departmentService.deleteDepartments(deptIds);
        if (count > 0) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("count", count);
            return DefaultReturnObject.getSuccessReturnObject(resultMap);
        }
        return DefaultReturnObject.getErrorParamReturnObject(null);
    }

    @ResponseBody
    @RequestMapping(value = "/system/dept/put", method = RequestMethod.POST)
    public Object editDepartment(@RequestBody Department department) {
        if (!checkDepartment(department))
            return DefaultReturnObject.getErrorParamReturnObject(null);
        String grandparentId = departmentService.queryDepartmentParentId(department.getDeptParent());
        String deptId = String.valueOf(department.getDeptId());
        if (deptId.equals(grandparentId))
            return DefaultReturnObject.getErrorParamReturnObject("不能互为父级部门，保存失败", null);

        int count = departmentService.editDepartment(department);
        if (count > 0) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("count", count);
            return DefaultReturnObject.getSuccessReturnObject(resultMap);
        }
        return DefaultReturnObject.getErrorParamReturnObject(null);
    }

    public boolean checkDepartment(Department department) {
        if (department == null || department.getDeptId() <= 1000)
            return false;

        try {
            String parent = department.getDeptParent();
            if (parent == null || parent.equals(""))
                return false;
            int parentId = Integer.parseInt(parent);
            if (parentId < 1000)
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @RequestMapping(value="/system/dept/get/excel/keys", method = RequestMethod.POST)
    public void queryDepartmentsToExcel(@RequestBody Department department, HttpServletResponse response) {
        List<Department> list = departmentService.queryDepartmentsByKeys(department);
        createExcel(list, response);
    }

    @RequestMapping(value = "/system/dept/get/excel/ids", method = RequestMethod.POST)
    public void queryDepartmentsToExcel(@RequestBody List<String> deptIds, HttpServletResponse response) {
        List<Department> list;
        if (deptIds != null && deptIds.size() > 0)
            list = departmentService.queryDepartmentsByIds(deptIds);
        else list = departmentService.queryDepartmentsByIds(null);
        createExcel(list, response);
    }

    public void createExcel(List<Department> list, HttpServletResponse response) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("部门导出表");

        Field[] fields = Department.class.getDeclaredFields();
        List<Field> fieldList = AsListUtils.asList(fields);
        XSSFRow titleRow = sheet.createRow(0);

        int columnIndex = 0;
        Iterator<Field> iterator = fieldList.iterator();
        while (iterator.hasNext()) {
            String name = iterator.next().getName();
            if (name.equals("deptId"))
                iterator.remove();
            else {
                sheet.setColumnWidth(columnIndex, 5000);
                XSSFCell cell = titleRow.createCell(columnIndex);
                cell.setCellValue(ParamTranslationMap.DEPT.get(name));
                columnIndex++;
            }
        }

        OutputStream outputStream = null;
        try {
            for (int i = 0; i < list.size(); i++) {
                XSSFRow row = sheet.createRow(i + 1);
                for (int n = 0; n < fieldList.size(); n++) {
                    Field field = fieldList.get(n);
                    field.setAccessible(true);
                    String value = (String) field.get(list.get(i));
                    XSSFCell cell = row.createCell(n);
                    cell.setCellValue(value);
                }
            }

            response.setContentType("application/octet-stream;charset=UTF-8");
            String fileName = URLEncoder.encode("部门导出表.xlsx", "UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null)
                    outputStream.close();
                workbook.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
