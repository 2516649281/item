<template>
  <el-container>
    <el-main>
      <el-table
        :data="tableData"
        height="400"
        ref="workIds"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55"> </el-table-column>
        <el-table-column type="expand" fixed>
          <template #default="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="作业描述:">
                <span>{{ props.row.workContent }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column
          label="编号"
          prop="workId"
          align="center"
          width="150"
          fixed
        ></el-table-column>
        <el-table-column
          label="作业名"
          prop="workName"
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
              >{{ scope.row.log.deleted == 0 ? "正常" : "已删除" }}</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" align="center" fixed="right">
          <template #default="scope"
            ><el-button
              type="primary"
              circle
              icon="el-icon-edit"
              @click="editClick(scope.row)"
            ></el-button>
            <el-button
              type="danger"
              circle
              icon="el-icon-delete"
              @click="deleteWork(scope.row.workId, true)"
            >
            </el-button>
            <el-button
              type="success"
              circle
              icon="el-icon-refresh-right"
              @click="deleteWork(scope.row.workId, false)"
            ></el-button
          ></template>
        </el-table-column>
      </el-table>
      <div class="addbtn">
        <el-button
          type="success"
          icon="el-icon-plus"
          @click="adddialogFormVisible = true"
          >添加作业</el-button
        >
        <el-button
          type="danger"
          @click="deleteClasss(this.workIds, true)"
          icon="el-icon-delete"
          >批量删除</el-button
        >
        <el-button
          type="success"
          @click="deleteClasss(this.workIds, false)"
          icon="el-icon-refresh-right"
          >批量恢复</el-button
        >
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
  <!-- 添加作业 -->
  <el-dialog title="添加作业" v-model="adddialogFormVisible">
    <el-form :model="addFrom" :rules="rules" ref="addFrom">
      <el-form-item label="作业名称:" prop="workName">
        <el-input v-model="addFrom.workName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="发布班级" prop="workId"
        ><el-select v-model="addFrom.workId" placeholder="请选择">
          <el-option
            v-for="item in classMap"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option> </el-select
      ></el-form-item>
      <el-form-item label="作业描述:" prop="workContent">
        <el-input
          v-model="addFrom.workContent"
          type="textarea"
          :rows="2"
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
  <!-- 修改作业 -->
  <el-dialog title="修改作业" v-model="editdialogFormVisible">
    <h1>你选择了{{ editFrom.workId }}号作业</h1>
    <el-form :model="editFrom" :rules="rules" ref="updateFrom">
      <el-form-item label="作业名称:" prop="workName">
        <el-input v-model="editFrom.workName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="发布班级" prop="workId"
        ><el-select v-model="editFrom.workId" placeholder="请选择">
          <el-option
            v-for="item in classMap"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option> </el-select
      ></el-form-item>
      <el-form-item label="作业描述:" prop="workContent">
        <el-input
          v-model="editFrom.workContent"
          type="textarea"
          :rows="2"
        ></el-input>
      </el-form-item>
    </el-form>
    <template v-slot:footer>
      <el-button @click="editdialogFormVisible = false" round>取 消</el-button>
      <el-button type="primary" @click="editWork(editFrom)" round
        >确 定</el-button
      >
    </template>
  </el-dialog>
</template>

<script>
import jwtDecode from "jwt-decode";
import {
  selectAllWorkByTeacherId,
  addWork,
  updateWorkById,
  deleteWork,
  selectAllClass,
} from "../api/work";
export default {
  data() {
    return {
      //表格数据
      tableData: [],
      //班级展示数据
      classMap: [],
      //页码
      current: 1,
      //页长
      size: 10,
      //总数
      pageSize: "",
      //添加作业对话框显隐
      adddialogFormVisible: false,
      //编辑作业对话框
      editdialogFormVisible: false,
      //添加数据
      addFrom: {
        //作业名
        workName: "",
        //作业描述
        workContent: "",
        //班级编号
        workId: "",
        //教师编号
        teacherId: "",
      },
      //班级编号
      workIds: [],
      //删除数据
      map: [],
      //编辑数据
      editFrom: {
        //作业编号
        workId: "",
        //作业名
        workName: "",
        //作业描述
        workContent: "",
        //班级编号
        workId: "",
        //教师编号
        teacherId: "",
      },
      //表单验证
      rules: {
        workName: [
          { required: true, message: "作业名不得为空!", trigger: "blur" },
        ],
        workId: [{ required: true, message: "请选择班级!", trigger: "change" }],
        workContent: [
          { required: true, message: "作业描述不得为空!", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.selectAllWorkByTeacherId(
      this.current,
      this.size,
      jwtDecode(sessionStorage.getItem("token")).user.user.teacherId
    );
    this.selectAllClass();
  },
  methods: {
    //查询所有班级
    selectAllClass() {
      selectAllClass().then((req) => {
        var classList = req.data.data;
        for (let i = 0; i < classList.length; i++) {
          var a = {
            value: classList[i].workId,
            label: classList[i].className,
          };
          this.classMap.push(a);
        }
      });
    },
    //根据教师编号查询已布置的作业
    selectAllWorkByTeacherId(current, size, teacherId) {
      selectAllWorkByTeacherId(current, size, teacherId).then((req) => {
        this.tableData = req.data.data;
        this.pageSize = req.data.pageSize;
      });
    },
    //添加作业
    addWork(obj) {
      this.$refs["addFrom"].validate((valid) => {
        if (valid) {
          obj.teacherId = jwtDecode(
            sessionStorage.getItem("token")
          ).user.user.teacherId;
          addWork(obj).then((req) => {
            if ((req.data.statue === 200) & (req.data.data != null)) {
              this.$message({
                type: "success",
                message: "添加成功!",
                showClose: true,
              });
              this.selectAllWorkByTeacherId(
                this.current,
                this.size,
                jwtDecode(sessionStorage.getItem("token")).user.user.teacherId
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
    //修改作业
    editWork(obj) {
      this.$refs["updateFrom"].validate((valid) => {
        if (valid) {
          updateWorkById(obj).then((req) => {
            if ((req.data.statue === 200) & (req.data.data != null)) {
              this.$message({
                type: "success",
                message: "修改成功!",
                showClose: true,
              });
              this.selectAllWorkByTeacherId(
                this.current,
                this.size,
                jwtDecode(sessionStorage.getItem("token")).user.user.teacherId
              );
              this.editdialogFormVisible = false;
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
    //删除作业
    deleteWork(workId, index) {
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
        this.map[workId] = index;
        deleteWork(this.map).then((req) => {
          if ((req.data.statue === 200) & (req.data.data != null)) {
            this.$message({
              type: "success",
              message: "操作成功!",
              showClose: true,
            });
            this.selectAllWorkByTeacherId(
              this.current,
              this.size,
              jwtDecode(sessionStorage.getItem("token")).user.user.teacherId
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
    //编辑操作
    editClick(obj) {
      this.editFrom = obj;
      this.editdialogFormVisible = true;
    },
    //批量删除
    deleteClasss(ids, index) {
      this.$confirm(
        index ? "此操作将删除班级, 是否继续?" : "此操作将恢复班级, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).then(() => {
        this.map = new Map();
        for (let i = 0; i < ids.length; i++) {
          this.map[ids[i].workId] = index;
        }
        deleteWork(this.map).then((req) => {
          if ((req.data.statue === 200) & (req.data.data != null)) {
            this.$message({
              type: "success",
              message: "操作成功!",
              showClose: true,
            });
            this.selectAllWorkByTeacherId(
              this.current,
              this.size,
              jwtDecode(sessionStorage.getItem("token")).user.user.teacherId
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
      this.workIds = val;
    },
    // 分页
    handleCurrentChange(val) {
      this.current = val;
      this.selectAllWorkByTeacherId(
        this.current,
        this.size,
        jwtDecode(sessionStorage.getItem("token")).user.user.teacherId
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
  color: white;
  color: grey;
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