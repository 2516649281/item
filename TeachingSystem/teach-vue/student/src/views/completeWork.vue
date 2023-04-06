<template>
  <el-container>
    <el-main>
      <el-table :data="tableData" height="450">
        <el-table-column align="center" type="expand">
          <template #default="props">
            <el-form label-position="left" inline>
              <el-form-item label="作业具体内容:">
                <span>{{ props.row.workContent }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column
          label="作业编号"
          prop="workId"
          align="center"
        ></el-table-column>
        <el-table-column
          label="作业名"
          prop="workName"
          align="center"
        ></el-table-column>
        <el-table-column
          label="创建时间"
          prop="log.createTime"
          align="center"
        ></el-table-column>
        <el-table-column
          label="最后修改时间"
          prop="log.updateTime"
          align="center"
        ></el-table-column>
      </el-table>
    </el-main>
  </el-container>
</template>

<script>
import jwtDecode from "jwt-decode";
import { selectAllCreateWork } from "../api/work";
export default {
  data() {
    return {
      tableData: "",
    };
  },
  created() {
    this.selectAllCreateWork(
      jwtDecode(sessionStorage.getItem("token")).user.user.studentId
    );
  },
  methods: {
    selectAllCreateWork(classId) {
      selectAllCreateWork(classId).then((req) => {
        this.tableData = req.data.data;
      });
    },
  },
};
</script>

<style scoped>
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
</style>