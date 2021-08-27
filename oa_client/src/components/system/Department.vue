<template>
  <div class="data">
    <div class="data-dialog">
      <el-dialog :title="singleTitle" :visible.sync="singleVisible" :close-on-click-modal="false"
                 :close-on-press-escape="false" :show-close="false" v-if="singleVisible" append-to-body>
        <SingleDlg @singleClose="singleClose" :singleDlgType="singleDlgType" :sourceData="sourceData" :deptNames="deptNames"></SingleDlg>
      </el-dialog>
      <el-dialog title="批量添加部门" :visible.sync="batchVisible" :close-on-click-modal="false"
                 :close-on-press-escape="false" :show-close="false">
        <BatchDlg @batchClose="batchClose"></BatchDlg>
      </el-dialog>
      <el-dialog title="批量导出" :visible.sync="exportVisible" :close-on-click-modal="false"
                 :close-on-press-escape="false" :show-close="false">
        <ExportDlg @exportClose="exportClose" :deptNames="deptNames" :selection="selection"></ExportDlg>
      </el-dialog>
    </div>
    <div class="data-op">
      <el-button type="primary" @click="singleClick">单个添加</el-button>
      <el-button type="primary" @click="batchVisible = true">批量添加</el-button>
      <el-button type="danger" @click="batchDelClick">批量删除</el-button>
      <el-button type="success" @click="refreshClick">刷新页面</el-button>
      <el-button class="popover-button" slot="reference" @click="exportClick">批量导出</el-button>
      <el-input class="data-search" v-model="valueInput" placeholder="支持模糊查询" size="small" clearable>
        <el-select class="search-select" v-model="searchName" slot="prepend">
          <el-option label="部门名称" value="0"></el-option>
          <el-option label="联系方式" value="1"></el-option>
          <el-option label="父级部门" value="2"></el-option>
          <el-option label="部门地址" value="3"></el-option>
        </el-select>
        <el-button slot="append" @click="searchClick" icon="el-icon-search"></el-button>
      </el-input>
    </div>
    <div class="data-table">
      <el-table id="table" :data="tableData" :height=TABLE_DEFAULT_HEIGHT v-loading="loading" @selection-change="selectionChange"
                @row-dblclick="rowDblClick" @sort-change="sortChange" :default-sort="{prop: 'deptName', order: 'descending'}">
        <el-table-column prop="deptName" label="部门名称" sortable="custom" :sort-orders="sortOrders" :index="0" :width=TABLE_BASE_WIDTH></el-table-column>
        <el-table-column prop="deptPhone" label="联系方式" sortable="custom" :sort-orders="sortOrders" :index="1" :width=TABLE_BASE_WIDTH></el-table-column>
        <el-table-column prop="deptParent" label="父级部门" sortable="custom" :sort-orders="sortOrders" :index="2" :width=TABLE_BASE_WIDTH></el-table-column>
        <el-table-column prop="deptLocal" label="部门地址" sortable="custom" :sort-orders="sortOrders" :index="3" :width=TABLE_EXTRA_LARGE_WIDTH></el-table-column>
        <el-table-column prop="" label="操作" :width=TABLE_MEDIUM_WIDTH>
          <template slot-scope="scope">
            <el-button type="primary" @click="editClick(scope.$index)">编辑</el-button>
            <el-button type="danger" @click="deleteClick(scope.$index)">删除</el-button>
          </template>
        </el-table-column>
        <el-table-column type="selection" :width=TABLE_EXTRA_SMALL_WIDTH></el-table-column>
      </el-table>
    </div>
    <div class="data-page">
      <el-pagination layout="sizes, prev, pager, next, total, jumper" :page-sizes="[12, 25, 50]"
                     :page-size="pageSize" :total="dataCount" @size-change="sizeChange"
                     @current-change="currentChange" :current-page="currentPage"></el-pagination>
    </div>
  </div>
</template>

<script>
import Method from "@/js/methods";
import {mapState} from "vuex";
import SingleDlg from "@/components/system/Department/SingleDlg";
import BatchDlg from "@/components/system/Department/BatchDlg";
import ExportDlg from "@/components/system/Department/ExportDlg";
import departmentApi from "@/js/departmentApi";

