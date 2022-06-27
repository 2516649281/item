import router from "../util/index";

//查询所有已布置的作业
export function selectAllWork(current, size) {
    return router({
        url: `/work/${current}/${size}`,
        method: "GET",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//根据教师编号查询作业
export function selectAllWorkByTeacherId(current, size, teacherId) {
    return router({
        url: `/work/${current}/${size}/${teacherId}`,
        method: "GET",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//添加作业
export function addWork(obj) {
    return router({
        url: "/work",
        method: "POST",
        data: obj,
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//修改作业
export function updateWorkById(obj) {
    return router({
        url: "/work",
        method: "PUT",
        data: obj,
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//删除作业:操作指数(true代表查询未删除的数据,false代表查询已删除的数据)
export function deleteWork(workId, index) {
    return router({
        url: `/work/${workId}/${index}`,
        method: "DELETE",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//根据班级查询学生
export function selectAllStudent(classId) {
    return router({
        url: `/work/${classId}?student`,
        method: "GET",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//根据作业编号查询学生作业完成情况
export function selectAllSubmit(workId) {
    return router({
        url: `/work/${workId}?sw`,
        method: "GET",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//根据班级编号查询教师已布置的作业
export function selectAllWorkByClassId(classId) {
    return router({
        url: `/work${classId}?work`,
        method: "GET",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//批改学生作业
export function updateSubmit(obj) {
    return router({
        url: `/work/sc`,
        method: "PUT",
        data: obj,
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//查询所有班级
export function selectAllClass() {
    return router({
        url: "/work?class",
        method: "GET",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}
