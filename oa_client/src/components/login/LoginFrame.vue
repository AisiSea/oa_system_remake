<template>
  <div class="login-frame">
    <div class="login-title">
      <span>登录 </span>
      <span class="login-subtitle">爱思海管理系统</span>
    </div>
    <el-form :model="account" :rules="acRules" ref="loginRef">
      <el-form-item prop="username">
        <el-input
          placeholder="账户名称"
          v-model="account.username"
          @blur="clearLoginMsg"
        ></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          placeholder="账户密码"
          v-model="account.password"
          @blur="clearLoginMsg"
          show-password
        ></el-input>
        <div class="el-form-item__error login-msg">{{ loginMsg }}</div>
      </el-form-item>
      <div class="login-last">
        <el-checkbox
          label="3天内免登陆"
          v-model="account.loginKeep"
        ></el-checkbox>
        <el-button type="primary" size="medium" v-on:click="loginBtnClick"
          >登入</el-button
        >
      </div>
    </el-form>
  </div>
</template>

<script>
import {mapActions, mapState} from "vuex";

export default {
  name: "LoginFrame",

  computed: {
    ...mapState(["loginMsg"]),
  },

  data() {
    return {
      account: {
        username: "",
        password: "",
        loginKeep: true,
      },

      acRules: {
        username: [
          { required: true, message: "要输入用户名呀!", trigger: "blur" },
          {
            min: 3,
            max: 16,
            message: "长度在 3 到 16 个字符",
            trigger: "blur",
          },
        ],
        password: [
          { required: true, message: "喵，密码不见了！", trigger: "blur" },
          {
            min: 5,
            max: 16,
            message: "长度在 5 到 16 个字符",
            trigger: "blur",
          },
        ],
      },
    };
  },

  created() {
    let token = localStorage.getItem("token");
    if (token === null || token === "") {
      token = sessionStorage.getItem("token");
      if (token === null || token === "") return;
    }
    this.axios({
      method: "POST",
      url: "/token",
      data: {
        token: token,
      },
    }).then((res) => {
      if (res.data.state === this.$store.state.SUCCESS_RESPONSE_STATE)
        this.$router.push("/System");
    });
  },

  methods: {
    ...mapActions(["clearLoginMsg"]),

    loginBtnClick() {
      this.$refs.loginRef.validate((valid) => {
        if (valid) {
          this.axios.post('/login',{
              acUsername: this.account.username,
              acPassword: this.account.password,
              loginKeep: this.account.loginKeep,
          })
            .then((res) => {
              if (res.data.state === this.$store.state.SUCCESS_RESPONSE_STATE) {
                let token = res.data.data.token;
                if (token !== undefined && token !== null && token !== "") {
                  if (this.account.loginKeep) {
                    localStorage.setItem("token", token);
                  } else sessionStorage.setItem("token", token);
                  this.$router.push("/System");
                } else this.$store.dispatch("setLoginMsg", "服务器错误");
              } else if (
                res.data.state === this.$store.state.PARAM_ERROR_RESPONSE_STATE
              ) {
                this.$store.dispatch("setLoginMsg", "用户名或密码错误");
              } else this.$store.dispatch("setLoginMsg", "未知错误");
            })
            .catch((error) => {
              console.log(error);
              this.$store.dispatch("setLoginMsg", "系统错误");
            });
        } else return false;
      });
    },
  },
};
</script>

<style lang="less" scoped>
@import "~@/styles/size.less";
@import "~@/styles/color.less";

.login-frame {
  width: 350px;
  padding: 40px 50px;

  color: @primaryTextColor;
  background-color: @basicWhiteColor;
  box-shadow: @boxShadowSetter;

  left: 50%;
  top: 50%;
  transform: translateX(-50%) translateY(-50%);
  position: absolute;

  .login-title {
    font-size: @fontExtraLarge;
    font-weight: bold;

    display: flex;
    align-items: baseline;
    justify-content: space-between;
  }

  .login-subtitle {
    font-size: @fontExtraSmall;
    font-weight: normal;
  }

  > div {
    margin: @rowMargin;
  }

  .login-last {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .login-msg {
    font-size: @fontExtraSmall;
  }
}
</style>