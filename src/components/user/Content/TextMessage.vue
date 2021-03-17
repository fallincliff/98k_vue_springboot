<template>
  <div>
    <el-page-header @back="goBack()">
      <el-link icon="el-icon-back" :underline="false">返回</el-link>
    </el-page-header>
    <el-link icon="el-icon-download" :underline="false" style="float:right" @click="Download">下载</el-link>
    <br>
    <div><h1 v-text="TextWord.path.substring(0,TextWord.path.lastIndexOf('.'))">
    </h1>
      <br>Tag :
      <span v-for="item in TextWord.tag">
        <el-tag type="danger" >{{ item }}</el-tag>
      </span><br><span>作者 :</span><span>{{TextWord.author}}</span>
      <el-divider></el-divider>
    <p v-html=TextData></p>

    </div>
  </div>
</template>

<script>
export default {
  name: "TextMessage",
  data(){
    return{
      TextData:'',
      TextWord:{
        tid:'',
        author:'',
        path:'',
        tag:['随笔','杂谈'],
        picture:'',

        //内部html资源
        url1:'',
      }
    };
  },
  methods: {
    goBack() {
      this.$router.push({path: '/user/Content'});
    },
    GetTag(tag){
      const l = new Array();
      let temp='';
      for(let i=0; i<tag.length; ++i){
        if(tag[i]!='，'&&tag[i]!=','){
          temp+=tag[i];
        }else{
          l.push(temp);
          temp='';
        }
      }
      l.push(temp);
      //console.log(l);
      this.TextWord.tag=l;
    },
    Download(){
      window.location.href = `http://localhost:8888/file/download?fileName=` + this.TextWord.path;
    }
  },
  created() {
    const info = localStorage.getItem('tid')
    const _this = this;
    this.$axios.get('http://localhost:8888/text/selectText?tid='+info).then(function (resp) {
      _this.TextWord=resp.data;
      _this.GetTag(_this.TextWord.tag);
      _this.$axios.get('http://localhost:8888/text/'+_this.TextWord.path).then(function (resp) {
      _this.url1='http://localhost:8888/text/'+_this.TextWord.path;
        _this.TextData=resp.data;
        //console.log(_this.TextWord.path);
      }).catch(
        function (error) {
          _this.$notify.error({
            title: '错误',
            message: '获取文章内容失败'
          });
        });
      //console.log(resp.data);
    }).catch(
      function (error) {
        _this.$notify.error({
          title: '错误',
          message: '获取文章内容失败'
        });
      })


  }
}
</script>

<style scoped>

</style>
