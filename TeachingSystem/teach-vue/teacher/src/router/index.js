import {createRouter, createWebHashHistory} from "vue-router";
//登录
import login from "../views/login";
//注册
import register from "../views/register";
//公共
import publicView from "../views/public";
//检查作业
import examineWork from "../views/examineWork";
//已发布的作业
import lookWork from "../views/lookWork";
//账号管理
import user from "../views/user";
//身份绑定
import identity from "../views/identity";

const routes = [
    //登录
    {
        path: "/",
        name: "login",
        component: login,
    },
    //注册
    {
        path: "/register",
        name: "register",
        component: register,
    },
    //公共界面
    {
        path: "/public",
        name: "public",
        component: publicView,
        redirect: "/user",
        children: [
            //检查
            {
                path: "/examine",
                name: "examine",
                component: examineWork,
            },
            //已发布
            {
                path: "/look",
                name: "look",
                component: lookWork,
            },
            //账号管理
            {
                path: "/user",
                name: "user",
                component: user,
            },
            //身份绑定
            {
                path: "/identity",
                name: "identity",
                component: identity,
            },
        ],
    },
];

const router = createRouter({
    history: createWebHashHistory(process.env.BASE_URL),
    routes,
});

//登录拦截器
router.beforeEach((to, from, next) => {
    if (to.name != "login" && to.name != "register") {
        var token = sessionStorage.getItem("token");
        if (token == null) {
            next({name: "login"});
        } else {
            next();
        }
    } else {
        next();
    }
});

export default router;
