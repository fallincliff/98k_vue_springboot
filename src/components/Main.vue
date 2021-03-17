<template>
  <!--首页-->

  <div id="app">
    <el-container style="height: 600px; border: 1px solid #eee">
      <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
        <el-menu :default-openeds="['1', '3']">
          <el-submenu index="1">
            <template slot="title"><i class="el-icon-menu"></i>导航栏</template>

            <template slot="title"></template>
            <el-menu-item index="2-1" @click="RouteTo(1)">
              首页
            </el-menu-item>
            <el-menu-item index="2-2" @click="RouteTo(2)">
              用户列表
            </el-menu-item>


            <el-menu-item index="2-3" @click="RouteTo(4)">博文</el-menu-item>

            <el-submenu index="2-4">
              <template slot="title">选项4</template>
              <el-menu-item index="2-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title"><i class="el-icon-setting"></i>导航三</template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="3-1">选项1</el-menu-item>
              <el-menu-item index="3-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="3-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="3-4">
              <template slot="title">选项4</template>
              <el-menu-item index="3-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-container>
        <el-header style="text-align: right; font-size: 12px">
          <el-dropdown>
            <i class="el-icon-setting" style="margin-right: 15px"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>查看</el-dropdown-item>
              <el-dropdown-item>新增</el-dropdown-item>
              <el-dropdown-item>删除</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <el-link :underline="false" type="primary" @click="drawer = true">音乐</el-link>
          <el-link :underline="false" type="primary" @click="PlayMusic">{{ MusicText }}</el-link>
          <a>夜间模式</a>
          <!--name地址，params传递参数-->
          <el-link :underline="false" type="primary" @click="RouteTo(3)">个人中心</el-link>

        </el-header>


        <el-main>
          <!--走马灯-->
          <div v-if="show==='true'">
            <template>
              <el-carousel :interval="4000" type="card">
                <el-carousel-item v-for="item in picturelist" :key="item" @click.native="Rooto(Texts[item].tid)">
                  <img :src='"http://localhost:8888/picture/"+Texts[item].picture' style="max-width: 100%; height-width: 100%">
                </el-carousel-item>
              </el-carousel>
            </template>


          </div>
          <router-view></router-view>
          <el-drawer
            title="BGM"
            direction="btt"
            :visible.sync="drawer"
            :with-header="true">
            <audio :src="musicPath" controls="controls" id="music-audio">
              您的浏览器不支持 audio 标签
            </audio>
          </el-drawer>


        </el-main>


      </el-container>
    </el-container>
  </div>
</template>

<script>
import MusicPath from "../assets/周杰伦-夜曲.mp3"
import PictureStatic from "../assets/a.jpg"


export default {
  name: 'Main',
  data() {
    return {
      msg: "Welcom to FirstApp",
      //传递参数
      //分页页数
      currentPage1: 5,
      currentPage2: 5,
      currentPage3: 5,
      currentPage4: 4,

      show: 'false',

      //抽屉
      drawer: false,
      musicPath: MusicPath,
      Isplay: false,
      MusicText: '关',

      //走马灯
      picturelist: [PictureStatic],
      Texts: this.$root.$data.TextWords,

    }
  },
  methods: {
    PlayMusic() {
      let music = document.getElementById("music-audio");
      if (music != null) {
        if (this.Isplay) {
          music.pause();
          this.MusicText = '关';
        } else {
          music.play();
          this.MusicText = '开';
        }
        this.Isplay = !this.Isplay;
      } else {
        this.$notify.error({
          title: '错误',
          message: '请点击音乐，导入资源'
        })
      }
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
    RouteTo(n) {
      switch (n) {
        case 1:
          this.$router.push({name: 'Main'});
          this.ShowUp();
          break;//首页
        case 2:
          this.$router.push({path: '/user/list'});
          this.ShowDown();
          break;//用户列表（动画演示）
        case 3:
          this.$router.push({name: 'UserProfile'});
          this.ShowDown();
          break;//（个人信息）
        case 4:
          this.$router.push({path: '/user/Content'});
          this.ShowDown();
          break;//博文
      }
    },
    ShowDown() {
      this.show = 'false';
    },
    ShowUp() {
      this.show = 'true';
    },
    Rooto(item) {
      localStorage.setItem('tid', item) //  保存主键tid
      this.show='false';
      this.$router.push({path:'/user/Content/TextMessage'});
    },
    randomFiveDiffNum(n) {
      let num = [];
      for (let i = 0; i < 5; i++) {
        num[i] = Math.floor(Math.random() * n);
        for (let j = 0; j < i; j++) {
          if (num[i] == num[j]) {
            i--;
          }
        }
      }
      return num;
    }
  },
  mounted() {
  },
  computed: {},
  beforeRouteLeave: (to, from, next) => {
    next();
  },
  created() {
    const _this = this;
    this.$axios.get('http://localhost:8888/text/findAll/').then(function (resp) {
      _this.$root.$data.TextWords=resp.data;
      _this.Texts = resp.data;
      _this.picturelist=_this.randomFiveDiffNum(_this.Texts.length )

      //console.log(_this.picturelist);
    }).catch(
      function (error) {
        // 请求失败处理
        //console.log(error)
        _this.$notify.error({
          title: '错误',
          message: error
        });
      })

    /*this.$axios.get('http://localhost:8888/user/selectUser?id='+this.$root.$data.SelectedId).then(function (resp) {

      console.log(resp.data);
    }).catch(
      function (error) {
        // 请求失败处理
        this.$notify.error({
          title: '错误',
          message: '传递值失败'
        });
      })*/

  }


}


</script>

<style scoped>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}

.a {
  text-underline: none;
}

.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
</style>
