/*
 * @Description: 
 * @Version: 2.0
 * @Author: AICHEN
 * @Date: 2023-06-24 00:35:19
 * @LastEditors: AICHEN
 * @LastEditTime: 2023-11-01 00:50:37
 */
import request from '../index.ts';
import { Folder } from './types.ts';

const context = "/cloud-coding-program";


// 获得一个文件夹下的子文件夹和他的文件
export const queryFolderById = (folder: string) => {
    const url = `${context}/folder/${folder}`;
    const method = "get";
    return request<Folder>(url, method);
}
// 获得项目顶级文件夹

export const queryTopFolderByProgramId = (programId: string) => {
    const url = `${context}/folder/top/${programId}`;
    const method = "get";
    return request<Folder>(url, method);
}

// 创建一个文件夹
export const createFolder = (data: Folder) => {
    const url = `${context}/folder/new`;
    const method = "post";
    return request<Folder>(url, method, data);
}// 更新一个文件夹
export const updateFolder = (data: Folder) => {
    const url = `${context}/folder/update`;
    const method = "put";
    return request<Folder>(url, method, data);
}