<template>
  <div class="data">
    <div class="data-dialog">
      <el-dialog :title="singleTitle" :visible.sync="singleVisible" :close-on-click-modal="false"
                 :close-on-press-escape="false" :show-close="false" v-if="singleVisible" append-to-body>
      </el-dialog>
      <el-dialog title="批量添加员工" :visible.sync="batchVisible" :close-on-click-modal="false"
                 :close-on-press-escape="false" :show-close="false">
      </el-dialog>
    </div>
    <div class="data-op">
      <el-button type="primary">单个添加</el-button>
      <el-button type="primary">批量添加</el-button>
      <el-button type="danger">批量删除</el-button>
      <el-button type="success">刷新页面</el-button>
      <el-button>批量导出</el-button>
      <el-input class="data-search" v-model="valueInput" placeholder="支持模糊查询" size="small" clearable>
        <el-select class="search-select" v-model="searchName" slot="prepend">
          <el-option label="员工姓名" value="0"></el-option>
        </el-select>
        <el-button slot="append" icon="el-icon-search"></el-button>
      </el-input>
    </div>
    <div class="data-table">
      <el-table id="table" :data="tableData" :height=TABLE_DEFAULT_HEIGHT v-loading="loading" @selection-change="selectionChange"
                @row-dblclick="rowDblClick" @sort-change="sortChange" :default-sort="{}">
        <el-table-column prop="empName" label="员工姓名" sortable="custom" :sort-orders="sortOrders" :index="0" :width=TABLE_BASE_WIDTH></el-table-column>
        <el-table-column prop="" label="操作" :width=TABLE_MEDIUM_WIDTH>
          <template>
            <el-button type="primary">编辑</el-button>
            <el-button type="danger">删除</el-button>
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
import Method from '@/js/methods';
import {mapState} from "vuex";

export default {
  name: "Employee",
  computed: {
    ...mapState(['TABLE_DEFAULT_HEIGHT', 'TABLE_EXTRA_SMALL_WIDTH', 'TABLE_BASE_WIDTH', 'TABLE_MEDIUM_WIDTH', 'TABLE_EXTRA_LARGE_WIDTH']),
  },

  data() {
    return {
      singleTitle: '',
      singleVisible: false,
      batchVisible: false,
      valueInput: '',
      searchName: '0',
      tableData: [],
      sortOrders: ['ascending', 'descending'],
      pageSize: 12,
      dataCount: 0,
      currentPage: 1,


    }
  },

  created() {
    Method.checkLogin(this);
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
}


</style>