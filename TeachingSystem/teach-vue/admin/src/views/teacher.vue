<template>
  <el-container>
    <el-header>
      <el-form :inline="true" :model="selectFrom">
        <el-form-item>
          <el-select v-model="selectFrom.subjectId" placeholder="请选择科目">
            <el-option
                v-for="item in subjectMap"
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
              selectAllTeacherBySubject(
                this.current,
                this.size,
                selectFrom.subjectId
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
            label="教师编号"
            prop="teacherId"
            align="center"
            width="100"
            fixed="left"
        ></el-table-column>
        <el-table-column
            label="教师姓名"
            prop="teacherName"
            align="center"
            width="200"
        ></el-table-column>
        <el-table-column label="教师年龄" align="center" width="200">
          <template #default="scope"> {{ scope.row.teacherAge }}岁</template>
        </el-table-column>
        <el-table-column label="教师性别" align="center" width="200">
          <template #default="scope">
            {{ scope.row.teacherGender === 0 ? "女" : "男" }}
          </template>
        </el-table-column>
        <el-table-column
            label="教师住址"
            prop="teacherAddress"
            align="center"
            width="200"
        ></el-table-column>
        <el-table-column
            label="教师电话"
            prop="teacherPhone"
            align="center"
            width="200"
        ></el-table-column>
        <el-table-column
            label="教师邮箱"
            prop="teacherEmail"
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
                @click="deleteTeacherById(scope.row.teacherId, true)"
            >
            </el-button>
            <el-button
                type="success"
                circle
                icon="el-icon-refresh-right"
                @click="deleteTeacherById(scope.row.teacherId, false)"
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
  <!-- 添加教师 -->
  <el-dialog title="添加教师" v-model="adddialogFormVisible">
    <el-form :model="addFrom" :rules="rules" ref="addFrom">
      <el-form-item label="教师姓名:" prop="teacherName">
        <el-input v-model="addFrom.teacherName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="教师年龄:" prop="teacherAge">
        <el-input v-model="addFrom.teacherAge" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="教师性别:" prop="teacherGender">
        <el-radio-group v-model="addFrom.teacherGender">
          <el-radio :label="1">男</el-radio>
          <el-radio :label="0">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="教师住址:" prop="teacherAddress">
        <el-input
            v-model="addFrom.teacherAddress"
            autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item label="教师电话:" prop="teacherPhone">
        <el-input v-model="addFrom.teacherPhone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="教师邮箱:" prop="teacherEmail">
        <el-input v-model="addFrom.teacherEmail" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="所在科目" prop="subjectId"
      >
        <el-select v-model="addFrom.subjectId" placeholder="请选择">
          <el-option
              v-for="item in subjectMap"
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
      <el-button type="primary" @click="addTeacher(addFrom)" round
      >确 定
      </el-button
      >
    </template>
  </el-dialog>
  <!-- 修改教师 -->
  <el-dialog title="修改教师" v-model="editdialogFormVisible">
    <h1>你选择了{{ editFrom.teacherId }}号教师</h1>
    <el-form :model="editFrom" :rules="rules" ref="updateFrom">
      <el-form-item label="教师姓名:" prop="teacherName">
        <el-input v-model="editFrom.teacherName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="教师年龄:" prop="teacherAge">
        <el-input v-model="editFrom.teacherAge" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="教师性别:" prop="teacherGender">
        <el-radio-group v-model="editFrom.teacherGender">
          <el-radio :label="1">男</el-radio>
          <el-radio :label="0">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="教师住址:" prop="teacherAddress">
        <el-input
            v-model="editFrom.teacherAddress"
            autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item label="教师电话:" prop="teacherPhone">
        <el-input v-model="editFrom.teacherPhone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="教师邮箱:" prop="teacherEmail">
        <el-input v-model="editFrom.teacherEmail" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="所在科目" prop="subjectId"
      >
        <el-select v-model="editFrom.subjectId" placeholder="请选择">
          <el-option
              v-for="item in subjectMap"
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
      <el-button type="primary" @click="updateTeacherById(editFrom)" round
      >确 定
      </el-button
      >
    </template>
  </el-dialog>
</template>

<script>
import {
  selectAllTeacher,
  selectAllTeacherBySubject,
  addTeacher,
  updateTeacherById,
  deleteTeacherById,
} from "../api/teacher";
import {selectAllSubject} from "../api/subject";

export default {
  data() {
    return {
      //表格数据
      tableData: [],
      //查询数据
      selectFrom: {
        //科目名称
        subjectId: "",
      },
      //科目展示数据
      subjectMap: [],
      //页码
      current: 1,
      //页长
      size: 10,
      //总数
      pageSize: "",
      //添加教师对话框显隐
      adddialogFormVisible: false,
      //编辑教师对话框
      editdialogFormVisible: false,
      //添加数据
      addFrom: {
        //教师姓名
        teacherName: "",
        //教师年龄
        teacherAge: "",
        //教师性别
        teacherGender: "",
        //教师住址
        teacherAddress: "",
        //教师电话
        teacherPhone: "",
        //教师邮箱
        teacherEmail: "",
      },
      //编辑数据
      editFrom: {
        //教师编号
        teacherId: "",
        //教师姓名
        teacherName: "",
        //教师年龄
        teacherAge: "",
        //教师性别
        teacherGender: "",
        //教师住址
        teacherAddress: "",
        //教师电话
        teacherPhone: "",
        //教师邮箱
        teacherEmail: "",
      },
      //表单验证
      rules: {
        teacherName: [
          {required: true, message: "教师姓名不得为空!", trigger: "blur"},
        ],
        teacherAge: [
          {required: true, message: "教师年龄不得为空!", trigger: "blur"},
        ],
        teacherGender: [
          {required: true, message: "教师性别不得为空!", trigger: "blur"},
        ],
        teacherAddress: [
          {required: true, message: "教师地址不得为空!", trigger: "blur"},
        ],
        teacherPhone: [
          {required: true, message: "教师电话不得为空!", trigger: "blur"},
        ],
        teacherEmail: [
          {required: true, message: "教师邮箱不得为空!", trigger: "blur"},
        ],
        subjectId: [
          {required: true, message: "科目不得为空!", trigger: "blur"},
        ],
      },
    };
  },
  created() {
    this.selectAllTeacher(this.current, this.size);
    this.selectAllSubject(1, 10000);
  },
  methods: {
    //获取所有科目
    selectAllSubject(current, size) {
      selectAllSubject(current, size).then((req) => {
        var classList = req.data.data;
        for (let i = 0; i < classList.length; i++) {
          var a = {
            value: classList[i].subjectId,
            label: classList[i].subjectName,
          };
          this.subjectMap.push(a);
        }
      });
    },
    //查询所有教师
    selectAllTeacher(current, size) {
      selectAllTeacher(current, size).then((req) => {
        this.tableData = req.data.data;
        this.pageSize = req.data.pageSize;
      });
    },
    //按照科目查询教师
    selectAllTeacherBySubject(current, size, subjectId) {
      selectAllTeacherBySubject(current, size, subjectId).then((req) => {
        this.tableData = req.data.data;
        this.pageSize = req.data.pageSize;
      });
    },
    //添加教师
    addTeacher(obj) {
      this.$refs["addFrom"].validate((valid) => {
        if (valid) {
          //添加教师
          addTeacher(obj).then((req) => {
            if ((req.data.statue === 200) & (req.data.data != null)) {
              this.$message({
                type: "success",
                message: "添加成功!",
                showClose: true,
              });
              this.selectAllTeacherBySubject(
                  this.current,
                  this.size,
                  this.selectFrom.subjectId
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
    //修改教师
    updateTeacherById(obj) {
      this.$refs["updateFrom"].validate((valid) => {
        if (valid) {
          //修改教师
          updateTeacherById(obj).then((req) => {
            if ((req.data.statue === 200) & (req.data.data != null)) {
              this.$message({
                type: "success",
                message: "修改成功!",
                showClose: true,
              });
              this.selectAllTeacherBySubject(
                  this.current,
                  this.size,
                  this.selectFrom.subjectId
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
    //删除教师
    deleteTeacherById(teacherId, index) {
      this.$confirm(
          index
              ? "此操作将删除该教师, 是否继续?"
              : "此操作将恢复该教师, 是否继续?",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }
      ).then(() => {
        deleteTeacherById(teacherId, index).then((req) => {
          if ((req.data.statue === 200) & (req.data.data != null)) {
            this.$message({
              type: "success",
              message: "操作成功!",
              showClose: true,
            });
            this.selectAllTeacherBySubject(
                this.current,
                this.size,
                this.selectFrom.subjectId
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
      this.selectAllTeacherBySubject(
          this.current,
          this.size,
          this.selectFrom.subjectId
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