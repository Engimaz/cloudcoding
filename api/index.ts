/*
 * @Description:
 * @Version: 2.0
 * @Author: AICHEN
 * @Date: 2023-06-24 00:29:33
 * @LastEditors: AICHEN
 * @LastEditTime: 2023-08-22 21:06:51
 */

import axios, { AxiosError } from "axios";
import type { Method, AxiosRequestConfig } from "axios";
import { ApiResponse } from "@/api/types.ts";
import errorHandler from "./error-handler.ts";
import { CLOUD_CODING_GATEWAY } from "@/config/base-url.ts";

// 创建实例
const service = axios.create({
  // 后端接口地址
  baseURL: CLOUD_CODING_GATEWAY,
  // 请求超时时间
  timeout: 8000,
  headers: {
    "Content-Type": "application/json;charset=utf-8",
  },
});

// 注入文件中的后端地址
// if (import.meta.env.CLOUD_CODING_GATEWAY) {
// 	service.defaults.baseURL = import.meta.env.CLOUD_CODING_GATEWAY;
// }

// 请求拦截
service.interceptors.request.use(async (config) => {
  // 假如上面没有定义 header 会报错 因此在这里先声明一个
  config.headers = config.headers || {};
  const token = await window.localStorage.getItem("token");
  const tokenType = await window.localStorage.getItem("tokenType");

  // 假如有token 就加到请求头里面
  if (token && tokenType) {
    config.headers.Authorization = `${tokenType} ${token}`;
  }
  return config;
});

// 响应拦截
service.interceptors.response.use(
  (res) => {
    return res.data;
  },
  (err: AxiosError) => {
    if (err.response) {
      errorHandler(err.response.status);
    }
    return Promise.reject(err.response);
  }
);

const request = <T>(
  url: string,
  method: Method,
  data?: object | string,
  config?: AxiosRequestConfig
) => {
  return service.request<T, ApiResponse<T>>({
    url,
    method,
    [method.toLowerCase() === "get" ? "params" : "data"]: data,
    // 解构剩余配置
    ...config,
  });
};
export default request;
