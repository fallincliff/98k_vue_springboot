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
      <span v-for="item in tags">
        <el-tag type="danger">{{ item }}</el-tag>
      </span><br><span>作者 :</span><span>{{ TextWord.author }}</span>
      <br>
      <el-button :type="information1" @click="Up()" icon="el-icon-caret-top" circle></el-button>
      <el-button :type="information2" @click="Collection()" icon="el-icon-star-off " circle></el-button>
      <span type="float:right"> 点赞数:{{ TextWord.up }}</span>
      <span type="float:left"> 收藏数:{{ TextWord.collection }}</span>
      <el-divider></el-divider>
      <p v-html=TextData></p>

    </div>
  </div>
</template>

<script>
export default {
  name: "TextMessage",
  data() {
    return {
      TextData: '',
      TextWord: {
        tid: '',
        author: '',
        path: '',
        tag: '',
        picture: '',
        up: 0,
        collection: 0,//内部html资源

      },
      tags: ['随笔', '杂谈'],
      information1: 'warning',
      information2: 'warning',
      Isup: 0,
      IsCol: 0,
    };
  },
  methods: {
    goBack() {
      this.$router.push({path: '/user/Content'});
    },
    GetTag(tag) {
      const l = new Array();
      let temp = '';
      for (let i = 0; i < tag.length; ++i) {
        if (tag[i] != '，' && tag[i] != ',') {
          temp += tag[i];
        } else {
          l.push(temp);
          temp = '';
        }
      }
      l.push(temp);
      //console.log(l);
      this.tags = l;
    },
    Download() {
      window.location.href = `http://localhost:8888/file/download?fileName=` + this.TextWord.path;
    },
    UpdateText(val_up, val_collection) {
      const _this = this;
      this.$axios.get('http://localhost:8888/text/update?tid=' + _this.TextWord.tid +
        '&author=' + _this.TextWord.author +
        '&path=' + _this.TextWord.path +
        '&tag=' + _this.TextWord.tag +
        '&picture=' + _this.TextWord.picture +
        '&up=' + val_up +
        '&collection=' + val_collection).then(function (resp) {
      }).catch(function (error) {
        _this.$notify.error({title: '错误', message: error});
      })
    },
    UpdateUt(val_up, val_collection) {
      const _this = this;
      const user = JSON.parse(localStorage.getItem('user'));
      this.$axios.get('http://localhost:8888/ut/insert?uid=' + user.data.id +
        '&tid=' + _this.TextWord.tid +
        '&name=' + user.data.name +
        '&path=' + _this.TextWord.path +
        '&author=' + _this.TextWord.author +
        '&up=' + val_up +
        '&collection=' + val_collection).then(function (resp) {
      }).catch(function (error) {
        _this.$notify.error({title: '错误', message: error});
      })
    },
    Up() {
      if (this.information1 == "warning") {
        this.information1 = "danger";
        //点赞了，修改数据库、（添加）
        this.TextWord.up += 1;
        this.UpdateText(this.TextWord.up + 0, this.TextWord.collection + 0);
        if (this.information2 == "warning") {
          this.UpdateUt(1,0);
        } else {
          this.UpdateUt(1,1);
        }
      } else {
        this.information1 = "warning";
        //取消点赞，修改数据库、（删除）
        this.TextWord.up -= 1;
        this.UpdateText(this.TextWord.up + 0, this.TextWord.collection + 0);
        if (this.information2 == "warning") {
          this.UpdateUt(0,0);
        } else {
          this.UpdateUt(0,1);
        }
      }
    },
    Collection() {
      if (this.information2 == "warning") {
        this.information2 = "danger";
        //点收藏，修改数据库、（添加）
        this.TextWord.collection += 1;
        this.UpdateText(this.TextWord.up + 0, this.TextWord.collection + 0);
        if (this.information1 == "warning") {
          this.UpdateUt(0,1);
        } else {
          this.UpdateUt(1,1);
        }
      } else {
        this.information2 = "warning";
        //取消收藏，修改数据库、（删除）
        this.TextWord.collection -= 1;
        this.UpdateText(this.TextWord.up + 0, this.TextWord.collection + 0);
        if (this.information1 == "warning") {
          this.UpdateUt(0,0);
        } else {
          this.UpdateUt(1,0);
        }
      }
    },
    //获取该用户是否点赞或者收藏过该文章
    GetUt() {
      const _this = this;
      const user = JSON.parse(localStorage.getItem('user'));
      this.$axios.get('http://localhost:8888/ut/findByUidandTid?uid=' + user.data.id +
        '&tid=' + _this.TextWord.tid).then(function (resp) {
        //加载点赞按钮以及收藏
        if (resp.data[0].up == 1) {
          _this.information1 = "danger";
        }
        if (resp.data[0].collection == 1) {
          _this.information2 = "danger";
        }
      }).catch(function (error) {
        _this.UpdateUt(0,0);
      })
    }
  },
  created() {
    const info = localStorage.getItem('tid')
    const _this = this;
    this.$axios.get('http://localhost:8888/text/selectText?tid=' + info).then(function (resp) {
      _this.TextWord = resp.data;
      _this.GetUt();
      _this.GetTag(_this.TextWord.tag);
      _this.$axios.get('http://localhost:8888/text/' + _this.TextWord.path).then(function (resp) {
        _this.TextData = resp.data;
      }).catch(
        function (error) {
          _this.$notify.error({
            title: '错误',
            message: '获取文章内容失败'
          });
        });
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
