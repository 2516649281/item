import router from "../util/index";

//分宜查询所有班级
export function selectAllClass(current, size) {
    return router({
        url: `/class/${current}/${size}`,
        method: "GET",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//根据名称查询班级
export function selectAllClassByName(current, size, className) {
    return router({
        url: `/class/${current}/${size}/${className}`,
        method: "GET",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//根据编号查询班级
export function selectAllClassById(classId) {
    return router({
        url: `/class/${classId}`,
        method: "GET",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//添加班级
export function addClass(obj) {
    return router({
        url: `/class`,
        method: "POST",
        data: obj,
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//根据编号修改班级
export function updateClassById(obj) {
    return router({
        url: `/class`,
        method: "PUT",
        data: obj,
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//根据班级编号删除班级(index值为true,则代表删除,反之代表恢复)
export function deleteClassById(classId, index) {
    return router({
        url: `/class/${classId}/${index}`,
        method: "DELETE",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}
