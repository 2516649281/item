<template>
  <div>
    <video autoplay loop muted class="login-fillWidth" v-on:canplay="canplay">
      <source src="../image/bg.mp4" type="video/mp4"/>
    </video>
  </div>
  <el-container>
    <el-main>
      <el-form
          :model="loginFrom"
          :rules="rules"
          :inline-message="true"
          @keyup.enter.native="login(loginFrom)"
          ref="loginFrom"
      >
        <h1>欢迎登录</h1>
        <el-form-item prop="userName">
          <el-input v-model="loginFrom.userName" placeholder="请输入账号">
            <template v-slot:prefix
            ><i class="el-icon-user-solid"></i
            ></template>
          </el-input>
        </el-form-item>
        <el-form-item prop="userPassword">
          <el-input
              v-model="loginFrom.userPassword"
              placeholder="请输入密码"
              show-password
          >
            <template v-slot:prefix><i class="el-icon-lock"></i></template
            >
          </el-input>
        </el-form-item>
        <el-form-item prop="code">
          <el-input
              class="input_code"
              placeholder="请输入验证码"
              v-model="loginFrom.code"
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
        <el-form-item prop="againPassword">
          <el-button @click="getRegister" class="form-btn" round
          >注册
          </el-button
          >
          <el-button class="form-btn" round @click="login(loginFrom)"
          >登录
          </el-button
          >
        </el-form-item>
      </el-form>
    </el-main>
  </el-container>
</template>

<script>
import {login} from "../api/user";
import SIdentify from "../components/identify";

export default {
  data() {
    return {
      //表单数据
      dataFrom: {},
      //登录表单
      loginFrom: {
        //用户名
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
  components: {
    SIdentify: SIdentify,
  },
  methods: {
    //跳转到注册页面
    getRegister() {
      this.$router.push({name: "register"});
    },
    //登录逻辑
    login(obj) {
      this.$refs["loginFrom"].validate((valid) => {
        if (valid) {
          login(obj).then((req) => {
            if ((req.data.statue === 200) & (req.data.data != null)) {
              if (req.data.data.userIdentity === 1) {
                this.$message({
                  type: "success",
                  message: "登录成功!",
                  showClose: true,
                });
                sessionStorage.setItem("token", req.data.data.token);
                this.$router.push({name: "public"});
              } else {
                this.$message({
                  type: "warning",
                  message: "当前账号不是教师身份，请使用教师账号登录!",
                  showClose: true,
                });
                this.refreshCode();
              }
            } else {
              this.$message({
                type: "error",
                message: req.data.message,
                showClose: true,
              });
              this.refreshCode();
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

.el-form {
  border-radius: 30px;
  color: white;
  margin: 0 auto;
  width: 80%;
  border: 1px solid white;
}

.el-form-item__error {
  z-index: 5;
}

/deep/ .el-input__inner {
  color: white;
  background-color: transparent;
  font-size: 20px;
}

.el-form h1 {
  text-align: center;
}

.el-form .el-input {
  width: 60%;
  margin-top: 5%;
  margin-left: 20%;
}

.el-button,
.el-button:hover,
.el-button:focus {
  background-color: transparent;
  width: 15%;
}

.el-button:hover {
  color: wheat;
}

.el-button {
  color: white;
}

.form-btn {
  margin: 3% 22%;
}
</style>