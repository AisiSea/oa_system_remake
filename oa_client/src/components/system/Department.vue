<template>
  <div class="dept">
    <div class="dept-dialog">
      <el-dialog title="添加单个部门" :visible.sync="deptSingleVisible" :close-on-click-modal="false"
                 :close-on-press-escape="false" :show-close="false" v-if="deptSingleVisible" append-to-body>
        <SingleDept @deptSingleClose="deptSingleClose" :singleDeptType="singleDeptType"></SingleDept>
      </el-dialog>
      <el-dialog title="批量添加部门" :visible.sync="deptBatchVisible" :close-on-click-modal="false"
                 :close-on-press-escape="false" :show-close="false">
        <BatchDept @deptBatchClose="deptBatchClose"></BatchDept>
      </el-dialog>
    </div>
    <div class="dept-op">
      <el-button type="primary" @click="deptSingleVisible = true">单个添加</el-button>
      <el-button type="primary" @click="deptBatchVisible = true">批量添加</el-button>
      <el-button type="danger" @click="batchDelClick">批量删除</el-button>
      <el-button type="success" @click="refreshClick">刷新页面</el-button>
      <el-button @click="refreshClick">批量导入</el-button>
      <el-button @click="refreshClick">批量导出</el-button>
      <el-input class="dept-search" v-model="valueInput" placeholder="支持模糊查询" size="small" clearable>
        <el-select class="search-select" v-model="searchName" slot="prepend">
          <el-option label="部门名称" value="0"></el-option>
          <el-option label="联系方式" value="1"></el-option>
          <el-option label="父级部门" value="2"></el-option>
          <el-option label="部门地址" value="3"></el-option>
        </el-select>
        <el-button slot="append" @click="searchClick" icon="el-icon-search"></el-button>
      </el-input>
    </div>
    <div class="dept-table">
      <el-table id="table" :data="deptData" :height=TABLE_DEFAULT_HEIGHT v-loading="loading" @selection-change="selectionChange"
                @row-dblclick="rowDblClick" @sort-change="sortChange" :default-sort="{prop: 'deptName', order: 'descending'}">
        <el-table-column prop="deptName" label="部门名称" sortable="custom" :sort-orders="sortOrders" :index="0" :width=TABLE_BASE_WIDTH></el-table-column>
        <el-table-column prop="deptPhone" label="联系方式" sortable="custom" :sort-orders="sortOrders" :index="1" :width=TABLE_BASE_WIDTH></el-table-column>
        <el-table-column prop="deptParent" label="父级部门" sortable="custom" :sort-orders="sortOrders" :index="2" :width=TABLE_BASE_WIDTH></el-table-column>
        <el-table-column prop="deptLocal" label="部门地址" sortable="custom" :sort-orders="sortOrders" :index="3" :width=TABLE_EXTRA_LARGE_WIDTH></el-table-column>
        <el-table-column prop="" label="操作" :width=TABLE_MEDIUM_WIDTH>
          <template slot-scope="scope">
            <el-button type="primary">编辑</el-button>
            <el-button type="danger" size="mini" @click="deleteClick(scope.$index)">删除</el-button>
          </template>
        </el-table-column>
        <el-table-column type="selection" :width=TABLE_EXTRA_SMALL_WIDTH></el-table-column>
      </el-table>
    </div>
    <div class="dept-page">
      <el-pagination layout="sizes, prev, pager, next, total, jumper" :page-sizes="[12, 25, 50]"
                     :page-size="pageSize" :total="deptDataCount" @size-change="sizeChange"
                     @current-change="currentChange" :current-page="currentPage"></el-pagination>
    </div>
  </div>
</template>

<script>
import Method from "@/js/methods";
import {mapState} from "vuex";
import SingleDept from "@/components/system/Department/SingleDept";
import BatchDept from "@/components/system/Department/BatchDept";
import departmentApi from "@/js/departmentApi";

export default {
  name: "Department",
  components: {SingleDept, BatchDept},
  computed: {
    ...mapState(['TABLE_DEFAULT_HEIGHT', 'TABLE_EXTRA_SMALL_WIDTH', 'TABLE_BASE_WIDTH', 'TABLE_MEDIUM_WIDTH', 'TABLE_EXTRA_LARGE_WIDTH']),
  },

  data() {
    return {
      loading: true,
      deptData: [],
      deptDataCount: 1,
      valueInput: '',
      searchValue: '',
      searchName: '0',
      sortOrders: ['ascending', 'descending'],
      deptSingleVisible: false,
      deptBatchVisible: false,
      deptDelVisible: false,
      orderCol: '0',
      orderTy: 'desc',
      currentPage: 1,
      pageSize: 12,
      selection: null,
      singleDeptType: 1024
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
          this.deptData = res.data.data.departments;
          this.deptDataCount = res.data.data.total;
          this.loading = false;
        }
      });
    },

    deleteDepartments(deptIds) {
      if (!Array.isArray(deptIds))
        return;
      this.axios({
        url: departmentApi.department.delDept,
        method: 'POST',
        data: deptIds
      }).then(res => {
        if (res.data.state === this.$store.state.SUCCESS_RESPONSE_STATE) {
          this.$message.success(res.data.msg);
          this.getDepartments(this.currentPage, this.searchName, this.searchValue, this.orderCol, this.orderTy);
        }
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

    rowDblClick(row, column, event) {
      console.log('row: ' + row);
      console.log('column: ' + column);
      console.log('event: ' + event);
    },

    deleteClick(index){
      let deptName = this.deptData[index].deptName;
      if (confirm('确定要删除 【 ' + deptName + ' 】 吗？该操作不可逆'))
        this.deleteDepartments([this.deptData[index].deptId]);
    },

    deptSingleClose(isSuccess) {
      if (isSuccess)
        this.getDepartments(0);
      this.deptSingleVisible = false;
    },

    deptBatchClose() {
      this.deptBatchVisible = false;
    }
  }
}
</script>

<style lang="less" scoped>
@import "~@/styles/size";
@import "~@/styles/color";

.dept {
  width: @totalWidth - @leftBarWidth;
  height: @totalHeight;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-evenly;

  .dept-op {
    width: 1440px;

    .el-input {
      float: right;
    }
  }

  .dept-search {
    width: 550px;

    .search-select {
      width: 110px;
    }
  }
}


</style>