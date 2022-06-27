<template>
  <div>
    <video autoplay loop muted class="login-fillWidth" v-on:canplay="canplay">
      <source src="../image/bg.mp4" type="video/mp4"/>
    </video>
  </div>
  <el-container>
    <el-main>
      <el-form
          :model="registerFrom"
          :rules="rules"
          :inline-message="true"
          @keyup.enter.native="register(registerFrom)"
          ref="registerFrom"
      >
        <h1>欢迎注册</h1>
        <el-form-item prop="userName">
          <el-input placeholder="请输入账号" v-model="registerFrom.userName"
          >
            <template v-slot:prefix
            ><i class="el-icon-user-solid"></i></template
            >
          </el-input>
        </el-form-item>
        <el-form-item prop="userPassword">
          <el-input
              placeholder="请输入密码"
              v-model="registerFrom.userPassword"
              show-password
          >
            <template v-slot:prefix><i class="el-icon-lock"></i></template
            >
          </el-input>
        </el-form-item>
        <el-form-item prop="againPassword">
          <el-input
              placeholder="请再次输入密码"
              v-model="registerFrom.againPassword"
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
              v-model="registerFrom.code"
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
          <el-button @click="exit" class="form-btn" round>返回</el-button>
          <el-button class="form-btn" round @click="register(registerFrom)"
          >注册
          </el-button
          >
        </el-form-item>
      </el-form>
    </el-main>
  </el-container>
</template>

<script>
import {register} from "../api/user";
import SIdentify from "../components/identify";

export default {
  data() {
    return {
      //注册表单
      registerFrom: {
        //用户名
        userName: "",
        //用户密码
        userPassword: "",
        //再次输入密码
        againPassword: "",
        //验证码
        code: "",
        //用户身份
        userIdentity: "",
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
        againPassword: [
          {required: true, message: "请再次输入密码!", trigger: "blur"},
        ],
        userName: [
          {required: true, message: "用户名不得为空!", trigger: "blur"},
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
    //返回
    exit() {
      this.$router.go(-1);
    },
    //注册逻辑
    register(obj) {
      obj.userIdentity = 1;
      this.$refs["registerFrom"].validate((valid) => {
        if (valid) {
          if (obj.userPassword != obj.againPassword) {
            this.$message({
              type: "warning",
              message: "两次输入的密码不一致!",
              showClose: true,
            });
            this.refreshCode();
            return;
          }
          register(obj).then((req) => {
            if ((req.data.statue === 200) & (req.data.data != null)) {
              this.$message({
                type: "success",
                message: "注册成功,请登录!",
                showClose: true,
              });
              this.$router.push({
                name: "login",
              });
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