import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios';
import VueAxios from 'vue-axios';
import Vuex from 'vuex';

//import ElementPlus from 'element-plus';
//import 'element-plus/lib/theme-chalk/index.css';
import staticPicture from "./assets/a.jpg"
Vue.config.productionTip = false

//显示声明，使用vuerouter
//Vue.use(ElementPlus)
Vue.use(router);
Vue.use(Vuex);
Vue.use(ElementUI);
Vue.use(VueAxios, axios);
Vue.prototype.$axios = axios;
//Vue.prototype.$http=axios;
new Vue({
  el: '#app',
  //配置路由
  router,
  components: {App},
  template: '<App/>',
  render: h => h(App),
  data: function () {
    return {
      SelectedId: '',
      users: [{
        id: '1',
        name: 'wode98k',
        password: '123456',
        sex: '男',
        jurisdiction:0,
      }],
      TextWords: [{
        tid: '1',
        author: 'wode98k',
        path: '服务器无响应.txt',
        tag: '随笔',
        picture: 'a.jpg'
      }]
    }
  },


})
