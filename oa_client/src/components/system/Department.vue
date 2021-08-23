<template>
  <div class="dept">
    <div class="dept-dialog">
      <el-dialog title="添加单个部门" :visible.sync="deptSingleVisible" :close-on-click-modal="false"
                 :close-on-press-escape="false" :show-close="false" v-if="deptSingleVisible" append-to-body>
        <SingleDept @deptSingleClose="deptSingleClose"></SingleDept>
      </el-dialog>
      <el-dialog title="批量添加部门" :visible.sync="deptBatchVisible" :close-on-click-modal="false"
                 :close-on-press-escape="false" :show-close="false">
        <BatchDept @deptBatchClose="deptBatchClose"></BatchDept>
      </el-dialog>
    </div>
    <div class="dept-op">
      <el-button type="primary" @click="deptSingleVisible = true">单个添加</el-button>
      <el-button type="primary" @click="deptBatchVisible = true">批量添加</el-button>
      <el-button type="success" @click="deptRefresh">刷新页面</el-button>
      <el-button type="danger">批量删除</el-button>
      <el-input class="dept-search" v-model="searchKey" placeholder="支持模糊查询" size="small" clearable>
        <el-select class="search-select" v-model="searchSelect" slot="prepend">
          <el-option label="部门名称" value="0"></el-option>
          <el-option label="联系方式" value="1"></el-option>
          <el-option label="父级部门" value="2"></el-option>
          <el-option label="部门地址" value="3"></el-option>
        </el-select>
        <el-button slot="append" @click="searchClick" icon="el-icon-search"></el-button>
      </el-input>
    </div>
    <div class="dept-table">
      <el-table id="table" :data="deptData" :height=TABLE_DEFAULT_HEIGHT  v-loading="loading">
        <el-table-column prop="deptName" label="部门名称" sortable :width=TABLE_BASE_WIDTH></el-table-column>
        <el-table-column prop="deptPhone" label="联系方式" sortable :width=TABLE_BASE_WIDTH></el-table-column>
        <el-table-column prop="deptParent" label="父级部门" sortable :width=TABLE_BASE_WIDTH></el-table-column>
        <el-table-column prop="deptLocal" label="部门地址" sortable :width=TABLE_EXTRA_LARGE_WIDTH></el-table-column>
        <el-table-column prop="" label="操作" :width=TABLE_MEDIUM_WIDTH>
          <template>
            <el-button type="primary">编辑</el-button>
            <el-button type="danger">删除</el-button>
          </template>
        </el-table-column>
        <el-table-column type="selection" :width=TABLE_EXTRA_SMALL_WIDTH></el-table-column>
      </el-table>
    </div>
    <div class="dept-page">
      <el-pagination layout="sizes, prev, pager, next, total, jumper" :page-sizes="[12, 25, 50]"
                     :page-size="pageSize" :total="deptDataCount" @size-change="sizeChange"></el-pagination>
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
      deptDataCount: 0,
      searchKey: '',
      searchSelect: '0',
      deptSingleVisible: false,
      deptBatchVisible: false,
      pageSize: 12
    }
  },

  created() {
    Method.checkLogin(this);
  },

  mounted() {
    this.getDepartments();
  },

  methods: {
    getDepartments(keyName, keyValue) {
      this.axios({
        url: departmentApi.department.getDept,
        method: 'POST',
        data: {
          offset: 0,
          count: this.pageSize,
          keyName: keyName,
          keyValue: keyValue
        }
      }).then(res => {
        if (res.data.state === this.$store.state.SUCCESS_RESPONSE_STATE) {
          this.deptData = res.data.data.departments;
          this.deptDataCount = res.data.data.total;
          this.loading = false;
        }
      });
    },

    sizeChange(size) {
      this.pageSize = size;
      this.getDepartments(this.searchSelect, this.searchKey);
    },

    deptRefresh() {
      this.$router.go(0);
    },

    searchClick() {
      this.getDepartments(this.searchSelect, this.searchKey);
    },

    deptSingleClose() {
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