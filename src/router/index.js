import Vue from 'vue';
import Router from 'vue-router';



import Content from "../components/user/Content";
import Main from "../components/Main";
import Login from '../components/Login';
import Signup from '../components/Signup';
import UserList from '../components/user/List';
import UserProfile from '../components/user/Profile';
import NotFound from "../components/NotFound";
import TextMessage from "../components/user/Content/TextMessage";
import Write from "../components/user/Profile/Wirte";
import MesList from "../components/user/MessageList";

Vue.use(Router)
export default new Router({
  mode:'history',
  routes: [
    {
      //路由路径
      path: '/main',
      name: 'Main',
      //跳转的组件
      component: Main,
      //嵌套路由
      children: [
        //:id
        {path:'/user/profile',name:'UserProfile' ,component:UserProfile,props:true},//个人中心
        {path:'/user/list',component:UserList},//动画渲染
        {path: '/user/content', component: Content,},//博文
        {path:'/user/Content/TextMessage',component:TextMessage},//博文：浏览全文页面
        {path:'/user/MessageList',name:'MesList' ,component:MesList,props:true},//消息中心
      ]
    },
    {
      //路由路径
      path: '/user/Profile/Write',
      name: 'Write',
      props:true,
      //跳转的组件
      component: Write,
    },
    {
      //路由路径
      path: '/',
      name: 'Login',
      props:true,
      //跳转的组件
      component: Login,


    },
    {
      //路由路径
      path: '/signup',
      name: 'Signup',
      //跳转的组件
      component: Signup
    },
    {
      //路由路径
      path: '*',
      //跳转的组件
      component: NotFound
    }
  ]
})
