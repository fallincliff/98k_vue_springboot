<template>
  <div>
  <el-col :span="8" v-for="(o, index) in 1" :key="o" :offset="index > 0 ? 2 : 0">
    <el-card :body-style="{ padding: '0px' }" el-card shadow="hover">
      <img style="width: 100%;height:100%" :src="path">
      <div style="padding: 14px;">
        <span>用户:{{username }}</span>

        <br>
        <el-switch
          v-model='Isgz'
          active-text="已关注"
          inactive-text="未关注">
        </el-switch>

      </div>
    </el-card>
  </el-col>
  </div>
</template>

<script>
import staticPicture from "../../../assets/userlogo.jpg";

export default {
  name: "UserCard",
  props: {
    username:{type:String,
    default:'wode98k'},
    Isgz:{type:Boolean,
      default:false}
    ,path:{type:String,
      default:staticPicture}
  },
data() {
  return {

  };
}


}
</script>

<style scoped>
.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}
</style>
