<template>
  <img src="../image/bg.jpg" alt="" id="background" />
  <el-container>
    <el-aside :width="collapse ? '5%' : '20%'"
      ><el-menu
        default-active="1"
        class="el-menu-vertical-demo"
        background-color="transparent"
        text-color="#fff"
        active-text-color="#ffd04b"
        :collapse="collapse"
        :collapse-transition="false"
      >
        <el-image :src="require('@/assets/logo.png')" fit="contain"></el-image>
        <el-menu-item index="1" @click="getUser">
          <i class="el-icon-user-solid"></i>
          <span>账号管理</span>
        </el-menu-item>
        <el-menu-item index="2" @click="getExamine" :disabled="menudisabled">
          <i class="el-icon-view"></i>
          <span>检查作业</span>
        </el-menu-item>
        <el-menu-item index="4" @click="getLook" :disabled="menudisabled">
          <i class="el-icon-s-claim"></i>
          <span>已发作业</span>
        </el-menu-item>
        <el-menu-item index="5" @click="exit">
          <i class="el-icon-switch-button"></i>
          <span>退出系统</span>
        </el-menu-item>
      </el-menu></el-aside
    >
    <el-container>
      <el-header
        ><el-button
          class="menu_shrink"
          :icon="this.collapse ? 'el-icon-caret-right' : 'el-icon-caret-left'"
          @click="this.collapse = !this.collapse"
        ></el-button>
        <div class="user">
          <el-avatar
            shape="square"
            v-if="userFrom.userHeader != 0"
            fit="contain"
            :src="FileURL + this.userFrom.userHeader"
          >
          </el-avatar>
          <el-avatar shape="square" v-else>user</el-avatar>
          <h1>欢迎你，{{ this.userFrom.userName }}</h1>
        </div>
      </el-header>
      <el-main><router-view></router-view></el-main>
    </el-container>
  </el-container>
</template>

<script>
import { FileServerURL } from "../../public/config";
export default {
  data() {
    return {
      //菜单权限控制
      menudisabled: false,
      //token
      token: "",
      //折叠控制
      collapse: true,
      //用户名
      userFrom: {},
      //文件服务器
      FileURL: FileServerURL,
    };
  },
  created() {
    this.userFrom = JSON.parse(sessionStorage.getItem("user"));
    if (this.userFrom.user === null) {
      this.$message({
        type: "warning",
        message:
          "当前账号未绑定教师身份信息，请联系管理员绑定后以获取完整功能!",
        showClose: true,
      });
      this.menudisabled = true;
    }
  },
  methods: {
    //检查作业
    getExamine() {
      this.$router.push({ name: "examine" });
    },

    //已发布的作业
    getLook() {
      this.$router.push({ name: "look" });
    },

    //用户管理
    getUser() {
      this.$router.push({ name: "user" });
    },

    //退出
    exit() {
      this.$confirm("你真的要退出登录吗?", "退出登录", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        sessionStorage.clear();
        this.$router.push({ name: "login" });
        this.$message({
          type: "success",
          message: "登出成功!",
          showClose: true,
        });
      });
    },
  },
};
</script>

<style scoped>
.el-aside,
.el-menu {
  transition: 0.3s;
  -webkit-transition: 0.5s;
  -moz-transition: 0.5s;
  -webkit-transition: 0.5s;
  -o-transition: 0.5s;
}
.el-menu {
  width: auto;
}
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
.el-container {
  color: white;
  width: 100%;
  height: 100%;
}
.el-container .el-header {
  border-bottom: 1px solid white;
}
.el-container .el-main {
  width: 100%;
}
.el-container .el-header .el-button >>> .el-button__inner {
  background-color: transparent;
}
.menu_shrink {
  background-color: transparent;
  color: white;
}
.el-button,
.el-button:hover,
.el-button:focus {
  background-color: transparent;
}
.user {
  float: right;
  /* border: 1px solid red; */
  width: 20%;
  height: auto;
}
.user h1 {
  width: auto;
  float: left;
}
</style>