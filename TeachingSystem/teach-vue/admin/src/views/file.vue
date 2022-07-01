<template>
  <el-container>
    <el-header>
      <el-form :inline="true" :model="selectFrom">
        <el-form-item>
          <el-input
              v-model="selectFrom.fileName"
              placeholder="键入文件名称"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
              type="primary"
              @click="
              selectAllFileByName(
                this.current,
                this.size,
                this.selectFrom.fileName
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
            label="文件编号"
            prop="fileId"
            align="center"
            width="200"
            fixed="left"
        ></el-table-column>
        <el-table-column
            label="文件名称"
            prop="fileName"
            align="center"
            width="350"
        ></el-table-column>
        <el-table-column
            label="文件类型"
            prop="fileType"
            align="center"
            width="200"
        ></el-table-column>
        <el-table-column
            label="存放路径"
            prop="filePath"
            align="center"
            width="450"
        ></el-table-column>
        <el-table-column
            label="文件大小"
            prop="fileSize"
            align="center"
            width="200"
        ></el-table-column>
        <el-table-column
            label="上传时间"
            prop="fileUploadTime"
            align="center"
            width="200"
        ></el-table-column>
        <el-table-column
            label="修改时间"
            prop="fileUpdateTime"
            align="center"
            width="200"
        ></el-table-column>
        <el-table-column label="操作" width="200" align="center" fixed="right">
          <template #default="scope">
            <el-button
                type="danger"
                circle
                icon="el-icon-delete"
                @click="deleteFileById(scope.row.fileId)"
            >
            </el-button
            >
          </template>
        </el-table-column>
      </el-table>
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
</template>

<script>
import {
  selectAllFile,
  selectAllFileBySource,
  deleteFileById,
} from "../api/file";
export default {
  data() {
    return {
      //表格数据
      tableData: [],
      //查询数据
      selectFrom: {
        //文件名
        fileName: "",
      },
      //页码
      current: 1,
      //页长
      size: 10,
      //总数
      pageSize: "",
    };
  },
  created() {
    this.selectAllFile(this.current, this.size);
  },
  methods: {
    //查询所有文件
    selectAllFile(current, size) {
      selectAllFile(current, size).then((req) => {
        this.tableData = req.data.data;
        this.pageSize = req.data.pageSize;
      });
    },
    //条件查询文件
    selectAllFileByName(current, size, fileName) {
      selectAllFileBySource(current, size, fileName).then((req) => {
        this.tableData = req.data.data;
        this.pageSize = req.data.pageSize;
      });
    },
    //删除一个文件
    deleteFileById(fileId) {
      this.$confirm("此操作将永久删除该文件,真的永久,是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        deleteFileById(fileId).then((req) => {
          if ((req.data.statue === 200) & (req.data.data != null)) {
            this.$message({
              type: "success",
              message: "删除成功!",
              showClose: true,
            });
            this.selectAllFileByName(
                this.current,
                this.size,
                this.selectFrom.fileName
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
    // 分页
    handleCurrentChange(val) {
      this.current = val;
      this.selectAllFileByName(
          this.current,
          this.size,
          this.selectFrom.fileName
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