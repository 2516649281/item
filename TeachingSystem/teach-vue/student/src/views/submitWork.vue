<template>
  <el-container>
    <el-main
      ><el-table
        :data="tableData"
        height="410"
        id="table"
        ref="submitWorkIds"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column type="expand" align="center" fixed
          ><template #default="props">
            <el-form label-position="left" inline>
              <el-form-item label="我提交的作业:">
                <span>{{ props.row.submitContent }}</span>
              </el-form-item>
            </el-form>
          </template></el-table-column
        >
        <el-table-column
          align="center"
          label="提交编号"
          prop="submitId"
          width="100"
          fixed
        ></el-table-column>
        <el-table-column
          align="center"
          label="作业编号"
          prop="workId"
          width="100"
        ></el-table-column>
        <el-table-column
          align="center"
          label="分数"
          prop="submitScore"
          width="150"
        ></el-table-column>
        <el-table-column
          align="center"
          label="提交时间"
          prop="log.createTime"
          width="250"
        ></el-table-column>
        <el-table-column
          align="center"
          label="修改时间"
          prop="log.updateTime"
          width="250"
        ></el-table-column>
        <el-table-column label="状态" width="200" align="center">
          <template #default="scope">
            <el-tag
              :type="scope.row.log.deleted === 0 ? 'success' : 'danger'"
              >{{ scope.row.log.deleted === 0 ? "正常" : "已删除" }}</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right" align="center">
          <template #default="scope">
            <el-button
              icon="el-icon-edit"
              circle
              type="primary"
              title="修改作业"
              @click="editClick(scope.row)"
            ></el-button>
            <el-button
              icon="el-icon-delete"
              circle
              type="danger"
              title="删除作业"
              @click="deleteWork(scope.row.submitId, true)"
            ></el-button>
            <el-button
              icon="el-icon-refresh-left"
              circle
              type="success"
              title="恢复作业"
              @click="deleteWork(scope.row.submitId, false)"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="addbtn">
        <el-button
          type="success"
          icon="el-icon-plus"
          @click="this.adddialogFormVisible = true"
          >添加作业</el-button
        >
        <el-button
          type="danger"
          @click="deleteSubmitWorks(this.subjectWorkIds, true)"
          icon="el-icon-delete"
          >批量删除</el-button
        >
        <el-button
          type="success"
          @click="deleteSubmitWorks(this.subjectWorkIds, false)"
          icon="el-icon-refresh-left"
          >批量恢复</el-button
        >
      </div>
    </el-main>
    <el-footer>
      <div class="page">
        <el-pagination
          background
          layout="prev, pager, next"
          :total="this.pageSize"
          :page-size="this.size"
          @current-change="handleCurrentChange"
        >
        </el-pagination></div
    ></el-footer>
  </el-container>
  <el-dialog title="修改作业" v-model="editdialogFormVisible">
    <el-form
      :model="editFrom"
      :rules="rules"
      @keyup.enter.native="updateWork(editFrom)"
      ref="updateFrom"
    >
      <h3>注意:当前选择的提交编号:{{ editFrom.submitId }}</h3>
      <el-form-item label="需完成的作业编号:" prop="workId">
        <el-input v-model="editFrom.workId" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item
        label="修改你的作业内容:"
        :label-width="formLabelWidth"
        prop="submitContent"
      >
        <el-input
          v-model="editFrom.submitContent"
          autocomplete="off"
          type="textarea"
        ></el-input>
      </el-form-item>
    </el-form>
    <template v-slot:footer>
      <el-button @click="editdialogFormVisible = false" round>取 消</el-button>
      <el-button type="primary" @click="updateWork(editFrom)" round
        >确 定</el-button
      >
    </template>
  </el-dialog>
  <el-dialog title="添加作业" v-model="adddialogFormVisible">
    <el-form
      :model="addFrom"
      :rules="rules"
      @keyup.enter.native="addWork(addFrom)"
      ref="addFrom"
    >
      <el-form-item label="作业编号:" prop="workId">
        <el-input v-model="addFrom.workId" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item
        label="作业内容:"
        :label-width="formLabelWidth"
        prop="submitContent"
      >
        <el-input
          v-model="addFrom.submitContent"
          autocomplete="off"
          type="textarea"
        ></el-input>
      </el-form-item>
    </el-form>
    <template v-slot:footer>
      <el-button @click="adddialogFormVisible = false" round>取 消</el-button>
      <el-button type="primary" @click="addWork(addFrom)" round
        >确 定</el-button
      >
    </template>
  </el-dialog>
</template>

