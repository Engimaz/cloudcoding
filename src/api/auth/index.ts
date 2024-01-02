/*
 * @Description: 
 * @Version: 2.0
 * @Author: AICHEN
 * @Date: 2023-06-24 00:35:19
 * @LastEditors: AICHEN
 * @LastEditTime: 2023-10-22 01:45:26
 */
import request from '../index.ts';
import type { RegisterUser, TokenRes, UpdateUser, User } from './types.ts'
const context = "cloud-coding-auth";

export type Grant_Type = "password" | "phone" | "gitee" | "email";

// 获取token
export const getToken = (username: string, password: string, grant_type: Grant_Type) => {
    const url = `${context}/oauth/login`;
    const method = "post";
    return request<TokenRes>(url, method, { client_id: "cloud-coding", client_secret: "N/A", scope: "all", username, password, grant_type });
}

// 退出登录
export const logout = () => {
    const url = `${context}/oauth/logout`;
    const method = "post";
    return request<string>(url, method);
}

// 注册用户
export const addUser = (data: RegisterUser) => {
    const url = `${context}/user/new`;
    const method = "post";
    return request<User>(url, method, data);
}
// 更新用户
export const updateUser=(data:UpdateUser)=>{
    const url = `${context}/user/update`;
    const method = "put";
    return request<User>(url, method, data);
}
// 查询用户信息
export const getUserInfo=(id:string)=>{
    const url = `${context}/user/${id}`;
    const method = "get";
    return request<User>(url, method);
}