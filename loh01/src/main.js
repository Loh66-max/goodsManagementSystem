import Vue from 'vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue';
import axios from "axios";
import VueRouter from 'vue-router';
import router from "@/router";


Vue.config.productionTip = false;
Vue.use(ElementUI, {
  size: 'small'
})
Vue.use(VueRouter)
Vue.prototype.$axios = axios;
new Vue({
  router,
  el: '#app',
  render: h => h(App)
}).$mount('#app');