import axios from "axios";
import {baseURL} from "../../public/config";
//设置公共后台url地址
const server = axios.create({
    baseURL: baseURL,
});

export default server;
