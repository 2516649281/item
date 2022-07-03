<template>
  <el-container>
    <el-header>
      <el-form :inline="true" :model="selectFrom">
        <el-form-item>
          <el-input
              v-model="selectFrom.adminAddress"
              placeholder="键入管理员地址"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
              icon="el-icon-search"
              type="primary"
              @click="
              selectAllAdminByAddress(
                this.current,
                this.size,
                selectFrom.adminAddress
              )
            "
          >查询
          </el-button
          >
          <el-button
              icon="el-icon-delete"
              type="danger"
              @click="deleteAdmins(this.adminIds, true)"
          >批量删除
          </el-button
          >
          <el-button
              icon="el-icon-refresh-right"
              type="success"
              @click="deleteAdmins(this.adminIds, false)"
          >批量恢复
          </el-button
          >
        </el-form-item>
      </el-form>
    </el-header>
    <el-main>
      <el-table
          ref="adminIds"
          :data="tableData"
          height="400"
          @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column
            align="center"
            fixed="left"
            label="管理员编号"
            prop="adminId"
            width="100"
        ></el-table-column>
        <el-table-column
            align="center"
            label="管理员姓名"
            prop="adminName"
            width="200"
        ></el-table-column>
        <el-table-column label="管理员年龄" align="center" width="200">
          <template #default="scope"> {{ scope.row.adminAge }}岁</template>
        </el-table-column>
        <el-table-column label="管理员性别" align="center" width="200">
          <template #default="scope">
            {{ scope.row.adminGender === 0 ? "女" : "男" }}
          </template>
        </el-table-column>
        <el-table-column
            align="center"
            label="管理员住址"
            prop="adminAddress"
            width="200"
        ></el-table-column>
        <el-table-column
            align="center"
            label="管理员电话"
            prop="adminPhone"
            width="200"
        ></el-table-column>
        <el-table-column
            align="center"
            label="管理员邮箱"
            prop="adminEmail"
            width="200"
        ></el-table-column>
        <el-table-column
            align="center"
            label="创建时间"
            prop="log.createTime"
            width="250"
        ></el-table-column>
        <el-table-column
            align="center"
            label="最后修改时间"
            prop="log.updateTime"
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
                circle
                icon="el-icon-edit"
                type="primary"
                @click="editClick(scope.row)"
            ></el-button>
            <el-button
                circle
                icon="el-icon-delete"
                type="danger"
                @click="deleteAdminById(scope.row.adminId, true)"
            >
            </el-button>
            <el-button
                circle
                icon="el-icon-refresh-right"
                type="success"
                @click="deleteAdminById(scope.row.adminId, false)"
            ></el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <div class="addbtn">
        <el-button
            circle
            icon="el-icon-plus"
            type="success"
            @click="adddialogFormVisible = true"
        ></el-button>
      </div>
      <el-pagination
          :page-size="size"
          :total="pageSize"
          background
          layout="prev, pager, next"
          @current-change="handleCurrentChange"
      >
      </el-pagination>
    </el-main>
  </el-container>
  <!-- 添加管理员 -->
  <el-dialog title="添加管理员" v-model="adddialogFormVisible">
    <el-form :model="addFrom" :rules="rules" ref="addFrom">
      <el-form-item label="管理员姓名:" prop="adminName">
        <el-input v-model="addFrom.adminName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="管理员年龄:" prop="adminAge">
        <el-input v-model="addFrom.adminAge" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="管理员性别:" prop="adminGender">
        <el-radio-group v-model="addFrom.adminGender">
          <el-radio :label="1">男</el-radio>
          <el-radio :label="0">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="管理员住址:" prop="adminAddress">
        <el-input v-model="addFrom.adminAddress" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="管理员电话:" prop="adminPhone">
        <el-input v-model="addFrom.adminPhone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="管理员邮箱:" prop="adminEmail">
        <el-input v-model="addFrom.adminEmail" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <template v-slot:footer>
      <el-button @click="adddialogFormVisible = false" round>取 消</el-button>
      <el-button type="primary" @click="addAdmin(addFrom)" round
      >确 定
      </el-button
      >
    </template>
  </el-dialog>
  <!-- 修改管理员 -->
  <el-dialog title="修改管理员" v-model="editdialogFormVisible">
    <h1>你选择了{{ editFrom.adminId }}号管理员</h1>
    <el-form :model="editFrom" :rules="rules" ref="updateFrom">
      <el-form-item label="管理员姓名:" prop="adminName">
        <el-input v-model="editFrom.adminName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="管理员年龄:" prop="adminAge">
        <el-input v-model="editFrom.adminAge" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="管理员性别:" prop="adminGender">
        <el-radio-group v-model="editFrom.adminGender">
          <el-radio :label="1">男</el-radio>
          <el-radio :label="0">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="管理员住址:" prop="adminAddress">
        <el-input v-model="editFrom.adminAddress" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="管理员电话:" prop="adminPhone">
        <el-input v-model="editFrom.adminPhone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="管理员邮箱:" prop="adminEmail">
        <el-input v-model="editFrom.adminEmail" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <template v-slot:footer>
      <el-button @click="editdialogFormVisible = false" round>取 消</el-button>
      <el-button type="primary" @click="updateAdminById(editFrom)" round
      >确 定
      </el-button
      >
    </template>
  </el-dialog>
