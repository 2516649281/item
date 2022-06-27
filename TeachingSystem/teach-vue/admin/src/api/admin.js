import router from "../util/index";

//分宜查询所有管理员
export function selectAllAdmin(current, size) {
    return router({
        url: `/admin/${current}/${size}`,
        method: "GET",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//根据地址名称查询管理员
export function selectAllAdminByAddress(current, size, adminAddress) {
    return router({
        url: `/admin/${current}/${size}/${adminAddress}`,
        method: "GET",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//根据管理员编号查询管理员
export function selectAllAdminById(AdminId) {
    return router({
        url: `/admin/${AdminId}`,
        method: "GET",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//添加管理员
export function addAdmin(obj) {
    return router({
        url: `/admin`,
        method: "POST",
        data: obj,
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//根据管理员编号修改管理员
export function updateAdminById(obj) {
    return router({
        url: `/admin`,
        method: "PUT",
        data: obj,
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//根据管理员编号删除管理员(index值为true,则代表删除,反之代表恢复)
export function deleteAdminById(AdminId, index) {
    return router({
        url: `/admin/${AdminId}/${index}`,
        method: "DELETE",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}
