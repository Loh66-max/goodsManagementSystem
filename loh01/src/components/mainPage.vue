<script>
export default {
  name: "mainPage",
  data() {
    return {
      navList: [
        {name: "/main", navItem: "首页"},
        {name: "/userManage", navItem: "用户管理"},
        {name: "/goodsManage", navItem: "货品管理"},
      ],
      isCollapse: true,
      activeIndex: 'mainPage'
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    handleCommand(command) {
      if (command === 'logout') {
        this.logout();
      } else if (command === 'profile') {
        this.$message.info('个人中心功能开发中...');
      }
    },
    logout() {
      this.$confirm('确定要退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 清除本地存储的用户信息
        localStorage.removeItem('userInfo');
        localStorage.removeItem('isLoggedIn');

        this.$message.success('退出成功');

        // 跳转到登录页
        this.$router.push('/');
      }).catch(() => {
        this.$message.info('已取消退出');
      });
    }
  }
};
</script>

<template>
  <el-container style="height: 680px">
    <!--Menu菜单内容-->
    <el-aside style="width: 200px;background-color:#545c64;margin-left: -10px;margin-top: -10px;margin-right: 3px">
      <el-menu
          active-text-color="#ffd04b"
          background-color="#545c64"
          text-color="#fff"
          :default-active="$route.path"
          router
          @select="handleSelect"
      >
        <el-menu-item v-for="(item, i) in navList" :key="i" :index="item.name">
          <i class="el-icon-message"></i>
          <span> {{ item.navItem }}</span>
        </el-menu-item>
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
              <el-menu-item index="4">
                <a href="https://www.ele.me" target="_blank">
                  <span style="font-weight: bold">订单管理</span>
                </a>
              </el-menu-item>
            </el-menu>
          </div>
          <div style="flex: 1;text-align: center">
            <span style="font-weight: bolder;font-size: 30px;color: black;margin-right: 100px">
              进销存仓库管理系统
            </span>
          </div>

          <span style="font-size: 12px;font-weight: bold;color: black">王小虎</span>
          <el-dropdown @command="handleCommand">
            <i class="el-icon-arrow-down"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="profile">个人中心</el-dropdown-item>
              <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-header>

      <!--Main主要内容-->
      <el-main style="border: 3px;margin-right: -10px">
        <!-- 路由视图，显示子组件 -->
        <router-view></router-view>
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