export default {
  name: "Department",
  components: {ExportDlg, SingleDlg, BatchDlg},
  computed: {
    ...mapState(['TABLE_DEFAULT_HEIGHT', 'TABLE_EXTRA_SMALL_WIDTH', 'TABLE_BASE_WIDTH', 'TABLE_MEDIUM_WIDTH', 'TABLE_EXTRA_LARGE_WIDTH']),
  },

  data() {
    return {
      loading: true,
      tableData: [],
      dataCount: 1,
      valueInput: '',
      searchValue: '',
      searchName: '0',
      sortOrders: ['ascending', 'descending'],
      singleVisible: false,
      batchVisible: false,
      orderCol: '0',
      orderTy: 'desc',
      currentPage: 1,
      pageSize: 12,
      selection: null,
      singleDlgType: 0,
      singleTitle: '',
      sourceData: null,
      exportVisible: false,
      deptNames: [],
    }
  },

  created() {
    Method.checkLogin(this);
  },

  mounted() {
    this.getDepartments();
  },

  methods: {
    getDepartments(offset, keyName, keyValue, orderCol, orderTy) {
      if (offset === undefined || offset === null)
        offset = 1;
      else if (typeof offset !== typeof 'number')
        offset = Number.parseInt(offset);
      if (offset <= 1)
        offset = 1;

      this.axios({
        url: departmentApi.department.getDept,
        method: 'POST',
        data: {
          offset: Number.parseInt(offset),
          count: this.pageSize,
          keyName: keyName,
          keyValue: keyValue,
          orderCol: orderCol,
          orderTy: orderTy
        }
      }).then(res => {
        if (res.data.state === this.$store.state.SUCCESS_RESPONSE_STATE) {
          this.tableData = res.data.data.departments;
          this.dataCount = res.data.data.total;
          this.loading = false;
        } else this.$message.error(res.data.msg);
      });
    },

    deleteDepartments(deptIds) {
      if (!Array.isArray(deptIds))
        return;
      this.axios({
        url: departmentApi.department.delDept,
        method: 'POST',
        data: deptIds,
        async: false
      }).then(res => {
        if (res.data.state === this.$store.state.SUCCESS_RESPONSE_STATE) {
          this.$message.success(res.data.msg);
          this.getDepartments(this.currentPage, this.searchName, this.searchValue, this.orderCol, this.orderTy);
        } else this.$message.error(res.data.msg);
      });
    },

    getDepartmentsNames() {
      this.axios({
        method: 'POST',
        url: departmentApi.department.getNames,
        data: {},
        async: false
      }).then((res) => {
        if (res.data.state === this.$store.state.SUCCESS_RESPONSE_STATE) {
          this.deptNames = res.data.data.deptNames;
        } else this.$message.error(res.data.msg);
      });
    },

    sizeChange(size) {
      this.pageSize = size;
      this.getDepartments(1, this.searchName, this.searchValue);
    },

    currentChange(current) {
      this.getDepartments(current, this.searchName, this.searchValue);
      this.currentPage = current;
    },

    sortChange({ column, order }){
      this.orderCol = column.index.toString();
      this.orderTy = 'asc';
      if (order === 'descending')
        this.orderTy = 'desc';
      this.getDepartments(1, this.searchName, this.searchValue, this.orderCol, this.orderTy);
    },

    selectionChange(selection) {
      this.selection = selection;
    },

    singleClick() {
      this.getDepartmentsNames();
      this.singleTitle = '添加单个部门';
      this.singleDlgType = 0;
      this.sourceData = null;
      this.singleVisible = true;
    },

    editClick(index) {
      this.getDepartmentsNames();
      this.singleTitle = '编辑部门';
      this.singleDlgType = 1;
      this.sourceData = this.tableData[index];
      this.singleVisible = true;
    },

    refreshClick() {
      this.$router.go(0);
    },

    batchDelClick(){
      let deptIds = [];
      let deptNames = [];
      this.selection.forEach(function (item) {
        deptIds.push(item.deptId);
        deptNames.push('【 ' + item.deptName + ' 】');
      });
      if (confirm('确定要删除以下部门吗？该操作不可逆\n' + deptNames))
        this.deleteDepartments(deptIds);
    },

    searchClick() {
      this.searchValue = this.valueInput;
      this.getDepartments(1, this.searchName, this.searchValue);
    },

    rowDblClick(row) {
      this.getDepartmentsNames();
      this.singleTitle = '编辑部门';
      this.singleDlgType = 1;
      this.sourceData = row;
      this.singleVisible = true;
    },

    deleteClick(index){
      let deptName = this.tableData[index].deptName;
      if (confirm('确定要删除 【 ' + deptName + ' 】 吗？该操作不可逆'))
        this.deleteDepartments([this.tableData[index].deptId]);
    },

    exportClick() {
      this.getDepartmentsNames();
      this.exportVisible = true;
    },

    singleClose(isSuccess) {
      if (isSuccess)
        this.getDepartments(0);
      this.singleVisible = false;
    },

    batchClose() {
      this.batchVisible = false;
    },

    exportClose() {
      this.exportVisible = false;
    }
  }
}
</script>

<style lang="less" scoped>
@import "~@/styles/size";
@import "~@/styles/color";

.data {
  width: @totalWidth - @leftBarWidth;
  height: @totalHeight;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-evenly;

  .data-op {
    width: 1440px;

    .el-input {
      float: right;
    }
  }

  .data-search {
    width: 550px;

    .search-select {
      width: 110px;
    }
  }

  .popover-button {
    display: inline-block;
    margin-left: 10px;
  }
}


</style>