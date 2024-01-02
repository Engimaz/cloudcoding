/*
 * @Description: 
 * @Version: 2.0
 * @Author: AICHEN
 * @Date: 2023-06-24 00:35:19
 * @LastEditors: AICHEN
 * @LastEditTime: 2023-10-14 15:13:54
 */
import request from '../index.ts';
import { File } from './types.ts'
const context = "/cloud-coding-program";




// 创建一个文件

export const createFile = (data: File) => {
    const url = `${context}/file/new`;
    const method = "post";
    return request<File>(url, method, data);
}

// 删除一个文件
export const deleteFile = (id: string) => {
    const url = `${context}/file/${id}`;
    const method = "delete";
    return request<boolean>(url, method);
}

// 更新一个文件

export const updateFile = (data: File) => {
    const url = `${context}/file/update`;
    const method = "put";
    return request<File>(url, method, data);
}