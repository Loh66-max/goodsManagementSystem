<script>

export default {
  name: "mainPage",
  data() {
    return {
      isCollapse: true,
      tableData: [],
      pageSize: 10,
      pageNum: 1,
      total:this.total
    };
  },
  methods: {
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum=val
      this.loadGet()
    },
    loadGet() {
      this.$axios.get('http://localhost:8090/getUserList',{params:{page:this.pageNum,pageSize:this.pageSize}})
          .then(res => {
            console.log(res)
            this.tableData = res.data.data.list;
            this.total = res.data.data.total;
          })
    },

  },
  beforeMount() {
    this.loadGet();
  }
};
</script>
<template>
  <el-container style="height: 680px">

    <!--Menu菜单内容-->
    <el-aside style="width: 200px;background-color:#545c64;margin-left: -10px;margin-top: -10px;margin-right: 3px">
      <el-menu active-text-color="#ffd04b"
               background-color="#545c64"
               text-color="#fff"
               default-active="/Home">
        <el-menu-item index="/Home"><i class="el-icon-message"></i>首页</el-menu-item>
        <el-menu-item index="/ONE"><i class="el-icon-message"></i>首页</el-menu-item>
        <el-menu-item index="/twe"><i class="el-icon-message"></i>首页</el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <!--Header标题内容-->
      <el-header style="margin-top: -10px;margin-right: -10px">

        <div style="display: flex">
          <div style="text-align: left">
            <el-menu
                :default-active="activeIndex2"
                class="el-menu-demo"
                mode="horizontal"
                @select="handleSelect"
                background-color="rgb(238, 241, 246)"
                text-color="black"
                active-text-color="#ffd04b">
              <el-submenu index="2">
                <template slot="title"><span style="font-weight: bold">工作台</span></template>
                <el-menu-item index="2-1">选项1</el-menu-item>
                <el-menu-item index="2-2">选项2</el-menu-item>
                <el-menu-item index="2-3">选项3</el-menu-item>
              </el-submenu>
              <el-menu-item index="4"><a href="https://www.ele.me" target="_blank"><span
                  style="font-weight: bold">订单管理</span></a></el-menu-item>
            </el-menu>
          </div>
          <div style="flex: 1;text-align: center"><span
              style="font-weight: bolder;font-size: 30px;color: black;margin-right: 100px">进销存仓库管理系统</span>
          </div>

          <span style="font-size: 12px;font-weight: bold;color: black">王小虎</span>
          <el-dropdown>
            <i class="el-icon-arrow-down"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>个人中心</el-dropdown-item>
              <el-dropdown-item>退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-header>
      <!--Main主要内容-->
      <el-main style="border: 3px;margin-right: -10px">
        <el-table :data="tableData"
                  border
                  :header-cell-style="{background:'#f2f5fc', color:'#555555' }">

          <el-table-column prop="id" label="序号">
          </el-table-column>
          <el-table-column prop="num" label="账号">
          </el-table-column>

          <el-table-column prop="role" label="身份">
            <template slot-scope="scope">
              <el-tag
                  :type="scope.row.role === 0 ? 'danger' : (scope.row.role === 1 ? 'primary' : 'success')"
                  disable-transitions>
                {{ scope.row.role === 0 ? '超级管理员' : (scope.row.role === 1 ? '管理员' : '用户') }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="value" label="是否激活">
            <template slot-scope="scope">
              <el-tag
                  :type="scope.row.value === '0'? 'primary' :  'danger'"
                  disable-transitions>{{ scope.row.value === '0' ? '已激活' : '未激活' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <el-button type="success" @click="dialogFormVisible=true">编辑</el-button>
            <el-button type="danger">删除</el-button>
          </el-table-column>
        </el-table>
        <div style="text-align: center">
          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="pageNum"
              :page-size="pageSize"
              layout="total,  prev, pager, next, jumper"
              :total="total">
          </el-pagination>
        </div>
      </el-main>
    </el-container>
  </el-container>
</template>
<style scoped>
.el-header {

  background-color: rgb(238, 241, 246);
  line-height: 60px;
  border-radius: 4px;
}

.el-aside {
  color: #2b2a2a;
}

.el-main {
  padding: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04)
}

</style>