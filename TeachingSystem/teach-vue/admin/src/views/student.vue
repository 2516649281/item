<template>
  <el-container>
    <el-header>
      <el-form :inline="true" :model="selectFrom">
        <el-form-item>
          <el-select v-model="selectFrom.classId" placeholder="请选择班级">
            <el-option
                v-for="item in classMap"
                :key="item.value"
                :label="item.label"
                :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button
              type="primary"
              @click="
              selectAllStudentByClass(
                this.current,
                this.size,
                selectFrom.classId
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
            label="学生编号"
            prop="studentId"
            align="center"
            width="100"
            fixed="left"
        ></el-table-column>
        <el-table-column
            label="学生姓名"
            prop="studentName"
            align="center"
            width="200"
        ></el-table-column>
        <el-table-column label="学生年龄" align="center" width="200">
          <template #default="scope"> {{ scope.row.studentAge }}岁</template>
        </el-table-column>
        <el-table-column label="学生性别" align="center" width="200">
          <template #default="scope">
            {{ scope.row.studentGender === 0 ? "女" : "男" }}
          </template>
        </el-table-column>
        <el-table-column
            label="学生住址"
            prop="studentAddress"
            align="center"
            width="200"
        ></el-table-column>
        <el-table-column
            label="学生电话"
            prop="studentPhone"
            align="center"
            width="200"
        ></el-table-column>
        <el-table-column
            label="学生邮箱"
            prop="studentEmail"
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
                @click="deleteStudentById(scope.row.studentId, true)"
            >
            </el-button>
            <el-button
                type="success"
                circle
                icon="el-icon-refresh-right"
                @click="deleteStudentById(scope.row.studentId, false)"
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
  <!-- 添加学生 -->
  <el-dialog title="添加学生" v-model="adddialogFormVisible">
    <el-form :model="addFrom" :rules="rules" ref="addFrom">
      <el-form-item label="学生姓名:" prop="studentName">
        <el-input v-model="addFrom.studentName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="学生年龄:" prop="studentAge">
        <el-input v-model="addFrom.studentAge" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="学生性别:" prop="studentGender">
        <el-radio-group v-model="addFrom.studentGender">
          <el-radio :label="1">男</el-radio>
          <el-radio :label="0">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="学生住址:" prop="studentAddress">
        <el-input
            v-model="addFrom.studentAddress"
            autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item label="学生电话:" prop="studentPhone">
        <el-input v-model="addFrom.studentPhone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="学生邮箱:" prop="studentEmail">
        <el-input v-model="addFrom.studentEmail" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="所在班级" prop="classId"
      >
        <el-select v-model="addFrom.classId" placeholder="请选择">
          <el-option
              v-for="item in classMap"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          >
          </el-option>
        </el-select
        >
      </el-form-item>
    </el-form>
    <template v-slot:footer>
      <el-button @click="adddialogFormVisible = false" round>取 消</el-button>
      <el-button type="primary" @click="addStudent(addFrom)" round
      >确 定
      </el-button
      >
    </template>
  </el-dialog>
  <!-- 修改学生 -->
  <el-dialog title="修改学生" v-model="editdialogFormVisible">
    <h1>你选择了{{ editFrom.studentId }}号学生</h1>
    <el-form :model="editFrom" :rules="rules" ref="updateFrom">
      <el-form-item label="学生姓名:" prop="studentName">
        <el-input v-model="editFrom.studentName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="学生年龄:" prop="studentAge">
        <el-input v-model="editFrom.studentAge" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="学生性别:" prop="studentGender">
        <el-radio-group v-model="editFrom.studentGender">
          <el-radio :label="1">男</el-radio>
          <el-radio :label="0">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="学生住址:" prop="studentAddress">
        <el-input
            v-model="editFrom.studentAddress"
            autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item label="学生电话:" prop="studentPhone">
        <el-input v-model="editFrom.studentPhone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="学生邮箱:" prop="studentEmail">
        <el-input v-model="editFrom.studentEmail" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="所在班级" prop="classId"
      >
        <el-select v-model="editFrom.classId" placeholder="请选择">
          <el-option
              v-for="item in classMap"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          >
          </el-option>
        </el-select
        >
      </el-form-item>
    </el-form>
    <template v-slot:footer>
      <el-button @click="editdialogFormVisible = false" round>取 消</el-button>
      <el-button type="primary" @click="updateStudentById(editFrom)" round
      >确 定
      </el-button
      >
    </template>
  </el-dialog>
</template>

<script>
import {
  selectAllStudent,
  selectAllStudentByClass,
  addStudent,
  updateStudentById,
  deleteStudentById,
} from "../api/student";
import {selectAllClass} from "../api/class";

export default {
  data() {
    return {
      //表格数据
      tableData: [],
      //查询数据
      selectFrom: {
        //班级名称
        classId: "",
      },
      //班级展示数据
      classMap: [],
      //页码
      current: 1,
      //页长
      size: 10,
      //总数
      pageSize: "",
      //添加学生对话框显隐
      adddialogFormVisible: false,
      //编辑学生对话框
      editdialogFormVisible: false,
      //添加数据
      addFrom: {
        //学生姓名
        studentName: "",
        //学生年龄
        studentAge: "",
        //学生性别
        studentGender: "",
        //学生住址
        studentAddress: "",
        //学生电话
        studentPhone: "",
        //学生邮箱
        studentEmail: "",
      },
      //编辑数据
      editFrom: {
        //学生编号
        studentId: "",
        //学生姓名
        studentName: "",
        //学生年龄
        studentAge: "",
        //学生性别
        studentGender: "",
        //学生住址
        studentAddress: "",
        //学生电话
        studentPhone: "",
        //学生邮箱
        studentEmail: "",
      },
      //表单验证
      rules: {
        studentName: [
          {required: true, message: "学生姓名不得为空!", trigger: "blur"},
        ],
        studentAge: [
          {required: true, message: "学生年龄不得为空!", trigger: "blur"},
        ],
        studentGender: [
          {required: true, message: "学生性别不得为空!", trigger: "blur"},
        ],
        studentAddress: [
          {required: true, message: "学生地址不得为空!", trigger: "blur"},
        ],
        studentPhone: [
          {required: true, message: "学生电话不得为空!", trigger: "blur"},
        ],
        studentEmail: [
          {required: true, message: "学生邮箱不得为空!", trigger: "blur"},
        ],
        classId: [
          {required: true, message: "班级不得为空!", trigger: "blur"},
        ],
      },
    };
  },
  created() {
    this.selectAllStudent(this.current, this.size);
    this.selectAllClass(1, 10000);
  },
  methods: {
    //获取所有班级
    selectAllClass(current, size) {
      selectAllClass(current, size).then((req) => {
        var classList = req.data.data;
        for (let i = 0; i < classList.length; i++) {
          var a = {
            value: classList[i].classId,
            label: classList[i].className,
          };
          this.classMap.push(a);
        }
      });
    },
    //查询所有学生
    selectAllStudent(current, size) {
      selectAllStudent(current, size).then((req) => {
        this.tableData = req.data.data;
        this.pageSize = req.data.pageSize;
      });
    },
    //按照班级查询学生
    selectAllStudentByClass(current, size, className) {
      selectAllStudentByClass(current, size, className).then((req) => {
        this.tableData = req.data.data;
        this.pageSize = req.data.pageSize;
      });
    },
    //添加学生
    addStudent(obj) {
      this.$refs["addFrom"].validate((valid) => {
        if (valid) {
          //添加学生
          addStudent(obj).then((req) => {
            if ((req.data.statue === 200) & (req.data.data != null)) {
              this.$message({
                type: "success",
                message: "添加成功!",
                showClose: true,
              });
              this.selectAllStudentByClass(
                  this.current,
                  this.size,
                  this.selectFrom.classId
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
    //修改学生
    updateStudentById(obj) {
      this.$refs["updateFrom"].validate((valid) => {
        if (valid) {
          //修改学生
          updateStudentById(obj).then((req) => {
            if ((req.data.statue === 200) & (req.data.data != null)) {
              this.$message({
                type: "success",
                message: "修改成功!",
                showClose: true,
              });
              this.selectAllStudentByClass(
                  this.current,
                  this.size,
                  this.selectFrom.classId
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
    //删除学生
    deleteStudentById(studentId, index) {
      this.$confirm(
          index
              ? "此操作将删除该学生, 是否继续?"
              : "此操作将恢复该学生, 是否继续?",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }
      ).then(() => {
        deleteStudentById(studentId, index).then((req) => {
          if ((req.data.statue === 200) & (req.data.data != null)) {
            this.$message({
              type: "success",
              message: "操作成功!",
              showClose: true,
            });
            this.selectAllStudentByClass(
                this.current,
                this.size,
                this.selectFrom.classId
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
      this.selectAllStudentByClass(
          this.current,
          this.size,
          this.selectFrom.classId
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