<template>
  <div class="addSingleDept">
    <el-divider></el-divider>
    <el-form :model="department" :inline="true" :rules="deptRules" ref="singleDeptRef" label-width="100px">
      <el-form-item prop="deptName" label="部门名称">
        <el-input size="small" placeholder="2 到 30 个字符" v-model="department.deptName"></el-input>
      </el-form-item>
      <el-form-item prop="deptPhone" label="联系方式">
        <el-input size="small" placeholder="选填 | 4 到 31 个字符" v-model="department.deptPhone"></el-input>
      </el-form-item>
      <el-form-item prop="deptLocal" label="部门地址">
        <el-input size="small" placeholder="选填 | 100 个字符以内" v-model="department.deptLocal"></el-input>
      </el-form-item>
      <el-form-item label="父级部门" prop="deptParent">
        <el-select v-model="department.deptParent" size="small" class="parent-select" placeholder="搜索" filterable>
          <el-option v-for="item in deptNames" :label="item.deptName" :key="item.deptId"
                     :value="item.deptId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="deptRemark" label="备注">
        <el-input type="textarea" size="small" :autosize="{ minRows: 3 }" maxlength="100" show-word-limit
                  v-model="department.deptRemarks" placeholder="选填 | 100 个字符以内"></el-input>
      </el-form-item>
    </el-form>
    <el-divider></el-divider>
    <div class="add-op">
      <el-popover placement="top" v-model="visible">
        <div class="el-popover-msg">确定取消？已输入的内容将不会保存</div>
        <div class="set-button-right">
          <el-button type="text" size="mini" @click="visible = false">取消</el-button>
          <el-button type="primary" size="mini" @click="cancelClick">确定</el-button>
        </div>
        <el-button type="warning" slot="reference">取消</el-button>
      </el-popover>
      <el-button type="primary" @click="saveClick">保存</el-button>
    </div>
  </div>
</template>

<script>
import departmentApi from "@/js/departmentApi";

export default {
  name: "SingleDept",

  data() {
    let checkDeptName = (rule, value, callback) => {
      this.axios({
        method: 'POST',
        url: departmentApi.department.checkName,
        async: false,
        data: {
          deptName: value
        }
      }).then(res => {
        if (res.data.state === this.$store.state.SUCCESS_RESPONSE_STATE) {
          if (res.data.data.isExist === true)
            callback(new Error('部门名已存在'));
          else callback();
        }
      });
    };

    return {
      department: {
        // deptName: '上海销售部',
        // deptPhone: '30624770',
        deptName: '',
        deptPhone: '',
        deptParent: '',
        // deptLocal: '上海浦东区迪士尼度假区正门售票处',
        deptLocal: '',
        deptRemarks: '',
      },
      visible: false,

      deptNames: [],

      deptRules: {
        deptName: [
          {required: true, message: '部门名不能为空', trigger: 'blur'},
          {min: 2, max: 30, message: '长度在 2 到 30 个字符', trigger: 'blur'},
          {validator: checkDeptName, trigger: 'blur'}
        ],
        deptPhone: [
          {required: true, message: '联系方式不能为空', trigger: 'blur'},
          {pattern: /^(([0\\+]\d{2,4}-)?(0\d{2,3})-)?(\d{4,})(-(\d{3,}))?$/, trigger: 'blur', message: '联系方式长度或格式不正确'}
        ],
        deptParent: [
          {required: true, message: '父级部门不能为空', trigger: 'change'}
        ],
        deptLocal: [
          {min: 0, max: 100, message: '长度在 100 个字符以内', trigger: 'blur'}
        ],
      }
    };
  },

  mounted() {
    this.selectVisible();
  },

  methods: {
    cancelClick() {
      this.$emit('deptSingleClose', false);
    },

    closeSingleDept() {
      this.$emit('deptSingleClose', true);
    },

    saveClick() {
      this.$refs.singleDeptRef.validate((valid) => {
        if (valid) {
          let departments = [];
          departments.push(this.department);
          this.axios({
            method: 'POST',
            url: departmentApi.department.save,
            data: departments
          }).then((res) => {
            if (res.data.state === this.$store.state.SUCCESS_RESPONSE_STATE) {
              this.$message.success(res.data.msg);
              this.closeSingleDept();
            } else
              this.$message.error(res.data.msg);
          });
        } else return false;
      });
    },

    selectVisible() {
      this.axios({
        method: 'POST',
        url: departmentApi.department.getNames,
        data: {},
      }).then((res) => {
        if (res.data.state === this.$store.state.SUCCESS_RESPONSE_STATE) {
          this.deptNames = res.data.data.deptNames;
        }
      });
    }
  }

};
</script>

<style lang="less" scoped>
@import "~@/styles/size";

.addSingleDept {
  .el-input,
  .parent-select {
    width: 300px;
  }

  .el-textarea {
    width: 600px;
  }

  .add-op {
    display: flex;
    justify-content: flex-end;
  }

  .el-button {
    margin-left: 10px;
  }
}
</style>