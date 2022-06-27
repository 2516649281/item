<template>
  <el-container>
    <el-main v-show="show1" class="selectFrom">
      <el-form :model="selectFrom" :inline="true" :rules="rules">
        <el-form-item label="请输入你要查询的作业编号:" prop="workId">
          <el-input v-model="selectFrom.workId"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button round @click="selectAllSubmit(selectFrom.workId)"
          >查询
          </el-button
          >
        </el-form-item>
      </el-form>
    </el-main>
    <el-main v-show="!show1">
      <el-button
          type="primary"
          @click="exit"
          circle
          class="exitbtn"
          icon="el-icon-refresh-left"
      ></el-button>
      <el-table :data="tableData" height="450">
        <el-table-column label="提交信息" type="expand" width="100">
          <template #default="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="学生完成的作业:">
                <span>{{ props.row.submitContent }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column
            label="提交编号"
            prop="submitId"
            align="center"
            width="100"
        ></el-table-column>
        <el-table-column
            label="学生姓名"
            prop="student.studentName"
            align="center"
            width="150"
        ></el-table-column>
        <el-table-column
            label="学生成绩"
            prop="submitScore"
            align="center"
            width="150"
        ></el-table-column>
        <el-table-column
            label="创建时间"
            prop="log.createTime"
            align="center"
            width="250"
        ></el-table-column>
        <el-table-column
            label="最后修改时间"
            prop="log.updateTime"
            align="center"
            width="250"
        ></el-table-column>
        <el-table-column label="操作" fixed="right">
          <template #default="props">
            <el-button
                icon="el-icon-edit"
                circle
                type="success"
                @click="updateClick(props.row)"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
  </el-container>
  <!-- 修改作业 -->
  <el-dialog title="批改作业" v-model="editdialogFormVisible">
    <h1>你选择了{{ editFrom.submitId }}号作业</h1>
    <el-form :model="editFrom">
      <el-form-item label="请输入一个合适的分数:" :label-width="formLabelWidth">
        <el-input-number
            v-model="editFrom.submitScore"
            :min="0"
            :max="100"
            :step="10"
        ></el-input-number>
      </el-form-item>
    </el-form>
    <template v-slot:footer>
      <el-button @click="editdialogFormVisible = false" round>取 消</el-button>
      <el-button type="primary" @click="updateSubmit(editFrom)" round
      >确 定
      </el-button
      >
    </template>
  </el-dialog>
</template>
<script>
import {selectAllSubmit, updateSubmit} from "../api/work";

export default {
  data() {
    return {
      //表格数据
      tableData: [],
      //表格与提交表单的显示和隐藏
      show1: true,
      //查询
      selectFrom: {
        //作业编号
        workId: "",
      },
      //批改作业
      editFrom: {
        //提交编号
        submitId: "",
        //成绩
        submitScore: "",
      },
      //编辑框
      editdialogFormVisible: false,
      rules: {
        workId: [
          {
            required: true,
            message: "作业编号不得为空!",
            trigger: "blur",
          },
        ],
      },
    };
  },
  created() {
    this.$notify({
      title: "提示",
      message: "请先到已发作业页面获取作业编号!",
      type: "warning",
    });
  },
  methods: {
    //根据作业编号查询学生完成情况
    selectAllSubmit(workId) {
      if (workId === "") {
        this.$message({
          type: "warning",
          message: "请输入作业编号!",
          showClose: true,
        });
        return;
      }
      selectAllSubmit(workId).then((req) => {
        this.tableData = req.data.data;
        this.show1 = false;
      });
    },

    exit() {
      this.show1 = true;
    },
    //批改作业
    updateSubmit(obj) {
      updateSubmit(obj).then((req) => {
        if ((req.data.statue === 200) & (req.data.data != null)) {
          this.$message({
            type: "success",
            message: "操作成功!",
            showClose: true,
          });
          this.selectAllSubmit(this.selectFrom.workId);
          this.editdialogFormVisible = false;
        } else {
          this.$message({
            type: "error",
            message: "操作失败!",
            showClose: true,
          });
        }
      });
    },
    //批改作业
    updateClick(obj) {
      this.editFrom = obj;
      this.editdialogFormVisible = true;
    },
  },
};
</script>

<style scoped>
/*最外层透明*/
/deep/ .el-table,
/deep/ .el-table__expanded-cell {
  background-color: transparent;
}

/* 表格内背景颜色 */
/deep/ .el-table th,
/deep/ .el-table tr,
/deep/ .el-table td {
  background-color: transparent;
  color: grey;
}

/deep/ .selectFrom .el-input__inner {
  color: white;
  background-color: transparent;
}

.el-form {
  /* width: 50%; */
  margin: 0 auto;
}

.selectFrom .el-button,
.selectFrom .el-button:hover,
.selectFrom .el-button:focus,
.exitbtn,
.exitbtn:hover,
.exitbtn:focus {
  background-color: transparent;
  color: gray;
}

.el-dialog .el-button {
  width: 20%;
  margin: 5% 15%;
}
</style>