<template>
  <div class="login-container">
    <el-card class="login-card" :body-style="{ padding: '20px' }">
      <h2 class="login-title">商品管理系统</h2>
      <el-form ref="loginForm" :model="form" :rules="rules" label-width="80px" class="login-form">
        <el-form-item label="用户名" prop="num">
          <el-input
              v-model="form.num"
              placeholder="请输入用户名"
              prefix-icon="el-icon-user"
              clearable
              class="my-input"
          />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input
              type="password"
              v-model="form.password"
              placeholder="请输入密码"
              prefix-icon="el-icon-lock"
              show-password
              clearable
              class="my-input"
          />
        </el-form-item>
        <el-form-item>
          <el-button
              type="primary"
              @click="handleSubmit"
              :loading="loading"
              style="width: 100%"
          >
            {{ loading ? '登录中...' : '登录' }}
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'userLogin',
  data() {
    return {
      loading: false,
      form: {
        num: '',
        password: '',
      },
      rules: {
        num: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 2, max: 20, message: '用户名长度在 2 到 20 个字符之间', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 20, message: '密码长度在 6 到 20 个字符之间', trigger: 'blur' },
        ],
      },
    };
  },
  methods: {
    handleSubmit() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.login();
        } else {
          this.$message.error('请检查表单输入');
          return false;
        }
      });
    },

    async login() {
      this.loading = true;
      try {
        const response = await this.$axios.post("http://localhost:8090/login", this.form);

        if (response.data.code === 1) {
          // 登录成功
          this.$message.success('登录成功');

          // 保存用户信息到本地存储
          const userInfo = response.data.data;
          localStorage.setItem('userInfo', JSON.stringify(userInfo));
          localStorage.setItem('isLoggedIn', 'true');

          // 跳转到主页面
          this.$router.push('/mainPage');
        } else {
          // 登录失败
          this.$message.error(response.data.msg || '登录失败');
        }
      } catch (error) {
        console.error('登录请求失败:', error);
        this.$message.error('网络错误，请稍后重试');
      } finally {
        this.loading = false;
      }
    },
  },

  mounted() {
    // 检查是否已经登录
    const isLoggedIn = localStorage.getItem('isLoggedIn');
    if (isLoggedIn === 'true') {
      this.$router.push('/mainPage');
    }
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-card {
  width: 400px;
  border-radius: 15px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(10px);
  background: rgba(255, 255, 255, 0.95);
}

.login-title {
  text-align: center;
  font-size: 28px;
  margin-bottom: 30px;
  color: #333;
  font-weight: 600;
}

.login-form .el-form-item {
  margin-bottom: 20px;
}

.login-form .el-button {
  margin-top: 20px;
  height: 45px;
  font-size: 16px;
  border-radius: 8px;
}

.login-form .el-input {
  height: 45px;
}

.login-form .el-input__inner {
  border-radius: 8px;
  border: 1px solid #dcdfe6;
  transition: all 0.3s;
}

.login-form .el-input__inner:focus {
  border-color: #409eff;
  box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
}
.my-input ::v-deep .el-input__inner {
  height: 40px; /* 统一高度 */
  line-height: 40px; /* 行高与高度一致 */
}
</style>