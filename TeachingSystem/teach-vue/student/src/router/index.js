import {
    createRouter,
    createWebHashHistory,
    createWebHistory,
} from "vue-router";
// 登录
import login from "../views/login";
// 公共页面
import publicView from "../views/public";
// 主页
import index from "../views/index";
// 我的班级
import myClass from "../views/myClass";
// 用户信息
import user from "../views/user";
// 提交的作业
import submitWork from "../views/submitWork";
//需完成的作业
import completeWork from "../views/completeWork";
//注册
import register from "../views/register";
//身份绑定
import identity from "../views/identity";

const routes = [
    // 登录
    {
        path: "/login",
        name: "login",
        component: login,
    },
    //注册
    {
        path: "/register",
        name: "register",
        component: register,
    },
    // 公共
    {
        path: "/",
        name: "public",
        component: publicView,
        redirect: "/index",
        children: [
            // 主页
            {
                path: "/index",
                name: "index",
                component: index,
            },
            // 我的班级
            {
                path: "/myClass",
                name: "myClass",
                component: myClass,
            },
            // 用户管理
            {
                path: "/user",
                name: "user",
                component: user,
            },
            // 已提交的作业
            {
                path: "/submitWork",
                name: "submitWork",
                component: submitWork,
            },
            //需完成的作业
            {
                path: "/completeWork",
                name: "completeWork",
                component: completeWork,
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

router.beforeEach((to, from, next) => {
    if (to.name != "login" && to.name != "register" && to.name != "index") {
        var token = sessionStorage.getItem("token");
        if (token == null) {
            next({path: "/login"});
        } else {
            next();
        }
    } else {
        next();
    }
});

export default router;
