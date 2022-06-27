import axios from "../util/index";
import {FileURL} from "../../public/config";

//查询所有文件
export function selectAllFile(current, size) {
    return axios({
        url: `/file/select/${current}/${size}`,
        method: "GET",
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//条件查询文件
export function selectAllFileBySource(current, size, fileName) {
    return axios({
        url: `/file/selectB/${current}/${size}`,
        method: "GET",
        params: {
            fileName: fileName,
        },
        headers: {
            token: sessionStorage.getItem("token"),
        },
    });
}

//删除一个文件
export function deleteFileById(id) {
    return axios({
        url: `${FileURL}delete/${id}`,
        method: "DELETE",
    });
}
