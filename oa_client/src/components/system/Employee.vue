<template>
  <div>
    <h1>EMPLOYEE</h1>
  </div>
</template>

<script>
import Method from '@/js/methods';

export default {
  name: "Employee",

  data() {
    return {

    }
  },

  created() {
    Method.checkLogin(this);
  }
}
</script>

<style scoped>

</style>