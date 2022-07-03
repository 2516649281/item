<template>
  <div>
    <video autoplay loop muted class="login-fillWidth" v-on:canplay="canplay">
      <source src="../img/login.mp4" type="video/mp4"/>
    </video>
    <el-container>
      <el-main>
        <el-form
            ref="registerFrom"
            :inline-message="true"
            :model="registerFrom"
            :rules="rules"
            @keyup.enter.native="register(registerFrom)"
        >
          <el-form-item>
            <h1>欢迎注册</h1>
          </el-form-item>
          <el-form-item prop="userName">
            <el-input
                v-model="registerFrom.userName"
                class="input_user"
                placeholder="输入用户名"
                prefix-icon="el-icon-user-solid"
            ></el-input>
          </el-form-item>
          <el-form-item prop="userPassword">
            <el-input
                v-model="registerFrom.userPassword"
                class="input_password"
                placeholder="输入密码"
                prefix-icon="el-icon-lock"
                show-password
            ></el-input>
          </el-form-item>
          <el-form-item prop="againPassword">
            <el-input
                v-model="registerFrom.againPassword"
                class="input_password"
                placeholder="再次输入密码"
                prefix-icon="el-icon-lock"
                show-password
            ></el-input>
          </el-form-item>
          <el-form-item prop="code">
            <el-input
                v-model="registerFrom.code"
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
            <el-button type="primary" round @click="exit">返回</el-button>
            <el-button type="success" round @click="register(registerFrom)"
            >注册
            </el-button
            >
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import {register} from "../api/user";
import SIdentify from "../components/identify";
export default {
  data() {
    return {
      //注册
      registerFrom: {
        userName: "",
        userPassword: "",
        againPassword: "",
        code: "",
      },
      rules: {
        userName: [
          {required: true, message: "账号不得为空!", trigger: "blur"},
        ],
        userPassword: [
          {required: true, message: "密码不得为空!", trigger: "blur"},
        ],
        againPassword: [
          {required: true, message: "请确认密码!", trigger: "blur"},
        ],
        code: [{required: true, message: "验证码不得为空!", trigger: "blur"}],
      },
      //随机数仓库
      identifyCodes: [1, 2, 3, 4, 5, 6, 7, 8, 9, 0],
      //验证码
      identifyCode: "",
    };
  },
  components: {
    SIdentify: SIdentify,
  },
  mounted() {
    this.identifyCode = "";
    this.makeCode(this.identifyCodes, 4);
  },
  created() {
  },
  methods: {
    //返回
    exit() {
      this.$router.go(-1);
    },
    //注册
    register(obj) {
      this.$refs["registerFrom"].validate((valid) => {
        if (valid) {
          if (obj.userPassword != obj.againPassword) {
            this.$message({
              type: "warning",
              message: "两次输入的密码不一致!",
            });
            return;
          }
          register(obj).then((req) => {
            if ((req.data.statue === 200) & (req.data.data != null)) {
              this.$message({
                type: "success",
                message: "注册成功!请登录",
                showClose: true,
              });
              this.$router.push({
                name: "login",
              });
            } else {
              this.$message({
                type: "error",
                message: "注册失败!" + req.data.message,
                showClose: true,
              });
            }
          });
        } else {
          return false;
        }
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
.el-container {
  width: 100%;
  height: 100%;
  background-size: 100%;
}
/* 表单 */
.el-form {
  width: 70%;
  height: 77%;
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
  margin-top: 3%;
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