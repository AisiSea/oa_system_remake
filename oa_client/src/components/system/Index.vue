<template>
  <div>
    <h1>INDEX</h1>
  </div>
</template>

<script>
import Method from "@/js/methods";

export default {
  name: "Index",

  created() {
    Method.checkLogin(this);
  }
}
</script>

<style scoped>

</style>