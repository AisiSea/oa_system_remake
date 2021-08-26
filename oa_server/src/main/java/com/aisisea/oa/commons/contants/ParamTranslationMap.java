package com.aisisea.oa.commons.contants;

import java.util.HashMap;
import java.util.Map;

public class ParamTranslationMap {

    public static final Map<String, String> DEPT = new HashMap<>();

    static {
        /** Department 参数转换 **/
        DEPT.put("deptId", "系统编号");
        DEPT.put("deptName", "部门名称");
        DEPT.put("deptPhone", "联系方式");
        DEPT.put("deptLocal", "部门地址");
        DEPT.put("deptParent", "父级部门");
        DEPT.put("deptRemarks", "部门备注");
    }

}
