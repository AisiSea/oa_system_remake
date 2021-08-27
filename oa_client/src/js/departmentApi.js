let departmentApi = {
    department: {
        getDept: '/system/dept/get',
        getNames: '/system/dept/get/names',
        checkName: '/system/dept/get/exist',
        editDept: '/system/dept/put',
        delDept: '/system/dept/del',
        save: '/system/dept/post',
        getExcelByKeys: '/system/dept/get/excel/keys',
        getExcelByIds: '/system/dept/get/excel/ids'
    }
}
export default departmentApi;