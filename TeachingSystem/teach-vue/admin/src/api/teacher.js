import router from "../util/index";

//分宜查询所有教师
export function selectAllTeacher(current, size) {
    return router({
        url: `/teacher/${current}/${size}`,
        method: "GET",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//根据科目编号查询教师
export function selectAllTeacherBySubject(current, size, subjectId) {
    return router({
        url: `/teacher/${current}/${size}/${subjectId}`,
        method: "GET",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//根据教师编号查询教师
export function selectAllTeacherById(teacherId) {
    return router({
        url: `/teacher/${teacherId}`,
        method: "GET",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//添加教师
export function addTeacher(obj) {
    return router({
        url: `/teacher`,
        method: "POST",
        data: obj,
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//根据教师编号修改教师
export function updateTeacherById(obj) {
    return router({
        url: `/teacher`,
        method: "PUT",
        data: obj,
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//根据教师编号删除教师(index值为true,则代表删除,反之代表恢复)
export function deleteTeacherById(map) {
    return router({
        url: `/teacher`,
        method: "DELETE",
        data: map,
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}
