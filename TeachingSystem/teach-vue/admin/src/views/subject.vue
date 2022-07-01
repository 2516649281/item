<template>
  <el-container>
    <el-header>
      <el-form :inline="true" :model="selectFrom">
        <el-form-item>
          <el-input
              v-model="selectFrom.subjectName"
              placeholder="键入科目名称"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
              type="primary"
              @click="
              selectAllSubjectByName(
                this.current,
                this.size,
                selectFrom.subjectName
              )
            "
          >查询
          </el-button
          >
        </el-form-item>
      </el-form>
    </el-header>
    <el-main>
      <el-table :data="tableData" height="400">
        <el-table-column
            label="科目编号"
            prop="subjectId"
            align="center"
            width="100"
            fixed="left"
        ></el-table-column>
        <el-table-column
            label="科目名称"
            prop="subjectName"
            align="center"
            width="200"
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
        <el-table-column label="状态" align="center" width="100">
          <template #default="scope">
            <el-tag
                :type="scope.row.log.deleted === 0 ? 'success' : 'danger'"
                disable-transitions
            >{{ scope.row.log.deleted == 0 ? "正常" : "已删除" }}
            </el-tag
            >
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" align="center" fixed="right">
          <template #default="scope"
          >
            <el-button
                type="primary"
                circle
                icon="el-icon-edit"
                @click="editClick(scope.row)"
            ></el-button>
            <el-button
                type="danger"
                circle
                icon="el-icon-delete"
                @click="deleteSubjectById(scope.row.subjectId, true)"
            >
            </el-button>
            <el-button
                type="success"
                circle
                icon="el-icon-refresh-right"
                @click="deleteSubjectById(scope.row.subjectId, false)"
            ></el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <div class="addbtn">
        <el-button
            type="success"
            circle
            icon="el-icon-plus"
            @click="adddialogFormVisible = true"
        ></el-button>
      </div>
      <el-pagination
          background
          @current-change="handleCurrentChange"
          layout="prev, pager, next"
          :total="pageSize"
          :page-size="size"
      >
      </el-pagination>
    </el-main>
  </el-container>
  <!-- 添加科目 -->
  <el-dialog title="添加科目" v-model="adddialogFormVisible">
    <el-form :model="addFrom" :rules="rules" ref="addFrom">
      <el-form-item label="科目名称:" prop="subjectName">
        <el-input v-model="addFrom.subjectName" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <template v-slot:footer>
      <el-button @click="adddialogFormVisible = false" round>取 消</el-button>
      <el-button type="primary" @click="addSubject(addFrom)" round
      >确 定
      </el-button
      >
    </template>
  </el-dialog>
  <!-- 修改科目 -->
  <el-dialog title="修改科目" v-model="editdialogFormVisible">
    <h1>你选择了{{ editFrom.subjectId }}号科目</h1>
    <el-form :model="editFrom" :rules="rules" ref="updateFrom">
      <el-form-item label="科目名称:" prop="subjectName">
        <el-input v-model="editFrom.subjectName" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <template v-slot:footer>
      <el-button @click="editdialogFormVisible = false" round>取 消</el-button>
      <el-button type="primary" @click="updateSubjectById(editFrom)" round
      >确 定
      </el-button
      >
    </template>
  </el-dialog>
</template>

<script>
import {
  selectAllSubject,
  selectAllSubjectByName,
  addSubject,
  updateSubjectById,
  deleteSubjectById,
} from "../api/subject";
export default {
  data() {
    return {
      //表格数据
      tableData: [],
      //查询数据
      selectFrom: {
        //名称
        subjectName: "",
      },
      //页码
      current: 1,
      //页长
      size: 10,
      //总数
      pageSize: "",
      //添加科目对话框显隐
      adddialogFormVisible: false,
      //编辑科目对话框
      editdialogFormVisible: false,
      //添加数据
      addFrom: {
        //科目名称
        subjectName: "",
      },
      //编辑数据
      editFrom: {
        //科目编号
        subjectId: "",
        //科目名称
        subjectName: "",
      },
      //表单验证
      rules: {
        subjectName: [
          {required: true, message: "科目名称不得为空!", trigger: "blur"},
        ],
      },
    };
  },
  created() {
    this.selectAllSubjectByName(
        this.current,
        this.size,
        this.selectFrom.subjectName
    );
  },
  methods: {
    //查询所有科目
    selectAllSubject(current, size) {
      selectAllSubject(current, size).then((req) => {
        this.tableData = req.data.data;
        this.pageSize = req.data.pageSize;
      });
    },
    //按照名称查询科目
    selectAllSubjectByName(current, size, subjectName) {
      selectAllSubjectByName(current, size, subjectName).then((req) => {
        this.tableData = req.data.data;
        this.pageSize = req.data.pageSize;
      });
    },
    //添加科目
    addSubject(obj) {
      this.$refs["addFrom"].validate((valid) => {
        if (valid) {
          //添加科目
          addSubject(obj).then((req) => {
            if ((req.data.statue === 200) & (req.data.data != null)) {
              this.$message({
                type: "success",
                message: "添加成功!",
                showClose: true,
              });
              this.selectAllSubjectByName(
                  this.current,
                  this.size,
                  this.selectFrom.subjectName
              );
              this.adddialogFormVisible = false;
            } else {
              this.$message({
                type: "error",
                message: "添加失败!",
                showClose: true,
              });
            }
          });
        } else {
          return false;
        }
      });
    },
    //修改科目
    updateSubjectById(obj) {
      this.$refs["updateFrom"].validate((valid) => {
        if (valid) {
          //修改科目
          updateSubjectById(obj).then((req) => {
            if ((req.data.statue === 200) & (req.data.data != null)) {
              this.$message({
                type: "success",
                message: "修改成功!",
                showClose: true,
              });
              this.selectAllSubjectByName(
                  this.current,
                  this.size,
                  this.selectFrom.subjectName
              );
              this.editdialogFormVisible = false;
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
    //删除科目
    deleteSubjectById(subjectId, index) {
      this.$confirm(
          index
              ? "此操作将删除该科目, 是否继续?"
              : "此操作将恢复该科目, 是否继续?",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }
      ).then(() => {
        deleteSubjectById(subjectId, index).then((req) => {
          if ((req.data.statue === 200) & (req.data.data != null)) {
            this.$message({
              type: "success",
              message: "操作成功!",
              showClose: true,
            });
            this.selectAllSubjectByName(
                this.current,
                this.size,
                this.selectFrom.subjectName
            );
          } else {
            this.$message({
              type: "error",
              message: req.data.message,
              showClose: true,
            });
          }
        });
      });
    },
    //编辑操作
    editClick(obj) {
      this.editFrom = obj;
      this.editdialogFormVisible = true;
    },
    // 分页
    handleCurrentChange(val) {
      this.current = val;
      this.selectAllSubjectByName(
          this.current,
          this.size,
          this.selectFrom.subjectName
      );
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
  color: greenyellow;
}
.addbtn {
  width: 10%;
  margin: 0 auto;
}

.el-dialog .el-button {
  width: 20%;
  margin: 5% 15%;
}

/deep/ .el-input__inner {
  background-color: transparent;
  /* color: white; */
}
</style>