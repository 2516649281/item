<template>
  <el-container>
    <el-main>
      <el-table :data="tableData" height="450" id="table">
        <el-table-column
            align="center"
            label="学生编号"
            prop="studentId"
        ></el-table-column>
        <el-table-column
            align="center"
            label="学生姓名"
            prop="studentName"
        ></el-table-column>
        <el-table-column prop="studentAge" label="学生年龄" align="center">
          <template #default="scope"> {{ scope.row.studentAge }}岁</template>
        </el-table-column>
        <el-table-column prop="studentGender" label="学生性别" align="center">
          <template #default="scope">
            {{ scope.row.studentGender == 0 ? "女" : "男" }}
          </template>
        </el-table-column>
      </el-table>
    </el-main>
  </el-container>
</template>

<script>
import {selectAllStudentByClassId} from "../api/work";
import jwtDecode from "jwt-decode";
export default {
  data() {
    return {
      tableData: [],
    };
  },
  created() {
    this.selectAllStudentByClassId(
        jwtDecode(sessionStorage.getItem("token")).user.user.classId
    );
  },
  methods: {
    //查询所有学生
    selectAllStudentByClassId(classId) {
      selectAllStudentByClassId(classId).then((req) => {
        this.tableData = req.data.data;
      });
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
</style>