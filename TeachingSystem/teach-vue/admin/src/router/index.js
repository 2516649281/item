import {
    createRouter,
    createWebHashHistory,
    createWebHistory,
} from "vue-router";
//登录
import login from "../views/login";
//注册
import register from "../views/register";
//公共界面
import publicView from "../views/public";
//个人管理
import user from "../views/user";
//学生管理
import student from "../views/student";
//教师管理
import teacher from "../views/teacher";
//管理员管理
import admin from "../views/admin";
//班级管理
import classView from "../views/class";
//班级管理
import subject from "../views/subject";
//绑定身份
import identity from "../views/identity";
//文件
import file from "../views/file";

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
            //个人管理
            {
                path: "/user",
                name: "user",
                component: user,
            },
            //学生管理
            {
                path: "/student",
                name: "student",
                component: student,
            },
            //教师管理
            {
                path: "/teacher",
                name: "teacher",
                component: teacher,
            },
            //管理员管理
            {
                path: "/admin",
                name: "admin",
                component: admin,
            },
            //班级管理
            {
                path: "/class",
                name: "class",
                component: classView,
            },
            //科目
            {
                path: "/subject",
                name: "subject",
                component: subject,
            },
            //身份
            {
                path: "/identity",
                name: "identity",
                component: identity,
            },
            //文件
            {
                path: "/file",
                name: "file",
                component: file,
            },
        ],
    },
];

const router = createRouter({
    mode: "history",
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
