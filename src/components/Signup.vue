<template>
  <!--注册页面-->
<div>
  <el-container>
    <el-header height="100px">
      <h1>注册界面</h1>
        <el-page-header @back="goBack()"><el-link icon="el-icon-back" :underline="false">返回</el-link></el-page-header>
      </el-header>
    <el-container>
      <el-aside width="200px">

      </el-aside>
    <el-main >
      <el-tooltip content="Top center" placement="top">
        <div slot="content">不超过十个字的用户名</div>
      <el-input style="width:250px" clearable maxlength="10" show-word-limit placeholder='Username' type="text"   v-model="username">
      </el-input>
      </el-tooltip><br>
      <el-input style="width:250px" clearable maxlength="30" show-word-limit placeholder='Email' type="text"   v-model="email">
      </el-input><br>
      <el-input style="width:250px" show-password clearable maxlength="10" show-word-limit placeholder='Password' type="password"   v-model="password">
      </el-input>
      <br>
      <el-input style="width:250px" show-password clearable maxlength="10" show-word-limit placeholder='Password' type="password"   v-model="surepassword">
      </el-input>
      <br>
      <el-radio-group v-model="sex">
        <el-radio-button label="男"  value="男" ></el-radio-button>
        <el-radio-button label="女"  value="女" ></el-radio-button>
      </el-radio-group>
      <br>
      <el-button  type="primary" round  @click="AddUser()">注册</el-button>
      <el-button  type="danger"  round  @click="Relode()">重置</el-button>
    </el-main>
    </el-container>
  </el-container>
</div>
</template>

<script>
export default {
  name: "Signup",
  data () {
    return {
      username: '',
      surepassword: '',
      email: '',
      password: '',
      sex:'',
      value: new Date(),
      Tempuser:{
        id:'',
        name: '',
        password: '',
        sex:''
      }
    };
  },
  methods: {
    Relode(){
      this.username = '';
      this.password = '';
      this.sex='';
      this.email = '';
      this.surepassword='';
    },
    AddUser(){
      if(this.username==''||this.password==''||this.sex==''||this.surepassword==''||
        this.username==null||this.password==null||this.sex==null||this.surepassword==null||
        this.password!=this.surepassword){

        this.$notify.error({
          title: '错误',
          message: '请按规定格式填写'
        });

        return null;
      }
      this.Tempuser.id= Math.round(Math.random()*100000000);
      this.Tempuser.name=this.username;
      this.Tempuser.password=this.password;
      this.Tempuser.sex=this.sex;
      this.$notify({
        title: '成功',
        message: '成功注册',
        type: 'success'
      });
      const _this = this;
      //让后端添加用户
      this.$axios.get('http://localhost:8888/user/insert?id='+this.Tempuser.id+'&name='+this.Tempuser.name+'&password='+this.Tempuser.password+'&sex='+this.Tempuser.sex+'&jurisdiction='+0).then(function (resp) {
      console.log(resp.data);
    }).catch(
      function (error) {
        // 请求失败处理
        _this.$notify.error({
          title: '错误',
          message: '注册失败'
        });
      })
    },
    goBack() {
      this.$router.push({path: '/'})
    }
  }
}
</script>

<style scoped>
.el-header, .el-footer {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  line-height: 80px;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
  line-height: 80px;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
</style>
