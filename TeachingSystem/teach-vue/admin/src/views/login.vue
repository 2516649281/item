<template>
  <div>
    <video autoplay loop muted class="login-fillWidth" v-on:canplay="canplay">
      <source src="../image/login.mp4" type="video/mp4"/>
    </video>
  </div>
  <el-container>
    <el-main>
      <el-form
          @keyup.enter.native="login(loginForm)"
          :model="loginForm"
          :rules="rules"
          :inline-message="true"
          ref="loginFrom"
      >
        <h1>欢迎登录</h1>
        <el-form-item prop="userName">
          <el-input
              placeholder="请输入账号"
              v-model="loginForm.userName"
          ></el-input>
        </el-form-item>
        <el-form-item prop="userPassword">
          <el-input
              placeholder="请输入密码"
              v-model="loginForm.userPassword"
              show-password
          ></el-input>
        </el-form-item>
        <el-form-item prop="code">
          <el-input placeholder="请输入验证码" v-model="loginForm.code"
          >
            <template v-slot:prefix><i class="el-icon-s-check"></i></template
            >
            <template v-slot:suffix
            >
              <s-identify
                  :identifyCode="identifyCode"
                  @click="refreshCode"
              ></s-identify
              >
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button round @click="toRegister">注册</el-button>
          <el-button @click="login(loginForm)" round>登录</el-button>
        </el-form-item>
      </el-form>
    </el-main>
  </el-container>
</template>

<script>
import {login} from "../api/user";
import SIdentify from "../components/identify";

export default {
  components: {
    SIdentify: SIdentify,
  },
  data() {
    return {
      loginForm: {
        //账号
        userName: "",
        //用户密码
        userPassword: "",
        //验证码
        code: "",
      },
      //随机数仓库
      identifyCodes: [1, 2, 3, 4, 5, 6, 7, 8, 9, 0],
      //验证码
      identifyCode: "",
      //表单验证
      rules: {
        userName: [
          {required: true, message: "用户名不得为空!", trigger: "blur"},
        ],
        userPassword: [
          {required: true, message: "密码不得为空!", trigger: "blur"},
        ],
        code: [{required: true, message: "验证码不得为空!", trigger: "blur"}],
      },
    };
  },
  created() {
    this.refreshCode();
  },
  methods: {
    //登录逻辑
    login(obj) {
      this.$refs["loginFrom"].validate((valid) => {
        if (valid) {
          login(obj).then((req) => {
            if ((req.data.statue === 200) & (req.data.data != null)) {
              if (req.data.data.userIdentity != 2) {
                this.$message({
                  type: "warning",
                  message: "该账号不是管理员账号，请使用管理员账号登录!",
                  showClose: true,
                });
                this.refreshCode();
                return;
              }
              this.$message({
                type: "success",
                message: "登录成功!",
                showClose: true,
              });
              sessionStorage.setItem("token", req.data.data.token);
              this.$router.push({
                name: "public",
              });
            } else {
              this.refreshCode();
              this.$message({
                type: "error",
                message:
                    req.data.message === null
                        ? "服务器未知异常!"
                        : req.data.message,
                showClose: true,
              });
            }
          });
        } else {
          return false;
        }
      });
    },
    //注册
    toRegister() {
      this.$router.push({name: "register"});
    },
    randomNum(min, max) {
      return Math.floor(Math.random() * (max - min) + min);
    },
    refreshCode() {
      this.identifyCode = "";
      this.makeCode(this.identifyCodes, 4);
    },
    makeCode(o, l) {
      for (let i = 0; i < l; i++) {
        this.identifyCode +=
            this.identifyCodes[this.randomNum(0, this.identifyCodes.length)];
      }
    },
  },
};
</script>

<style scoped>
video {
  position: fixed;
  right: 0px;
  bottom: 0px;
  min-width: 100%;
  min-height: 100%;
  height: auto;
  width: 100%;
  z-index: -5;
}

.el-form {
  border: 1px solid white;
  width: 80%;
  margin: 0 auto;
  border-radius: 30px;
}

.el-form h1 {
  color: white;
  text-align: center;
}

.el-form >>> .el-input__inner {
  background-color: transparent;
  color: white;
  font-size: 20px;
}

.el-form .el-input {
  width: 60%;
  margin-top: 5%;
  margin-left: 20%;
}

.el-form .el-button {
  width: 15%;
  background-color: transparent;
  margin-left: 23%;
  margin-top: 5%;
  color: white;
}
</style>