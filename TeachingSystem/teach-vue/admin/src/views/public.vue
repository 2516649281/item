<template>
  <img src="../image/bg.jpg" alt="" id="background"/>
  <el-container>
    <el-aside
    >
      <el-menu
          default-active="1"
          background-color="transparent"
          text-color="#fff"
          active-text-color="#ffd04b"
      >
        <el-image :src="require('@/assets/logo.png')" fit="contain"></el-image>
        <el-menu-item index="1" @click="toUser">
          <i class="el-icon-setting"></i>
          <template v-slot:title>个人设置</template>
        </el-menu-item>
        <el-menu-item index="2" @click="toStudent" :disabled="menudisabled">
          <i class="el-icon-school"></i>
          <template v-slot:title>学生管理</template>
        </el-menu-item
        >
        <el-menu-item index="3 " @click="toTeacher" :disabled="menudisabled">
          <i class="el-icon-s-custom"></i>
          <template v-slot:title>教师管理</template>
        </el-menu-item
        >
        <el-menu-item index="4" @click="toAdmin" :disabled="menudisabled">
          <i class="el-icon-star-on"></i>
          <template v-slot:title>管理员管理</template>
        </el-menu-item
        >
        <el-menu-item index="5" @click="toClass" :disabled="menudisabled">
          <i class="el-icon-price-tag"></i>
          <template v-slot:title>班级管理</template>
        </el-menu-item
        >
        <el-menu-item index="6" @click="toSubject" :disabled="menudisabled">
          <i class="el-icon-notebook-2"></i>
          <template v-slot:title>科目管理</template>
        </el-menu-item>
        <el-menu-item index="7" @click="toFile" :disabled="menudisabled">
          <i class="el-icon-folder-opened"></i>
          <template v-slot:title>文件管理</template>
        </el-menu-item>
        <el-menu-item index="8" @click="exitLogin">
          <i class="el-icon-switch-button"></i>
          <template v-slot:title>退出登录</template>
        </el-menu-item>
      </el-menu
      >
    </el-aside
    >
    <el-container>
      <el-header>
        <div class="user">
          <el-avatar
              shape="square"
              v-if="userFrom.userHeader != 0"
              fit="contain"
              :src="FileUrl + userFrom.userHeader"
          >
          </el-avatar>
          <el-avatar shape="square" v-else>user</el-avatar>
          <h1>欢迎你，{{ this.userFrom.userName }}</h1>
        </div>
      </el-header>
      <el-main>
        <iframe
            v-if="iframeV"
            :src="nacos"
            scrolling="yes"
            frameborder="0"
            width="100%"
            height="100%"
        ></iframe>
        <router-view v-else
        />
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import jwtDecode from "jwt-decode";
import {getToken} from "../api/user";
import {FileURL} from "../../public/config";

export default {
  data() {
    return {
      //菜单权限控制
      menudisabled: false,
      userFrom: {},
      FileUrl: FileURL + "/select/",
      timer: "",
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
        message: "当前账号未绑定管理员身份信息，请绑定后以获取完整功能!",
        showClose: true,
      });
      this.menudisabled = true;
    }
    this.timer = setInterval(this.getToken, 1000 * 60 * 3);
  },
  methods: {
    //个人管理
    toUser() {
      this.$router.push({name: "user"});
    },
    //学生管理
    toStudent() {
      this.$router.push({name: "student"});
    },
    //教师管理
    toTeacher() {
      this.$router.push({name: "teacher"});
    },
    //管理员管理
    toAdmin() {
      this.$router.push({name: "admin"});
    },
    //班级管理
    toClass() {
      this.$router.push({name: "class"});
    },
    //科目管理
    toSubject() {
      this.$router.push({name: "subject"});
    },
    //文件管理
    toFile() {
      this.$router.push({name: "file"});
    },
    //退出登录
    exitLogin() {
      this.$confirm("你真的要退出吗??", "退出登录", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.$message({
          type: "success",
          message: "登出成功!",
          showClose: true,
        });
        sessionStorage.clear();
        this.$router.push({name: "login"});
      });
    },
    //验证token
    getToken() {
      getToken().then((req) => {
        if (req.data.statue != 200 || req.data.data != true) {
          clearInterval(timer);
          this.$notify({
            title: "提示",
            message: "当前登录信息已失效，请重新登录!",
            type: "warning",
          });
        }
      });
    },
  },
  beforeDestroy() {
    clearInterval(this.timer);
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

.el-container .el-aside {
  width: 15%;
  /* border: 1px solid white; */
}

.el-container {
  color: white;
}

.el-header {
  border-bottom: 1px solid white;
}

.el-header h1,
.el-header .el-avatar {
  float: right;
}
</style>