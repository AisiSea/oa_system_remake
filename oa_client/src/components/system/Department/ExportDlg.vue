<template>
  <div>
    <el-divider></el-divider>
    <div class="box">
      <el-radio v-model="exportType" :label=true>导出选中内容</el-radio>
      <el-radio v-model="exportType" :label=false>自定义导出内容</el-radio>
    </div>
    <div class="box-form">
      <el-form>
        <el-form-item :model="form" label="部门名称">
          <el-input class="data-search" v-model="form.deptName" placeholder="支持模糊查询" size="small" clearable :disabled="exportType"></el-input>
        </el-form-item>
        <el-form-item label="父级部门">
          <el-select v-model="form.deptParent" size="small" class="data-search" placeholder="搜索" filterable :disabled="exportType">
            <el-option v-for="item in deptNames" :label="item.deptName" :key="item.deptId"
                       :value="item.deptId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="部门地址">
          <el-input class="data-search" v-model="form.deptLocal" placeholder="支持模糊查询" size="small" clearable :disabled="exportType"></el-input>
        </el-form-item>
      </el-form>
    </div>
    <el-divider></el-divider>
    <div class="dlg-op">
      <el-button type="warning" @click="cancelClick">取消</el-button>
      <el-button type="primary" @click="okClick">导出</el-button>
    </div>
  </div>
</template>

<script>
import departmentApi from "@/js/departmentApi";
import Method from "@/js/methods";

export default {
  name: "ExportDlg",

  props: ['selection', 'deptNames'],

  data() {
    return {
      form: {
        deptName: '',
        deptParent: '',
        deptLocal: '',
      },

      exportType: true,
    }
  },

  mounted() {

  },

  methods: {
    cancelClick() {
      this.$emit('exportClose');
    },

    okClick() {
      let url = '';
      let data;
      if (this.exportType) {
        if (this.selection === undefined || this.selection === null || this.selection.length <= 0) {
          this.$message.error("未选择任何内容！");
          return;
        } else {
          data = [];
          this.selection.forEach(item => {
            data.push(item.deptId);
          });
        }
        url = departmentApi.department.getExcelByIds;
      } else {
        url = departmentApi.department.getExcelByKeys;
        data = this.form
      }

      this.axios({
        url: url,
        method: 'POST',
        data: data,
        responseType: 'blob',
        async: false
      }).then(res => {
        if (res.status !== 200)
          this.$message.error("系统错误");
        Method.downloadFile(res);
        this.$emit('exportClose');
      });
    },
  }
}
</script>

<style lang="less" scoped>

.box {
  margin: 20px 10px;
}

.box-form {
  margin: 20px 35px;
}

.data-search {
  width: 600px;

  .search-select {
    width: 110px;
  }
}

.dlg-op {
  display: flex;
  justify-content: flex-end;
}
</style>