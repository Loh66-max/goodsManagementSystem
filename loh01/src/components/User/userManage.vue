<script>
export default {
  name:'userManage',
  data(){
    return {
      isCollapse: true,
      tableData: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      input:'',
      centerDialogVisible1:false,
      centerDialogVisible2:false,
      numUser:'',
      passwordUser:'',
      form1:{
        num:'',
        password:'',
        role:''
      },
      form2:{
        id:'',
        num:'',
        password:'',
        role:'',
        value:''
      },
      form3:{
        id:''
      }
    };
  },
  rules:{
    id:[
      { required: true, message: '请输入id', trigger: 'blur' },
    ],
  },
  methods: {
    logout() {
      this.$confirm('确定要退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        localStorage.removeItem('userInfo');
        localStorage.removeItem('isLoggedIn');
        this.$message.success('退出成功');
        this.$router.push('/');
      }).catch(() => {
        this.$message.info('已取消退出');
      });
    },
    deleteUser(row){
      this.form3= {...row};
      this.id = this.form3.id
      this.open()
    },
    open() {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        cancelButtonText: '取消',
        confirmButtonText: '确定',
        type: 'warning',
        center: true
      }).then(() => {
        this.$message({
              type: 'success',
              message: '删除成功!',
            },
            this.$axios.get('http://localhost:8090/deleteUser', {params:{id:this.id}}).then(res=>{
              console.log(res)
              this.loadGet()
            })
        );
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    loadGet() {
      if (this.input) {
        this.$axios.post('http://localhost:8090/queryUser',
            { goodsName: this.input },
            { params: { page: this.pageNum, pageSize: this.pageSize } }
        ).then(res => {
          console.log(res)
          this.tableData = res.data.records;
          this.total = res.data.total;
        }).catch(error => {
          console.error('查询用户失败:', error);
          this.$message.error('查询失败，请稍后重试');
        });
      } else {
        this.$axios.get('http://localhost:8090/pageUser', { params: { page: this.pageNum, pageSize: this.pageSize } })
            .then(res => {
              console.log(res)
              this.tableData = res.data.data.list;
              this.total = res.data.data.total;
            }).catch(error => {
          console.error('加载用户数据失败:', error);
          this.$message.error('加载数据失败，请稍后重试');
        });
      }
    },
    search() {
      this.pageNum = 1;
      this.loadGet();
    },
    reset() {
      this.input = '';
      this.pageNum = 1;
      this.loadGet();
    },
    save(){
      this.$axios.post('http://localhost:8090/saveUser',this.form1)
          .then(res=>{console.log(res)
            if (res.data){
              this.centerDialogVisible1 = false;
              this.$notify({
                title: '成功',
                message: '创建用户成功，已激活',
                type: 'success'
              });
              this.form1={}
              this.loadGet()
            }else {
              this.$notify.error({
                title: '错误',
                message: '创建用户失败'
              });
              this.form1={};
              this.centerDialogVisible1 = false;
            }}).catch(error => {
        console.error('保存用户失败:', error);
        this.$message.error('保存失败，请稍后重试');
      });
    },
    mod(row){
      this.form2= {...row}
      this.centerDialogVisible2 = true;
    },
    updateUser(){
      this.$axios.post('http://localhost:8090/modUser',this.form2)
          .then(res=>{console.log(res)
            if (res.data==true){
              this.centerDialogVisible2 = false;
              this.$notify({
                title: '成功',
                message: '修改用户成功，已激活',
                type: 'success'
              });
              this.loadGet()
            }else {
              this.$notify.error({
                title: '错误',
                message: '修改用户失败'
              });
              this.centerDialogVisible1 = false;
            }}).catch(error => {
        console.error('保存用户失败:', error);
        this.$message.error('保存失败，请稍后重试');
      });
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.loadGet();
    },
    handleCurrentChange(val) {
      this.pageNum = val;
      this.loadGet();
    }
  },
  mounted() {
    this.loadGet();
  },
  watch: {
    '$route'(to) {
      if (to.name === 'main') {
        this.loadGet();
      }
    }
  }
}
</script>

<template>
  <div>
    <!-- 搜索和操作区域 -->
    <div style="margin-bottom: -20px;">
      <el-input
          placeholder="请输入要查询的用户名(支持模糊搜索)"
          suffix-icon="el-icon-search"
          style="width: 300px"
          v-model="input">
      </el-input>
      <el-button type="primary" style="margin-left: 10px;margin-bottom: 5px" @click="search">查询</el-button>
      <el-button type="danger" @click="reset" style="margin-bottom: 5px">重置</el-button>
      <el-button type="info" @click="centerDialogVisible1=true" style="margin-bottom: 5px;margin-left: 780px;width: 100px">新建</el-button>
    </div>

    <!-- 用户数据表格 -->
    <el-table :data="tableData"
              border
              :header-cell-style="{background:'#f2f5fc', color:'#555555' }">
      <el-table-column prop="id" label="序号"></el-table-column>
      <el-table-column prop="num" label="账号"></el-table-column>
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
              disable-transitions>
            {{ scope.row.value === '0' ? '已激活' : '未激活' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="success" @click="mod(scope.row)">编辑</el-button>
          <el-button type="danger" @click="deleteUser(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="pageSize"
        layout="total,  prev, pager, next, jumper"
        :total="total"
        style="margin-top: 20px; text-align: center;">
    </el-pagination>

    <!-- 新建用户对话框 -->
    <el-dialog title="新建用户" :visible.sync="centerDialogVisible1" width="30%" center>
      <el-form :model="form1" label-width="80px">
        <el-form-item label="账号">
          <el-input v-model="form1.num"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form1.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="角色">
          <el-input v-model="form1.role" placeholder="请选择角色(0:超级管理员,1:管理员,2:用户)">
          </el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
          <el-button @click="centerDialogVisible1 = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </span>
    </el-dialog>

    <!-- 编辑用户对话框 -->
    <el-dialog title="编辑用户" :visible.sync="centerDialogVisible2" width="30%" center>
      <el-form :model="form2" label-width="80px">
        <el-form-item label="ID">
          <el-input v-model="form2.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="账号">
          <el-input v-model="form2.num"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form2.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="角色">
          <el-input v-model="form2.role" placeholder="请选择角色(0:超级管理员,1:管理员,2:用户)">
          </el-input>
        </el-form-item>
        <el-form-item label="用户状态">
          <el-input v-model="form2.value" placeholder="请选择用户状态(0:激活,1:不激活)">
          </el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
          <el-button @click="centerDialogVisible2 = false">取 消</el-button>
          <el-button type="primary" @click="updateUser">确 定</el-button>
        </span>
    </el-dialog>
  </div>
</template>

<style>
.el-table {
  margin-top: 20px;
}
</style>