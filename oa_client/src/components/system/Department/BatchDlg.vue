<template>
  <div>
    <el-divider></el-divider>
    <div class="add-op">
      <el-button type="warning" @click="cancelClick">取消</el-button>
      <el-button type="primary" @click="okClick">保存</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "BatchDept",

  data() {
    return {}
  },

  methods: {
    cancelClick() {
      this.$emit('batchClose');
    },

    okClick() {
      this.$emit('batchClose');
    }
  }
}
</script>

<style lang="less" scoped>
.add-op {
  display: flex;
  justify-content: flex-end;
}
</style>