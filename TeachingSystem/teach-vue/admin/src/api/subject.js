import router from "../util/index";

//分宜查询所有科目
export function selectAllSubject(current, size) {
    return router({
        url: `/subject/${current}/${size}`,
        method: "GET",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//根据名称查询科目
export function selectAllSubjectByName(current, size, subjectName) {
    return router({
        url: `/subject/${current}/${size}/${subjectName}`,
        method: "GET",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//根据编号查询科目
export function selectAllSubjectById(subjectId) {
    return router({
        url: `/subject/${subjectId}`,
        method: "GET",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//添加科目
export function addSubject(obj) {
    return router({
        url: `/subject`,
        method: "POST",
        data: obj,
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//根据编号修改科目
export function updateSubjectById(obj) {
    return router({
        url: `/subject`,
        method: "PUT",
        data: obj,
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//根据科目编号删除科目(index值为true,则代表删除,反之代表恢复)
export function deleteSubjectById(map) {
    return router({
        url: `/subject`,
        method: "DELETE",
        data: map,
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}
