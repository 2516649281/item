<template>
  <el-container>
    <el-main>
      <h1>绑定身份信息</h1>
      <el-form :model="IdentityFrom" :rules="rules" ref="userIndex">
        <el-form-item label="请输入管理员编号:" prop="userIndex">
          <el-input v-model="IdentityFrom.userIndex"> </el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="bindIdentity(IdentityFrom.userIndex)"
            round
            icon="el-icon-paperclip"
            >绑定</el-button
          ><el-button
            type="danger"
            @click="exit"
            round
            icon="el-icon-switch-button"
            >返回</el-button
          >
        </el-form-item>
      </el-form>
    </el-main>
  </el-container>
</template>

<script>
import { bindIdentity } from "../api/user";
import jwtDecode from "jwt-decode";
export default {
  data() {
    return {
      //身份表单
      IdentityFrom: {
        userId: "",
        userIndex: "",
      },
      rules: {
        userIndex: [
          { required: true, message: "管理员编号不得为空!", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.IdentityFrom.userId = jwtDecode(
      sessionStorage.getItem("token")
    ).user.userId;
    this.$notify({
      title: "提示",
      message: "请使用已绑定身份的管理员账号登录，以查看您的管理员编号!",
      type: "warning",
    });
  },
  methods: {
    //身份绑定
    bindIdentity(Source) {
      this.$refs["userIndex"].validate((valid) => {
        if (valid) {
          bindIdentity(this.IdentityFrom.userId, Source).then((req) => {
            if ((req.data.data != null) & (req.data.statue == 200)) {
              this.$message({
                type: "success",
                message: "绑定身份成功!下次登录时生效!",
                showClose: true,
              });
              sessionStorage.clear();
              this.$router.push({ name: "login" });
            } else {
              this.$message({
                type: "error",
                message: "绑定身份失败!",
                showClose: true,
              });
            }
          });
        } else {
          return false;
        }
      });
    },
    //退出
    exit() {
      this.$router.go(-1);
    },
  },
};
</script>

<style scoped>
.el-form {
  margin: 0 auto;
  width: 50%;
}
h1 {
  text-align: center;
  color: white;
}
.el-form .el-input >>> .el-input__inner {
  color: white;
  background-color: transparent;
  width: 80%;
}
.el-form .el-button {
  width: 20%;
  margin: 5% 15%;
}
</style>