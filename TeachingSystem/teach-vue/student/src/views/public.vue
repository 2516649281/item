<template>
  <img id="background" alt="" src="../img/bg.png"/>
  <el-container>
    <el-header>
      <div id="header-image">
        <el-image :src="header" fit="fill"></el-image>
      </div>
      <el-menu
          id="header-link"
          :default-active="1"
          active-text-color="#ffd04b"
          background-color="#212121"
          class="el-menu-demo"
          mode="horizontal"
          text-color="#fff"
      >
        <el-menu-item @click="toIndex" index="1"
        >
          <template v-slot:title>
            <i class="el-icon-s-home"></i>
            <span>首页</span></template
          >
        </el-menu-item
        >
        <el-menu-item @click="toMyClass" index="2" :disabled="menudisabled"
        >
          <template v-slot:title>
            <i class="el-icon-s-custom"></i>
            <span>我的班级</span></template
          >
        </el-menu-item>
        <el-submenu index="3" :disabled="menudisabled">
          <template v-slot:title>
            <i class="el-icon-tickets"></i>
            <span>作业</span></template
          >
          <el-menu-item @click="toSubmitWork" index="3-1"
          >
            <template v-slot:title>
              <i class="el-icon-document-checked"></i>
              <span>已提交的作业</span></template
            >
          </el-menu-item
          >
          <el-menu-item index="3-2" @click="toCompleteWork"
          >
            <template v-slot:title>
              <i class="el-icon-document"></i>
              <span>需完成的作业 </span></template
            >
          </el-menu-item
          >
        </el-submenu>
      </el-menu>
      <div id="header-link"></div>
      <div id="header-input">
        <el-input
            v-model="search"
            class="search_input"
            placeholder="输入关键字"
        >
        </el-input>
        <el-button type="success" icon="el-icon-search"></el-button>
      </div>
      <div id="header-user" v-if="this.token === null">
        <el-button :underline="false" type="success" circle @click="toRegister"
        >注
        </el-button
        >
        <router-link to="/login" :underline="false"
        >
          <el-avatar shape="square"> 请登录</el-avatar>
        </router-link
        >
      </div>
      <div id="header-user" v-else>
        <router-link to="/user" :underline="false"
        >
          <el-avatar
              v-if="userFrom.userHeader != 0"
              :src="fileUrl + userFrom.userHeader"
              fit="contain"
              shape="square"
          >
          </el-avatar>
          <el-avatar
              v-else
              icon="el-icon-user-solid"
              shape="square"
          ></el-avatar>
        </router-link
        >
        欢迎你,{{ userFrom.userName }}
      </div>
    </el-header>
    <el-main>
      <router-view/>
    </el-main>
    <el-footer>
      <el-footer>
        <div id="foot-text">
          <li>联系我们</li>
          <li>求职</li>
          <li>在线面试</li>
          <li>活动</li>
          <li>关于</li>
          <li></li>
        </div>
      </el-footer
      >
    </el-footer>
  </el-container>
</template>

<script>
import {getToken} from "../api/user";
import jwtDecode from "jwt-decode";
import jpg from "@/assets/logo.png";
import {FileServerURL} from "../../public/config";
export default {
  data() {
    return {
      menudisabled: false,
      //用户信息
      userFrom: {},
      //token
      token: "",
      //提交按钮
      search: "",
      // logo
      header: jpg,
      // 用户弹出窗显示
      userDialogFormVisible: false,
      // 用户信息
      userForm: {
        studentId: "",
        studentName: "",
      },
      fileUrl: FileServerURL,
    };
  },
  created() {
    this.token = sessionStorage.getItem("token");
    if (this.token != null) {
      this.userFrom = jwtDecode(this.token).user;
    }
    if (this.userFrom.user === null) {
      this.$message({
        type: "warning",
        message:
            "当前账号未绑定学生身份信息，请联系管理员绑定后以获取完整功能!",
        showClose: true,
      });
      this.menudisabled = true;
    }
  },
  mounted() {
    //用户登陆后每隔3min向后端发送一次http请求
    if (this.token != "") {
      const timer = setInterval(() => {
        setTimeout(
            getToken().then((req) => {
              if (req.data.statue != 200 || req.data.data != true) {
                clearInterval(timer);
                this.$notify({
                  title: "提示",
                  message: "当前登录信息已失效，请重新登录!",
                  type: "warning",
                });
              }
            }),
            0
        );
      }, 3 * 60 * 1000);
    }
  },
  methods: {
    // 主页
    toIndex() {
      this.$router.push({
        name: "index",
      });
    },

    // 我的班级
    toMyClass() {
      this.$router.push({
        name: "myClass",
      });
    },
    //作业
    toSubmitWork() {
      this.$router.push({
        name: "submitWork",
      });
    },
    //需完成的作业
    toCompleteWork() {
      this.$router.push({
        name: "completeWork",
      });
    },
    // 登录
    toLogin() {
      this.$router.push({
        name: "login",
      });
    },
    //注册
    toRegister() {
      this.$router.push({
        name: "register",
      });
    },
  },
};
</script>

<style scoped>
#background {
  position: fixed;
  right: 0px;
  bottom: 0px;
  min-width: 100%;
  min-height: 100%;
  height: auto;
  width: 100%;
  z-index: -5;
}
/* 头部 */
.el-header {
  width: 100%;
  height: 20%;
  border: 1px white solid;
  color: white;
}
#header-image,
#header-link,
#header-input,
#header-user {
  float: left;
}
#header-image {
  width: 6%;
}
#header-link {
  width: 40%;
}
#header-input {
  width: 30%;
}
#header-user {
  float: right;
}
#header-link .el-menu-item {
  width: 33%;
  text-align: center;
  color: white;
}
#header-input .el-input {
  width: 60%;
  margin: 3% 6%;
}
.search_input >>> .el-input__inner {
  background-color: transparent;
  color: white;
}
#header-user .el-avatar {
  float: right;
}
#header-user .el-button {
  text-align: center;
  float: right;
}
.el-image {
  width: 100%;
  height: 100%;
}
.el-main {
  border: 1px white solid;
}

.el-footer {
  margin: 0;
  padding: 0;
  background: transparent;
  color: white;
  border: 1px white solid;
}
#foot-text {
  width: 100%;
  margin: 0 auto;
}
#foot-text li {
  list-style-type: none;
  float: left;
  margin: 1%;
  text-align: center;
}
</style>