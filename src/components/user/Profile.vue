<template>
  <div>
    账号:{{user.name}}
    <br>
    密码:{{user.password}}
    <br>
    性别:{{user.sex}}
    <br>
    博文数:{{TextCount}}
    <br>
    <el-button type="primary" round @click="RootWrite">写新博文</el-button>
    <el-button type="info" round @click="LogOut">退出登录</el-button>
    <el-button v-if="user.jurisdiction===1" type="danger" round @click="table = true">删除博文</el-button>
    <!--抽屉，删除博文选项-->
    <el-drawer
      title="管理员专属删除博客功能!"
      :visible.sync="table"
      direction="rtl"
      size="50%">

      <el-table :data="Texts" style="width: 100%" max-height="600">

        <el-table-column fixed property="tid" label="tid" width="150"></el-table-column>
        <el-table-column property="path" label="标题" width="200"></el-table-column>
        <el-table-column property="author" label="作者"></el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="100">
          <template slot-scope="scope">
            <el-button @click="DeleteText(scope.row)" type="text" size="small">删除</el-button>
            <el-button type="text" size="small">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-drawer>
  </div>
</template>

<script>
export default {
  name: "UserProfile",
  data() {
    return {
      user: {
        id:'',
        name:'',
        password:'',
        sex:'',
        jurisdiction:0,
      },
      TextCount:'',
      table: false,
      Texts:this.$root.$data.TextWords
    }
  },
  methods: {
    LogOut() {
      this.$router.push({path: '/'});
    }
    ,
    RootWrite(){
      let routeUrl = this.$router.resolve({
        name: 'Write'
      });
      window.open(routeUrl.href, '_blank');
    },
    getData() {
      this.axios({
        method: "get",
        url: 'http://localhost:8080/static/mock/data.json',
      }).then(function (response) {
        console.log(response);
      });
    },
    DeleteText(row) {
      //console.log(row.tid)
      //先删除数据库内消息，再删除文件
      const _this = this;
      this.$axios.get('http://localhost:8888/text/delete?tid='+row.tid).then(function (resp){
        console.log(resp);
      }).catch(
        function (error){
          // 请求失败处理
          //console.log(error)
          _this.$notify.error({
            title: '错误',
            message: error
          });
        })
      //先删博文
      this.$axios.get('http://localhost:8888/file/deleteFileText?path='+row.path).then(function (resp){
        console.log(resp);
      }).catch(
        function (error){
          // 请求失败处理
          //console.log(error)
          _this.$notify.error({
            title: '错误',
            message: error
          });
        })
      //再删图片
      this.$axios.get('http://localhost:8888/file/deleteFilePic?path='+row.picture).then(function (resp){
        console.log(resp);
      }).catch(
        function (error){
          // 请求失败处理
          //console.log(error)
          _this.$notify.error({
            title: '错误',
            message: error
          });
        })
    }
  },
  mounted() {

  },//过滤器
  beforeRouteEnter: (to, from, next) => {
    console.log("进入路由之前");
    next(/*vm => {
      vm.getData();//进入路由之前执行该方法
    }*/);
  },
  beforeRouteLeave: (to, from, next) => {
    console.log("进入路由之后");
    next();
  },
  created(){
    const info = JSON.parse(localStorage.getItem('user'))
    //console.log(info)
    this.user=info.data;
    const _this = this;
    this.$axios.get('http://localhost:8888/text/selectByAuthor?author='+_this.user.name).then(function (resp){
      _this.TextCount=resp.data.length;
      //console.log(resp.data.length);
    }).catch(
      function (error){
        // 请求失败处理
        //console.log(error)
        _this.$notify.error({
          title: '错误',
          message: error
        });
      })
    //获取博文
    if(this.user.jurisdiction==1) {
      this.$axios.get('http://localhost:8888/text/findAll/').then(function (resp) {
        _this.Texts = resp.data;
        //console.log(resp.data)
      }).catch(
        function (error) {
          // 请求失败处理
          //console.log(error)
          _this.$notify.error({
            title: '错误',
            message: error
          });
        })
    }
  }

}
</script>

<style scoped>

</style>
