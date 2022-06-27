<template>
  <el-container>
    <el-main>
      <div id="main-image">
        <el-carousel height="200px" type="card">
          <el-carousel-item v-for="item in imgList" :key="item">
            <el-image :src="item" alt></el-image>
          </el-carousel-item>
        </el-carousel>
      </div>
      <div id="main-card">
        <el-card :body-style="{ padding: '0px' }">
          <div style="padding: 14px">
            <span>java基础培训班今日开课</span>
            <div class="bottom clearfix">
              <el-button type="text" class="button">查看详情</el-button>
            </div>
          </div>
        </el-card>
        <el-card :body-style="{ padding: '0px' }">
          <div style="padding: 14px">
            <span>python高级篇</span>
            <div class="bottom clearfix">
              <el-button type="text" class="button">查看详情</el-button>
            </div>
          </div>
        </el-card>
        <el-card :body-style="{ padding: '0px' }">
          <div style="padding: 14px">
            <span>学IT，来黑马，黑马来了!</span>
            <div class="bottom clearfix">
              <el-button type="text" class="button">查看详情</el-button>
            </div>
          </div>
        </el-card>
        <el-card :body-style="{ padding: '0px' }">
          <div style="padding: 14px">
            <span>腾讯、阿里面试题在线查看</span>
            <div class="bottom clearfix">
              <el-button type="text" class="button">查看详情</el-button>
            </div>
          </div>
        </el-card>
      </div>
      <!-- 后台获取教师发布的新作业 -->
      <div id="main-news">
        <el-timeline>
          <el-timeline-item timestamp="2018/4/12" placement="top">
            <el-card>
              <h4>发布了新作业</h4>
              <p>王老师 提交于 2018/4/12 20:46</p>
            </el-card>
          </el-timeline-item>
          <el-timeline-item timestamp="2018/4/3" placement="top">
            <el-card>
              <h4>发布了新作业</h4>
              <p>李老师 提交于 2018/4/3 20:46</p>
            </el-card>
          </el-timeline-item>
        </el-timeline>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import img1 from "../img/1.jpg";
import img2 from "../img/2.jpg";
import img3 from "../img/3.jpg";
import img4 from "../img/4.jpg";

export default {
  data() {
    return {
      imgList: [img1, img2, img3, img4],
    };
  },
};
</script>

<style scoped>
/* 主体 */
#main-image {
  width: 100%;
  margin: 0 auto;
}

#main-image .el-image {
  width: 100%;
}

#main-news {
  width: 50%;
  float: left;
}

#main-card {
  margin: 0 auto;
  float: left;
  width: 50%;
}

#main-card .el-card {
  background-color: transparent;
  color: white;
  width: 100%;
  /* float: left; */
  margin: 0 1.5%;
}

.el-dropdown-link {
  cursor: pointer;
  color: #409eff;
}

.el-icon-arrow-down {
  font-size: 12px;
}

#main-news .el-timeline .el-card {
  background-color: transparent;
  color: white;
}
</style>