<script>
import jwtDecode from "jwt-decode";
import {
  selectAllWorkByStudentId,
  addWork,
  updateWork,
  deleteWork,
} from "../api/work";
export default {
  data() {
    return {
      //表格数据
      tableData: [],
      //页码
      current: 1,
      //页长
      size: 10,
      //分页大小
      pageSize: "",
      //编辑框
      editdialogFormVisible: false,
      //添加框
      adddialogFormVisible: false,
      //编辑数据
      editFrom: {
        //提交编号
        submitId: "",
        //作业编号
        workId: "",
        //作业内容
        submitContent: "",
      },
      //删除数据
      map: [],
      //提交作业id
      submitWorkIds: [],
      //添加数据
      addFrom: {
        //学生编号
        studentId: "",
        //作业编号
        workId: "",
        //提交的作业
        submitContent: "",
      },
      rules: {
        workId: [
          { required: true, message: "请输入作业编号", trigger: "blur" },
        ],
        submitContent: [
          { required: true, message: "请输入你要提交的作业", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.addFrom.studentId = jwtDecode(
      sessionStorage.getItem("token")
    ).user.user.studentId;
    this.selectAllWorkByStudentId(
      this.current,
      this.size,
      this.addFrom.studentId
    );
    this.mounted();
  },
  methods: {
    //查询所有已完成的作业
    selectAllWorkByStudentId(current, size, studentId) {
      selectAllWorkByStudentId(current, size, studentId).then((req) => {
        this.tableData = req.data.data;
        this.pageSize = req.data.pageSize;
      });
    },
    //添加作业
    addWork(Source) {
      this.$refs["addFrom"].validate((valid) => {
        if (valid) {
          addWork(Source).then((req) => {
            if ((req.data.statue === 200) & (req.data.data != null)) {
              this.$message({
                showClose: true,
                type: "success",
                message: "添加成功",
              });
              this.selectAllWorkByStudentId(
                this.current,
                this.size,
                this.addFrom.studentId
              );
              this.adddialogFormVisible = false;
            } else {
              this.$message({
                type: "error",
                showClose: true,
                message: "添加失败!",
              });
            }
          });
        } else {
          return false;
        }
      });
    },
    // 修改作业
    updateWork(Source) {
      this.$refs["updateFrom"].validate((valid) => {
        if (valid) {
          updateWork(Source).then((req) => {
            if ((req.data.statue === 200) & (req.data.data != null)) {
              this.$message({
                showClose: true,
                type: "success",
                message: "修改成功",
              });
              this.selectAllWorkByStudentId(
                this.current,
                this.size,
                jwtDecode(sessionStorage.getItem("token")).user.user.studentId
              );
              this.editdialogFormVisible = false;
            } else {
              this.$message({
                showClose: true,
                type: "error",
                message: "修改失败!",
              });
            }
          });
        } else {
          return false;
        }
      });
    },
    // 删除作业
    deleteWork(submitId, index) {
      this.$confirm(
        index
          ? "此操作将删除该作业, 是否继续?"
          : "此操作将恢复该作业, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).then(() => {
        this.map = new Map();
        this.map[submitId] = index;
        deleteWork(this.map).then((req) => {
          if ((req.data.statue === 200) & (req.data.data != null)) {
            this.$message({
              showClose: true,
              type: "success",
              message: "操作成功",
            });
            this.selectAllWorkByStudentId(
              this.current,
              this.size,
              jwtDecode(sessionStorage.getItem("token")).user.user.studentId
            );
          } else {
            this.$message({
              showClose: true,
              type: "error",
              message: "操作失败!",
            });
          }
        });
      });
    },
    //批量删除
    deleteSubmitWorks(ids, index) {
      this.$confirm(
        index ? "此操作将删除作业, 是否继续?" : "此操作将恢复作业, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).then(() => {
        this.map = new Map();
        for (let i = 0; i < ids.length; i++) {
          this.map[ids[i].submitId] = index;
        }
        deleteWork(this.map).then((req) => {
          if ((req.data.statue === 200) & (req.data.data != null)) {
            this.$message({
              type: "success",
              message: "操作成功!",
              showClose: true,
            });
            this.selectAllWorkByStudentId(
              this.current,
              this.size,
              jwtDecode(sessionStorage.getItem("token")).user.user.studentId
            );
          } else {
            this.$message({
              type: "error",
              message: "操作失败!",
              showClose: true,
            });
          }
        });
      });
    },
    //获取打勾的项
    handleSelectionChange(val) {
      this.subjectWorkIds = val;
    },
    editClick(Source) {
      this.editFrom = Source;
      this.editdialogFormVisible = true;
    },
    // 分页
    handleCurrentChange(val) {
      this.current = val;
      this.selectAllWorkByStudentId(
        this.current,
        this.size,
        this.addFrom.studentId
      );
    },
    mounted() {
      setInterval(() => {
        document
          .getElementById("table")
          .classList.remove("el-table--enable-row-hover");
      });
    },
  },
};
</script>
<style scoped>
.el-pagination {
  width: 60%;
  margin: 0 auto;
}
/deep/ .el-table,
/deep/ .el-table__expanded-cell {
  background-color: transparent;
}
/deep/ .el-table th,
/deep/ .el-table tr,
/deep/ .el-table td {
  background-color: transparent;
  color: grey;
}
.el-tabls {
  color: white;
}
.addbtn {
  width: 80%;
  margin: 0 auto;
}
.el-dialog .el-button {
  width: 20%;
  margin: 5% 15%;
}
</style>