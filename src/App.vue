<template>
<!--主页面-->
  <div id="app">
    <router-link to="/"></router-link>
    <router-view></router-view>


  </div>
</template>

<script>
import HelloWorld from "./components/Main";
import Content from "./components/user/Content";
export default {
  name: 'App',
  components: {HelloWorld},


}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
