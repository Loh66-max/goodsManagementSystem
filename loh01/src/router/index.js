// src/router/index.js
import Vue from 'vue';
import Router from 'vue-router';
// import LoginPage from '../views/LoginPage.vue';
// import HomePage from '../views/HomePage.vue';
import userLogin from "@/components/User/userLogin.vue";
import mainPage from "@/components/mainPage.vue";
import goodsManage from "@/components/goods/goodsManage.vue";
import userManage from "@/components/User/userManage.vue";
import MainMain from "@/components/main/main.vue";


// 注册 Vue Router
Vue.use(Router);

const routes = [
    {
        path: '/',
        name: 'userLogin',
        component: userLogin
    },
    {
        path: '/mainPage',
        name: 'mainPage',
        meta:{
            title:'首页'
        },
        component:mainPage,
        children:[
            {
                path: '/main',
                name: 'main',
                meta:{
                    title:'首页'
                },
                component:MainMain
            },

            {
                path: '/goodsManage',
                name: 'goodsManage',
                meta:{
                    title:'货品管理'
                },
                component:goodsManage
            },
            {
                path: '/userManage',
                name: 'userManage',
                meta:{
                    title:'用户管理'
                },
                component:userManage
            }
        ]
    }

];

const router = new Router({
    routes // short for `routes: routes`
});

export default router;
