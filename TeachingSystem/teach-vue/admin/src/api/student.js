import router from "../util/index";

//分宜查询所有学生
export function selectAllStudent(current, size) {
    return router({
        url: `/student/${current}/${size}`,
        method: "GET",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//根据班级名称查询学生
export function selectAllStudentByClass(current, size, className) {
    return router({
        url: `/student/${current}/${size}/${className}`,
        method: "GET",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//根据学生编号查询学生
export function selectAllStudentById(studentId) {
    return router({
        url: `/student/${studentId}`,
        method: "GET",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//添加学生
export function addStudent(obj) {
    return router({
        url: `/student`,
        method: "POST",
        data: obj,
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//根据学生编号修改学生
export function updateStudentById(obj) {
    return router({
        url: `/student`,
        method: "PUT",
        data: obj,
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//根据学生编号删除学生(index值为true,则代表删除,反之代表恢复)
export function deleteStudentById(map) {
    return router({
        url: `/student`,
        method: "DELETE",
        data: map,
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}