</template>

<script>
import {
  selectAllAdmin,
  selectAllAdminByAddress,
  addAdmin,
  updateAdminById,
  deleteAdminById,
} from "../api/admin";
export default {
  data() {
    return {
      //表格数据
      tableData: [],
      //查询数据
      selectFrom: {
        //地址名称
        adminAddress: "",
      },
      //管理员编号
      adminIds: [],
      //页码
      current: 1,
      //页长
      size: 10,
      //总数
      pageSize: "",
      //添加管理员对话框显隐
      adddialogFormVisible: false,
      //编辑管理员对话框
      editdialogFormVisible: false,
      //添加数据
      addFrom: {
        //管理员姓名
        adminName: "",
        //管理员年龄
        adminAge: "",
        //管理员性别
        adminGender: "",
        //管理员住址
        adminAddress: "",
        //管理员电话
        adminPhone: "",
        //管理员邮箱
        adminEmail: "",
      },
      //编辑数据
      editFrom: {
        //管理员编号
        adminId: "",
        //管理员姓名
        adminName: "",
        //管理员年龄
        adminAge: "",
        //管理员性别
        adminGender: "",
        //管理员住址
        adminAddress: "",
        //管理员电话
        adminPhone: "",
        //管理员邮箱
        adminEmail: "",
      },
      //删除表单
      map: [],
      //表单验证
      rules: {
        adminName: [
          {required: true, message: "管理员姓名不得为空!", trigger: "blur"},
        ],
        adminAge: [
          {required: true, message: "管理员年龄不得为空!", trigger: "blur"},
        ],
        adminGender: [
          {required: true, message: "管理员性别不得为空!", trigger: "blur"},
        ],
        adminAddress: [
          {required: true, message: "管理员地址不得为空!", trigger: "blur"},
        ],
        adminPhone: [
          {required: true, message: "管理员电话不得为空!", trigger: "blur"},
        ],
        adminEmail: [
          {required: true, message: "管理员邮箱不得为空!", trigger: "blur"},
        ],
      },
    };
  },
  created() {
    this.selectAllAdminByAddress(
        this.current,
        this.size,
        this.selectFrom.adminAddress
    );
  },
  methods: {
    //查询所有管理员
    selectAllAdmin(current, size) {
      selectAllAdmin(current, size).then((req) => {
        this.tableData = req.data.data;
        this.pageSize = req.data.pageSize;
      });
    },
    //按照地址查询管理员
    selectAllAdminByAddress(current, size, addressId) {
      selectAllAdminByAddress(current, size, addressId).then((req) => {
        this.tableData = req.data.data;
        this.pageSize = req.data.pageSize;
      });
    },
    //添加管理员
    addAdmin(obj) {
      this.$refs["addFrom"].validate((valid) => {
        if (valid) {
          //添加管理员
          addAdmin(obj).then((req) => {
            if ((req.data.statue === 200) & (req.data.data != null)) {
              this.$message({
                type: "success",
                message: "添加成功!",
                showClose: true,
              });
              this.selectAllAdminByAddress(
                  this.current,
                  this.size,
                  this.selectFrom.adminAddress
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
    //修改管理员
    updateAdminById(obj) {
      this.$refs["updateFrom"].validate((valid) => {
        if (valid) {
          //修改管理员
          updateAdminById(obj).then((req) => {
            if ((req.data.statue === 200) & (req.data.data != null)) {
              this.$message({
                type: "success",
                message: "修改成功!",
                showClose: true,
              });
              this.selectAllAdminByAddress(
                  this.current,
                  this.size,
                  this.selectFrom.adminAddress
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
    //单次删除管理员
    deleteAdminById(adminId, index) {
      this.$confirm(
          index
              ? "此操作将删除该管理员, 是否继续?"
              : "此操作将恢复该管理员, 是否继续?",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }
      ).then(() => {
        //定义map集合
        this.map = new Map();
        this.map[adminId] = index;
        deleteAdminById(this.map).then((req) => {
          if ((req.data.statue === 200) & (req.data.data != null)) {
            this.$message({
              type: "success",
              message: "操作成功!",
              showClose: true,
            });
            this.selectAllAdminByAddress(
                this.current,
                this.size,
                this.selectFrom.adminAddress
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
    //批量删除
    deleteAdmins(ids, index) {
      this.$confirm(
          index
              ? "此操作将删除管理员, 是否继续?"
              : "此操作将恢复管理员, 是否继续?",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }
      ).then(() => {
        this.map = new Map();
        for (let i = 0; i < ids.length; i++) {
          this.map[ids[i].adminId] = index;
        }
        deleteAdminById(this.map).then((req) => {
          if ((req.data.statue === 200) & (req.data.data != null)) {
            this.$message({
              type: "success",
              message: "操作成功!",
              showClose: true,
            });
            this.selectAllAdminByAddress(
                this.current,
                this.size,
                this.selectFrom.adminAddress
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
    //获取打勾的项
    handleSelectionChange(val) {
      this.adminIds = val;
    },
    // 分页
    handleCurrentChange(val) {
      this.current = val;
      this.selectAllAdminByAddress(
          this.current,
          this.size,
          this.selectFrom.adminAddress
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