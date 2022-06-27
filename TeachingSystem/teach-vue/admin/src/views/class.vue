<template>
  <el-container>
    <el-header>
      <el-form :inline="true" :model="selectFrom">
        <el-form-item>
          <el-input
              v-model="selectFrom.className"
              placeholder="键入班级名称"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
              type="primary"
              @click="
              selectAllClassByName(
                this.current,
                this.size,
                selectFrom.className
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
            label="班级编号"
            prop="classId"
            align="center"
            width="100"
            fixed="left"
        ></el-table-column>
        <el-table-column
            label="班级名称"
            prop="className"
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
                @click="deleteClassById(scope.row.classId, true)"
            >
            </el-button>
            <el-button
                type="success"
                circle
                icon="el-icon-refresh-right"
                @click="deleteClassById(scope.row.classId, false)"
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
  <!-- 添加班级 -->
  <el-dialog title="添加班级" v-model="adddialogFormVisible">
    <el-form :model="addFrom" :rules="rules" ref="addFrom">
      <el-form-item label="班级名称:" prop="className">
        <el-input v-model="addFrom.className" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <template v-slot:footer>
      <el-button @click="adddialogFormVisible = false" round>取 消</el-button>
      <el-button type="primary" @click="addClass(addFrom)" round
      >确 定
      </el-button
      >
    </template>
  </el-dialog>
  <!-- 修改班级 -->
  <el-dialog title="修改班级" v-model="editdialogFormVisible">
    <h1>你选择了{{ editFrom.classId }}号班级</h1>
    <el-form :model="editFrom" :rules="rules" ref="updateFrom">
      <el-form-item label="班级名称:" prop="className">
        <el-input v-model="editFrom.className" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <template v-slot:footer>
      <el-button @click="editdialogFormVisible = false" round>取 消</el-button>
      <el-button type="primary" @click="updateClassById(editFrom)" round
      >确 定
      </el-button
      >
    </template>
  </el-dialog>
</template>

<script>
import {
  selectAllClass,
  selectAllClassByName,
  addClass,
  updateClassById,
  deleteClassById,
} from "../api/class";

export default {
  data() {
    return {
      //表格数据
      tableData: [],
      //查询数据
      selectFrom: {
        //名称
        className: "",
      },
      //页码
      current: 1,
      //页长
      size: 10,
      //总数
      pageSize: "",
      //添加班级对话框显隐
      adddialogFormVisible: false,
      //编辑班级对话框
      editdialogFormVisible: false,
      //添加数据
      addFrom: {
        //班级名称
        className: "",
      },
      //编辑数据
      editFrom: {
        //班级编号
        classId: "",
        //班级名称
        className: "",
      },
      //表单验证
      rules: {
        className: [
          {required: true, message: "班级名称不得为空!", trigger: "blur"},
        ],
      },
    };
  },
  created() {
    this.selectAllClassByName(
        this.current,
        this.size,
        this.selectFrom.className
    );
  },
  methods: {
    //查询所有班级
    selectAllClass(current, size) {
      selectAllClass(current, size).then((req) => {
        this.tableData = req.data.data;
        this.pageSize = req.data.pageSize;
      });
    },
    //按照名称查询班级
    selectAllClassByName(current, size, className) {
      selectAllClassByName(current, size, className).then((req) => {
        this.tableData = req.data.data;
        this.pageSize = req.data.pageSize;
      });
    },
    //添加班级
    addClass(obj) {
      this.$refs["addFrom"].validate((valid) => {
        if (valid) {
          //添加班级
          addClass(obj).then((req) => {
            if ((req.data.statue === 200) & (req.data.data != null)) {
              this.$message({
                type: "success",
                message: "添加成功!",
                showClose: true,
              });
              this.selectAllClassByName(
                  this.current,
                  this.size,
                  this.selectFrom.className
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
    //修改班级
    updateClassById(obj) {
      this.$refs["updateFrom"].validate((valid) => {
        if (valid) {
          //修改班级
          updateClassById(obj).then((req) => {
            if ((req.data.statue === 200) & (req.data.data != null)) {
              this.$message({
                type: "success",
                message: "修改成功!",
                showClose: true,
              });
              this.selectAllClassByName(
                  this.current,
                  this.size,
                  this.selectFrom.className
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
    //删除班级
    deleteClassById(classId, index) {
      this.$confirm(
          index
              ? "此操作将删除该班级, 是否继续?"
              : "此操作将恢复该班级, 是否继续?",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }
      ).then(() => {
        deleteClassById(classId, index).then((req) => {
          if ((req.data.statue === 200) & (req.data.data != null)) {
            this.$message({
              type: "success",
              message: "操作成功!",
              showClose: true,
            });
            this.selectAllClassByName(
                this.current,
                this.size,
                this.selectFrom.className
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
    // 分页
    handleCurrentChange(val) {
      this.current = val;
      this.selectAllClassByName(
          this.current,
          this.size,
          this.selectFrom.className
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