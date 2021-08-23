<template>
  <div>
    <div class="login-background"></div>
    <LoginFrame/>
  </div>
</template>

<script>
import LoginFrame from "@/components/login/LoginFrame";

export default {
  name: "Login",
  components: {LoginFrame}
}
</script>

<style lang="less" scoped>
.login-background {
  height: 100%;
  width: 100%;

  background-image: url("~@/assets/img/login/LoginBackground.jpg");
  background-size: cover;
  background-repeat: no-repeat;
  background-attachment: scroll;

  z-index: -1;
  position: absolute;
}
</style>