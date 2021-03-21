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
      <div>
        <h2>封面</h2>
        <el-upload
          action=""
          list-type="picture-card"
          :on-change="Add"
          :auto-upload="false">
          <i slot="default" class="el-icon-plus" ></i>
          <div slot="file" slot-scope="{file}" >
            <img
              class="el-upload-list__item-thumbnail"
              :src="file.url" alt=""
            >
            <span class="el-upload-list__item-actions">
          <span
            class="el-upload-list__item-preview"
            @click="handlePictureCardPreview(file)"
          >
          <i class="el-icon-zoom-in"></i>
        </span>
          <span
            v-if="!disabled"
            class="el-upload-list__item-delete"
            @click="handleDownload(file)"
          >
          <i class="el-icon-download"></i>
        </span>
        </span>
          </div>
        </el-upload>
        <el-dialog :visible.sync="dialogVisible" class="file" type="file" >
          <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
      </div>

      <el-input style="width:250px" clearable maxlength="10" show-word-limit placeholder='Username' type="text"   v-model="username">
      </el-input>
      <br>
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
      <el-button  type="primary" round  @click="uploadImage()">注册</el-button>
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
      pic:'',
      value: new Date(),
      Tempuser:{
        id:'',
        name: '',
        password: '',
        sex:'',
        email:'',
        pic:'',
      },
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false,
      fileList: [],
      SelectIndex:0,
      FL:[],
    };
  },
  methods: {
    //上传
    Add(file){
      this.fileList.push(file.name);
      this.FL.push(document.querySelector('input[type=file]').files[0]);
      //console.log(this.fileList)
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    findArrayIndex(val,vals){
      for(let i=0;i<vals.length;++i){
        if(vals[i]==val){
          return i;
        }
      }
      return 0;
    }
    ,
    //确认封面
    handleDownload(file) {
      this.SelectIndex=this.findArrayIndex(file.name,this.fileList);
    },
    //上传图片
    uploadImage(){
      const _this = this;
      let formData = new window.FormData()
      formData.append('file', this.FL[this.SelectIndex]) // 'file' 这个名字要和后台获取文件的名字一样;
      this.$axios({
        url: 'http://localhost:8888/file/upload',   //****: 你的ip地址
        data: formData,
        method: 'post',
        headers: {
          'Content-Type': 'multipart/form-data',
          // 'Access-Control-Allow-Origin': 'http://127.0.0.1:8080'
          //这里是为了解决跨域问题，但是博主并没有用这种方式解决。后面会给出解决方案
        }
      }).then((res) => {
        _this.pic=res.data;
        _this.AddUser()
        console.log(res.data);
      }).catch(
        function (error) {
          // 请求失败处理
          _this.$notify.error({
            title: '错误',
            message: error
          });
        }) // 发送请求
    },
    //重置
    Relode(){
      this.username = '';
      this.password = '';
      this.sex='';
      this.email = '';
      this.surepassword='';
    },
    //添加用户
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
      this.Tempuser.email=this.email;
      this.Tempuser.pic=this.pic;
      this.$notify({
        title: '成功',
        message: '成功注册',
        type: 'success'
      });
      const _this = this;
      //让后端添加用户
      this.$axios.get('http://localhost:8888/user/insert?id='+this.Tempuser.id+'&name='+this.Tempuser.name+'&password='+this.Tempuser.password+'&sex='+this.Tempuser.sex+'&jurisdiction='+0+'&email='+_this.Tempuser.email+'&pic='+_this.Tempuser.pic).then(function (resp) {
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
.el-select .el-input {
  width: 130px;
}

.input-with-select .el-input-group__prepend {
  background-color: #fff;
}

.el-tag + .el-tag {
  margin-left: 10px;
}

.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}

.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>
