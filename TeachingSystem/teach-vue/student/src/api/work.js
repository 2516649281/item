import router from "../util/index";

//查询所有作业
export function selectAllWork(current, size) {
    return router({
        url: `/work/${current}/${size}`,
        method: "GET",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//根据学生编号查询作业
export function selectAllWorkByStudentId(current, size, studentId) {
    return router({
        url: `/work/${current}/${size}/${studentId}`,
        method: "GET",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//提交作业
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

//修改已提交的作业
export function updateWork(obj) {
    return router({
        url: "/work",
        method: "PUT",
        data: obj,
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//删除已提交的作业
export function deleteWork(submitId, index) {
    return router({
        url: `/work/${submitId}/${index}`,
        method: "DELETE",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//查询学生
export function selectAllStudentByClassId(classId) {
    return router({
        url: `/work/${classId}?student`,
        method: "GET",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//查询所有教师已布置的作业
export function selectAllCreateWork(classId) {
    return router({
        url: `/work/${classId}?work`,
        method: "GET",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}
