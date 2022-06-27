import router from "../util/index";

// 登录
export function login(obj) {
    return router({
        url: "/user/login",
        method: "POST",
        data: obj,
    });
}

//修改密码
export function updatePassword(obj) {
    return router({
        url: "/user",
        method: "PUT",
        data: obj,
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//注册
export function register(obj) {
    return router({
        url: "/user/register",
        method: "POST",
        data: obj,
    });
}

//用户头像上传
export function headerUpload(headerFile, userId) {
    return router({
        url: `/user/${userId}`,
        method: "PUT",
        data: headerFile,
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//用户身份绑定
export function bindIdentity(userId, userIndex) {
    return router({
        url: `/user/${userId}/${userIndex}`,
        method: "PUT",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//轮询函数
export function getToken() {
    return router({
        url: "/user",
        method: "GET",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}
