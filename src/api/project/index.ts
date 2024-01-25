/*
 * @Description: 
 * @Version: 2.0
 * @Author: AICHEN
 * @Date: 2023-06-24 00:35:19
 * @LastEditors: AICHEN
 * @LastEditTime: 2023-10-15 15:44:19
 */
import request from '../index.ts';
import type { Project, ExecutionInfo } from './types.ts'

const context = "/cloud-coding-program";

// 添加一个项目
export const crateProject = (data: Project) => {
    const url = `${context}/program/new`;
    const method = "post";
    return request<Project>(url, method, data);
}

// 查询用户的所有项目
export const queryProgramListByUserId = (userId: string) => {
    const url = `${context}/program/list/${userId}`;
    const method = "get";
    return request<Array<Project>>(url, method);
}

export const runProgram = (id: string, command: string) => {
    const url = `${context}/program/run`;
    const method = "post";
    return request<ExecutionInfo>(url, method, { id, command });
}