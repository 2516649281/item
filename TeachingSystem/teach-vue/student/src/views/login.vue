<template>
  <div>
    <video autoplay loop muted class="login-fillWidth" v-on:canplay="canplay">
      <source src="../img/login.mp4" type="video/mp4"/>
    </video>
  </div>
  <el-button type="success" @click="toIndex" round>回到主页</el-button>
  <el-container>
    <el-form
        ref="loginFrom"
        :inline-message="true"
        :model="loginForm"
        :rules="rules"
        @keyup.enter.native="login(loginForm)"
    >
      <el-form-item>
        <h1>欢迎登录</h1>
      </el-form-item>
      <el-form-item prop="userName">
        <el-input
            v-model="loginForm.userName"
            class="input_user"
            placeholder="请输入账号"
            prefix-icon="el-icon-user-solid"
        ></el-input>
      </el-form-item>
      <el-form-item prop="userPassword"
      >
        <el-input
            v-model="loginForm.userPassword"
            class="input_password"
            placeholder="请输入密码"
            prefix-icon="el-icon-lock"
            show-password
        ></el-input
        >
      </el-form-item>
      <el-form-item prop="code">
        <el-input
            v-model="loginForm.code"
            class="input_code"
            placeholder="请输入验证码"
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
        <el-button round type="primary" @click="toRegister">注册</el-button>
        <el-button round type="success" @click="login(loginForm)"
        >登录
        </el-button
        >
      </el-form-item
      >
    </el-form>
  </el-container>
</template>

<script>
import {login} from "../api/user";
import SIdentify from "../components/identify";
export default {
  data() {
    return {
      loginForm: {
        userName: "",
        userPassword: "",
        code: "",
      },
      loginData: {
        statue: "",
        data: {
          user: {
            studentId: "",
            studentName: "",
            studentAge: "",
            studentGender: "",
            studentAddress: "",
            studentPhone: "",
            studentEmail: "",
          },
        },
        message: "",
      },
      rules: {
        userName: [
          {required: true, message: "账号不得为空!", trigger: "blur"},
        ],
        userPassword: [
          {required: true, message: "密码不得为空!", trigger: "blur"},
        ],
        code: [{required: true, message: "验证码不得为空!", trigger: "blur"}],
      },
      //随机数仓库
      identifyCodes: [1, 2, 3, 4, 5, 6, 7, 8, 9, 0],
      //验证码
      identifyCode: "",
    };
  },
  created() {
    this.refreshCode();
  },
  components: {
    SIdentify: SIdentify,
  },
  methods: {
    //   登录
    login(obj) {
      this.$refs["loginFrom"].validate((valid) => {
        if (valid) {
          login(obj).then((req) => {
            this.loginData = req.data;
            if (
                (this.loginData.statue == 200) &
                (this.loginData.data != null)
            ) {
              if (req.data.data.userIdentity === 0) {
                this.$message({
                  type: "success",
                  message: "登录成功!",
                  showClose: true,
                });
                sessionStorage.setItem("token", this.loginData.data.token);
                this.$router.push({
                  name: "public",
                  showClose: true,
                });
              } else {
                this.$message({
                  type: "warning",
                  message: "当前账号不是学生身份，请使用学生账号登录!",
                  showClose: true,
                });
              }
            } else {
              this.$message({
                type: "error",
                message: this.loginData.message,
                showClose: true,
              });
            }
          });
        } else {
          return false;
        }
      });
    },

    //回主页
    toIndex() {
      this.$router.push({
        name: "index",
      });
    },
    //注册
    toRegister() {
      this.$router.push({
        name: "register",
      });
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
.el-link {
  color: #fff;
  font-size: 15px;
}
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
.el-page-header {
  color: white;
}
.el-container {
  width: 100%;
  /* height: 100%; */
  height: 100%;
  background-size: 100%;
}
/* 表单 */
.el-form {
  width: 70%;
  height: 70%;
  margin: 5% auto;
  background-color: transparent;
  border: 1px white solid;
  border-radius: 25px;
  box-shadow: 0 2px 12px;
}
.el-form h1 {
  width: 25%;
  text-align: center;
  margin: 0 auto;
  color: white;
}
.el-form .el-input {
  width: 60%;
  margin-left: 20%;
  margin-top: 5%;
}
.el-form .el-form-item__error {
  width: 15%;
  margin: 0 auto;
}
.input_user >>> .el-input__inner,
.input_password >>> .el-input__inner {
  background-color: transparent;
  color: white;
  font-size: 20px;
}
.input_code >>> .el-input__inner {
  background-color: transparent;
  color: white;
  font-size: 20px;
  width: 70%;
  float: left;
}
.el-form .el-button {
  margin-left: 20%;
  width: 20%;
}
.el-button {
  background-color: transparent;
  height: 10%;
}
</style>