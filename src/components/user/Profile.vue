<template>
  <div>
    <div class="demo-image__preview">
      头像:
      <el-image
        style="width: 100px; height: 100px"
        :src="'http://localhost:8888/picture/'+user.pic"
        :preview-src-list="srcList">
      </el-image>
    </div>
    <br>
    账号:{{user.name}}
    <br>
    邮箱:{{user.email}}
    <br>
    性别:{{user.sex}}
    <br>
    博文数:<el-link :underline="false" type="primary" @click="table3=true">{{TextCount}}</el-link>
    <br>
    关注数: <el-link :underline="false" type="primary" @click="table1=true">{{follow}}</el-link>粉丝数<el-link :underline="false" type="primary" @click="table2=true">{{fans}}</el-link>
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
    <!--抽屉，关注与粉丝抽屉-->
    <el-drawer
      title="关注列表"
      :visible.sync="table1"
      direction="rtl"
      size="50%">

      <el-table :data="Follist" style="width: 100%" max-height="600">

        <el-table-column fixed property="vipuid" label="vipuid" width="150"></el-table-column>
        <el-table-column property="vipname" label="用户名" width="200"></el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="100">
          <template slot-scope="scope">
            <el-button type="text" size="small">取关</el-button>
            <el-button type="text" size="small">访问个人主页</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-drawer>
    <el-drawer
      title="粉丝列表"
      :visible.sync="table2"
      direction="rtl"
      size="50%">

      <el-table :data="Fanlist" style="width: 100%" max-height="600">

        <el-table-column fixed property="uid" label="uid" width="150"></el-table-column>
        <el-table-column property="name" label="用户名" width="200"></el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="100">
          <template slot-scope="scope">
            <el-button type="text" size="small">访问个人主页</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-drawer>
    <!--抽屉，自己博客-->
    <el-drawer
      title="博文管理"
      :visible.sync="table3"
      direction="rtl"
      size="50%">

      <el-table :data="MyTexts" style="width: 100%" max-height="600">

        <el-table-column fixed property="tid" label="tid" width="150"></el-table-column>
        <el-table-column property="path" label="标题" width="200"></el-table-column>
        <el-table-column property="author" label="作者"></el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="100">
          <template slot-scope="scope">
            <el-button type="text" size="small">浏览</el-button>
            <el-button type="text" size="small">删除</el-button>
            <el-button type="text" size="small">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-drawer>
  </div>
</template>

<script>
let pictu;
export default {
  name: "UserProfile",
  data() {
    return {
      srcList: [

      ],
      user: {
        email:'',
        pic:null,
        id:'',
        name:'',
        password:'',
        sex:'',
        jurisdiction:0,

      },
      TextCount:null,
      table: false,
      table1: false,
      table2: false,
      table3: false,
      Texts:this.$root.$data.TextWords,


      //关注数与粉丝数
      follow:0,
      fans:0,

      //自己的博客
      MyTexts:null,
      //关注与粉丝列表
      Follist:null,
      Fanlist:null,
    }
  },
  methods: {
    LogOut() {
      this.$router.push({path: '/'});
    },
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
    },
    //获取用户信息
    GetInfo(){
      const info = JSON.parse(localStorage.getItem('user'))
      this.user=info.data;
      pictu=this.user.pic;
      this.srcList.push('http://localhost:8888/picture/'+pictu);
      //console.log(this.srcList)
      const _this = this;
      this.$axios.get('http://localhost:8888/text/selectByAuthor?author='+_this.user.name).then(function (resp){
        _this.TextCount=resp.data.length;
        _this.MyTexts=resp.data;
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
    },
    //获取关注列表与粉丝列表
    GetFans(){
      const user = JSON.parse(localStorage.getItem('user'));
      const _this = this;
      this.$axios.get('http://localhost:8888/uu/findByuid?uid='+user.data.id).then(function (resp){
        //console.log(resp.data);
        _this.follow=resp.data.length;
        _this.Follist=resp.data;
      }).catch(function (error){_this.$notify.error({title: '错误', message: error});})
      this.$axios.get('http://localhost:8888/uu/findByvipuid?vipuid='+user.data.id).then(function (resp){
        //console.log(resp.data);
        _this.fans=resp.data.length;
        _this.Fanlist=resp.data;
      }).catch(function (error){_this.$notify.error({title: '错误', message: error});})
    }
  },
  mounted() {

  },//过滤器
  beforeRouteEnter: (to, from, next) => {
    //console.log("进入路由之前");
    next(/*vm => {
      vm.getData();//进入路由之前执行该方法
    }*/);
  },
  beforeRouteLeave: (to, from, next) => {
    //console.log("进入路由之后");
    next();
  },
  created(){
    this.GetInfo();
    this.GetFans();
  }

}
</script>

<style scoped>

</style>
