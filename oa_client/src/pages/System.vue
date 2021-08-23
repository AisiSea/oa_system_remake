<template>
  <div class="system">
    <el-menu mode="vertical" :router=true :default-active="refreshActive" text-color="#FFF" active-text-color="#8CE3FC"
             background-color="#545C64" @select="setRefreshActive">
      <el-menu-item index="index">
        <i class="el-icon-s-home"></i>
        <span slot="title">系统首页</span>
      </el-menu-item>
      <el-submenu index="department">
        <template slot="title">
          <i class="el-icon-s-custom"></i>
          <span>人员管理</span>
        </template>
        <el-menu-item index="department">
          <i class="el-icon-office-building"></i>
          <span slot="title">部门管理</span>
        </el-menu-item>
        <el-menu-item index="employee">
          <i class="el-icon-user"></i>
          <span slot="title">员工管理</span>
        </el-menu-item>
        <el-menu-item index="account">
          <i class="el-icon-unlock"></i>
          <span slot="title">账号管理</span>
        </el-menu-item>
      </el-submenu>
    </el-menu>
    <router-view></router-view>
  </div>
</template>

<script>
import Method from '@/js/methods'

export default {
  name: "System",

  data() {
    return {
      refreshActive: sessionStorage.getItem("refreshActive")
    }
  },

  created() {
    Method.checkLogin(this);
  },

  methods: {
    setRefreshActive(index) {
      sessionStorage.setItem("refreshActive", index);
    }
  },
}
</script>

<style lang="less" scoped>
@import "~@/styles/size";
@import "~@/styles/color";

.system {
  display: flex;

  .el-menu {
    width: @leftBarWidth;
    height: @totalHeight;
    box-shadow: @boxShadowSetter;
  }
}

</style>