<template v-slot:footer>
  <el-container>
    <div v-if="userFrom.user != null" class="main">
      <el-main
      >
        <el-descriptions title="学生信息" border="true" class="userContent">
          <el-descriptions-item label="学生编号">{{
              userFrom.user.studentId
            }}
          </el-descriptions-item>
          <el-descriptions-item label="学生姓名">{{
              userFrom.user.studentName
            }}
          </el-descriptions-item>
          <el-descriptions-item label="学生年龄"
          >{{ userFrom.user.studentAge }}岁
          </el-descriptions-item
          >
          <el-descriptions-item label="学生性别">{{
              userFrom.user.studentGender == 0 ? "女" : "男"
            }}
          </el-descriptions-item>
          <el-descriptions-item label="联系方式">{{
              userFrom.user.studentPhone
            }}
          </el-descriptions-item>
          <el-descriptions-item label="学生居住地">{{
              userFrom.user.studentAddress
            }}
          </el-descriptions-item>
          <el-descriptions-item label="电子邮箱">{{
              userFrom.user.studentEmail
            }}
          </el-descriptions-item>
          <el-descriptions-item>
            <router-link to="/identity">不是我？前去更改身份</router-link>
          </el-descriptions-item>
        </el-descriptions>
        <el-divider></el-divider>
        <el-descriptions title="账号信息" border="true" class="userContent">
          <el-descriptions-item label="账号编号">{{
              userFrom.userId
            }}
          </el-descriptions-item>
          <el-descriptions-item label="账号">{{
              userFrom.userName
            }}
          </el-descriptions-item>
        </el-descriptions>
        <el-button
            type="primary"
            @click="this.loginDialogFormVisible = true"
            icon="el-icon-key"
        >修改密码
        </el-button
        >
        <el-button type="success" icon="el-icon-upload" @click="toggleShow">
          更改头像
        </el-button>
        <el-button type="danger" @click="exitLogin" icon="el-icon-switch-button"
        >退出登录
        </el-button
        >
      </el-main>
    </div>
    <div v-else class="main">
      <el-main>
        <el-descriptions title="学生信息" border="true" class="userContent">
          <el-descriptions-item label="你还没有绑定身份信息">
            <router-link to="/identity">现在绑定</router-link>
          </el-descriptions-item>
        </el-descriptions>
        <el-divider></el-divider>
        <el-descriptions title="账号信息" border="true" class="userContent">
          <el-descriptions-item label="账号编号">{{
              userFrom.userId
            }}
          </el-descriptions-item>
          <el-descriptions-item label="账号">{{
              userFrom.userName
            }}
          </el-descriptions-item>
        </el-descriptions>
        <el-button
            type="primary"
            @click="this.loginDialogFormVisible = true"
            icon="el-icon-key"
        >修改密码
        </el-button
        >
        <el-button type="success" icon="el-icon-upload" @click="toggleShow">
          更改头像
        </el-button>
        <el-button
            type="danger"
            @click="exitLogin"
            icon="el-icon-switch-button
"
        >退出登录
        </el-button
        >
      </el-main>
    </div>
  </el-container>
  <!-- 修改密码 -->
  <el-dialog title="修改密码" v-model="loginDialogFormVisible">
    <el-form
        :model="updatePasswordFrom"
        @keyup.enter.native="updatePassword(updatePasswordFrom)"
        :rules="rules"
        ref="updateFrom"
    >
      <el-form-item
          label="请输入密码:"
          :label-width="formLabelWidth"
          prop="userPassword"
      >
        <el-input
            v-model="updatePasswordFrom.userPassword"
            show-password
        ></el-input>
      </el-form-item>
      <el-form-item
          label="输入新密码:"
          :label-width="formLabelWidth"
          prop="newUserPassword"
      >
        <el-input
            v-model="updatePasswordFrom.newUserPassword"
            show-password
        ></el-input>
      </el-form-item>
      <el-form-item
          label="确认新密码:"
          :label-width="formLabelWidth"
          prop="againUserPassword"
      >
        <el-input
            v-model="updatePasswordFrom.againUserPassword"
            show-password
        ></el-input>
      </el-form-item>
    </el-form>
    <div class="dialog-footer">
      <el-button @click="loginDialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="updatePassword(updatePasswordFrom)"
      >确 定
      </el-button
      >
    </div>
  </el-dialog>
  <div>
    <myUpload
        method="PUT"
        :headers="Headers"
        v-model="showDialog"
        :url="url + `/user/${this.userFrom.userId}`"
        @crop-upload-success="cropUploadSuccess"
    />
  </div>
</template>

<script>
import myUpload from "vue-image-crop-upload";
import {updatePassword} from "../api/user";
import jwtDecode from "jwt-decode";
import {baseURL} from "../../public/config";

export default {
  components: {myUpload},
  data() {
    return {
      // 用户信息
      userFrom: {},
      //   修改登录
      loginDialogFormVisible: false,
      //   修改密码的表单数据
      updatePasswordFrom: {
        userId: "",
        userPassword: "",
        newUserName: "",
        newUserPassword: "",
        againUserPassword: "",
        newUserHeader: "",
      },
      rules: {
        userPassword: [
          {required: true, message: "密码不得为空!", trigger: "blur"},
        ],
        newUserPassword: [
          {required: true, message: "请输入新密码", trigger: "blur"},
        ],
        againUserPassword: [
          {required: true, message: "请再次输入密码", trigger: "blur"},
        ],
      },
      //访问路径
      url: baseURL,
      //显示上传图片窗口
      showDialog: false,
      Headers: {token: sessionStorage.getItem("token")},
    };
  },
  created() {
    this.userFrom = jwtDecode(sessionStorage.getItem("token")).user;
  },
  methods: {
    // 退出操作
    exitLogin() {
      this.$confirm("是否退出?", "退出登录", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.$router.push({
          name: "login",
        });
        sessionStorage.clear();
        this.$message({
          showClose: true,
          type: "success",
          message: "登出成功!",
        });
      });
    },

    // 修改密码
    updatePassword(obj) {
      this.$refs["updateFrom"].validate((valid) => {
        if (valid) {
          if (obj.newUserPassword != obj.againUserPassword) {
            this.$message({
              type: "warning",
              message: "两次输入的密码不一致!",
              showClose: true,
            });
            return;
          }
          updatePassword(obj).then((req) => {
            if ((req.data.statue == 200) & (req.data.data > 0)) {
              this.loginDialogFormVisible = false;
              this.$message({
                type: "success",
                message: "修改账号成功!请重新登录!",
                showClose: true,
              });
              this.$router.push({
                name: "login",
              });
              sessionStorage.clear();
            } else {
              this.$message({
                type: "error",
                message: "修改失败!",
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
          type: "error",
          message: "头像上传失败!",
          showClose: true,
        });
      }
    },
  },
};
</script>

<style scoped>
.el-descriptions {
  width: 80%;
  margin: 0 auto;
  color: white;
}

.el-dialog .el-button {
  width: 17%;
  margin: 5% 15%;
}

.main .el-main .el-button {
  margin: 5% 9%;
  width: 15%;
}

.main {
  width: 100%;
}
</style>