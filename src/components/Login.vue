<template>
  <!--登录-->
  <div>
    <img src="../assets/logo.png">
    <br>
    <h1>Please sign in</h1>
    <el-input style="width:250px" clearable maxlength="10" show-word-limit placeholder='Username' type="text"
              v-model="username">
    </el-input>
    <br><br>
    <el-input style="width:250px" show-password clearable maxlength="10" show-word-limit placeholder='Password'
              type="password" v-model="password">
    </el-input>
    <br><br>
    <div class="checkbox mb-3">
      <label>
        <input type="checkbox" value="remember-me" id="check1" @click="SaveUser()"> Remember me
      </label>
    </div>
    <br>
    <el-button type="primary" round v-on:click="Login_Liu(username,password)">Sign in</el-button>
    <el-button type="danger" round v-on:click="Sing_Up()">Sign up</el-button>
    <p class="mt-5 mb-3 text-muted">© 2020-2021</p>
    <a class="btn btn-sm">中文</a>
    <a class="btn btn-sm">English</a>
    <table>
      <tr v-for="i in users">
        <td>{{i.id}}</td>
        <td>{{i.name}}</td>
        <td>{{i.password}}</td>
        <td>{{i.sex}}</td>
      </tr>
    </table>
  </div>

</template>
<script>

export default {
  name: 'Login',
  data() {
    return {
      msg: 'Hello',
      password: "",
      username: "",
      users:this.$root.$data.users,
    }
  },
  methods: {
    Login_Liu: function (username, password) {
      var count=0;
      //console.log(data.users)
       for (var i=0;i<this.users.length;++i) {
         //console.log(this.users[i]);
        if (username != '' && username != null && password != '' && password != null
        && username==this.users[i].name&&password==this.users[i].password) {
          //alert(username + '\n' + password);

          localStorage.removeItem('user');
          const info={name:'user',data:this.users[i]}
          localStorage.setItem('user', JSON.stringify(info)) //  第一个值为key，第二个值为value，value可以是变量
          localStorage.setItem('page','1');

          this.$router.push({name: 'Main'})
        } else {
          count++;
        }
      }
      if(count==this.users.length)
      {
        this.$notify.error({
          title: '错误',
          message: '账户名或密码错误'
        });
      }
    },
    Sing_Up: function () {
      //alert('正在跳转到注册界面');
      this.$router.push({name: 'Signup'})
    },
    SaveUser(){
      var ischeck=document.getElementById("check1").checked;
      if(ischeck==true)
      {
        //写保存用户登录
        localStorage.removeItem('username');
        localStorage.removeItem('password');
        localStorage.setItem('username',this.username);
        localStorage.setItem('password',this.password);
      }

    }
  },
  created() {
    //加载保存的用户信息
    const info = localStorage.getItem('username')
    //console.log(info)
    this.username=info;
    const info2 = localStorage.getItem('password')
    //console.log(info2)
    this.password=info2;



    const _this = this;
    this.$axios.get('http://localhost:8888/user/findAll').then(function (resp) {
      _this.users = resp.data;
      //console.log(_this.users);
    }).catch(
      function (error) {
        // 请求失败处理
        _this.$notify.error({
          title: '错误',
          message: '后端未启动，无法连接数据库'
        });
      })
  }

}
</script>

<style scoped>
</style>
