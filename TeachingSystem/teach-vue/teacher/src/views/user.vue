<template>
  <el-container>
    <div v-if="tableData.user != null" class="main">
      <el-main>
        <el-descriptions title="教师信息" border="true">
          <el-descriptions-item label="教师编号">{{
              tableData.user.teacherId
            }}
          </el-descriptions-item>
          <el-descriptions-item label="教师姓名">{{
              tableData.user.teacherName
            }}
          </el-descriptions-item>
          <el-descriptions-item label="教师年龄"
          >{{ tableData.user.teacherAge }}岁
          </el-descriptions-item
          >
          <el-descriptions-item label="教师性别">
            {{ tableData.user.teacherGender === 0 ? "女" : "男" }}
          </el-descriptions-item>
          <el-descriptions-item label="教师住址">{{
              tableData.user.teacherAddress
            }}
          </el-descriptions-item>
          <el-descriptions-item>
            <router-link to="/identity">不是我？前去更改身份</router-link>
          </el-descriptions-item>
        </el-descriptions>
        <el-divider></el-divider>
        <el-descriptions title="用户信息" border="true">
          <el-descriptions-item label="用户编号">{{
              tableData.userId
            }}
          </el-descriptions-item>
          <el-descriptions-item label="用户名">{{
              tableData.userName
            }}
          </el-descriptions-item>
        </el-descriptions>
        <el-divider></el-divider>
        <el-button
            round
            type="primary"
            icon="el-icon-key"
            @click="editdialogFormVisible = true"
        >修改密码
        </el-button
        >
        <el-button
            round
            type="success"
            icon="el-icon-upload"
            @click="toggleShow"
        >更改头像
        </el-button
        >
      </el-main>
    </div>
    <div v-else class="main">
      <el-main>
        <el-descriptions title="教师信息" border="true" class="userContent">
          <el-descriptions-item label="你还没有绑定身份信息">
            <router-link to="/identity">现在绑定</router-link>
          </el-descriptions-item>
        </el-descriptions>
        <el-divider></el-divider>
        <el-descriptions title="用户信息" border="true">
          <el-descriptions-item label="用户编号">{{
              tableData.userId
            }}
          </el-descriptions-item>
          <el-descriptions-item label="用户名">{{
              tableData.userName
            }}
          </el-descriptions-item>
        </el-descriptions>
        <el-button
            round
            type="primary"
            icon="el-icon-key"
            @click="editdialogFormVisible = true"
        >修改密码
        </el-button
        >
        <el-button
            round
            type="success"
            icon="el-icon-upload"
            @click="toggleShow"
        >更改头像
        </el-button
        >
      </el-main>
    </div>
  </el-container>
  <!-- 修改作业 -->
  <el-dialog title="修改密码" v-model="editdialogFormVisible">
    <el-form :model="updateFrom" :rules="rules" ref="updateFrom">
      <el-form-item prop="userPassword">
        <el-input
            v-model="updateFrom.userPassword"
            autocomplete="off"
            placeholder="请输入原始密码"
            show-password
        ></el-input>
      </el-form-item>
      <el-form-item prop="newUserPassword">
        <el-input
            v-model="updateFrom.newUserPassword"
            autocomplete="off"
            placeholder="请输入新密码"
            show-password
        ></el-input>
      </el-form-item>
      <el-form-item prop="againPassword">
        <el-input
            v-model="updateFrom.againPassword"
            autocomplete="off"
            placeholder="请再次输入新密码"
            show-password
        ></el-input>
      </el-form-item>
    </el-form>
    <template v-slot:footer>
      <el-button @click="editdialogFormVisible = false" round>取 消</el-button>
      <el-button type="primary" @click="updateUser(updateFrom)" round
      >确 定
      </el-button
      >
    </template>
  </el-dialog>
  <!-- 上传头像组件 -->
  <div>
    <myUpload
        method="PUT"
        :headers="Headers"
        v-model="showDialog"
        :url="url + `/user/${this.tableData.userId}`"
        @crop-upload-success="cropUploadSuccess"
    />
  </div>
</template>

<script>
import jwtDecode from "jwt-decode";
import {updateUser, getToken} from "../api/user";
import {baseURL} from "../../public/config";
import myUpload from "vue-image-crop-upload";

export default {
  components: {myUpload},
  data() {
    return {
      //数据
      tableData: {},
      //修改数据
      updateFrom: {
        //用户编号
        userId: "",
        //密码
        userPassword: "",
        //新密码
        newUserPassword: "",
        //再次确认密码
        againPassword: "",
      },
      //访问路径
      url: baseURL,
      //显示上传图片窗口
      showDialog: false,
      //请求头
      Headers: {token: sessionStorage.getItem("token")},
      //编辑框
      editdialogFormVisible: false,
      //表单验证
      rules: {
        userPassword: [
          {required: true, message: "原始密码不得为空!", trigger: "blur"},
        ],
        newUserPassword: [
          {required: true, message: "请输入新密码!", trigger: "blur"},
        ],
        againPassword: [
          {required: true, message: "请再次输入密码!", trigger: "blur"},
        ],
      },
      timer: "",
    };
  },
  created() {
    this.tableData = jwtDecode(sessionStorage.getItem("token")).user;
    this.timer = setInterval(this.getToken, 1000 * 60 * 3);
  },
  methods: {
    //修改密码
    updateUser(obj) {
      obj.userId = this.tableData.userId;
      this.$refs["updateFrom"].validate((valid) => {
        if (valid) {
          if (obj.newUserPassword != obj.againPassword) {
            this.$message({
              type: "warning",
              message: "两次输入的密码不一致!",
              showClose: true,
            });
          }
          updateUser(obj).then((req) => {
            if ((req.data.statue === 200) & (req.data.data != null)) {
              this.$message({
                type: "success",
                message: "修改成功!请重新登录!",
                showClose: true,
              });
              sessionStorage.clear();
              this.$router.push({name: "login"});
            } else {
              this.$message({
                type: "error",
                message: req.data.message,
                showClose: true,
              });
            }
          });
        } else {
          return false;
        }
      });
    },
    // 点击头像打开上传窗口
    toggleShow() {
      this.showDialog = !this.showDialog;
    },
    // 上传成功后重新载入信息
    cropUploadSuccess(jsonData) {
      if ((jsonData.statue === 200) & (jsonData.data != null)) {
        this.$confirm(
            "更换头像成功!但需要重新登录才能生效, 是否重新登录?",
            "提示",
            {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning",
            }
        ).then(() => {
          sessionStorage.clear();
          this.$router.push({name: "login"});
        });
      } else {
        this.$message({
          message: "更换头像失败!",
          type: "error",
          showClose: true,
        });
      }
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
.el-descriptions {
  margin: 0 auto;
  color: white;
}

.el-dialog .el-button {
  width: 20%;
  margin: 5% 15%;
}

.main .el-main .el-button {
  width: 20%;
  margin: 5% 15%;
}

.main {
  width: 100%;
  margin: 0 auto;
}
</style>