/*
 * @Description: 
 * @Version: 2.0
 * @Author: AICHEN
 * @Date: 2023-06-24 00:35:19
 * @LastEditors: AICHEN
 * @LastEditTime: 2023-10-28 23:00:59
 */
import request from '../index.ts';
import type { Comment } from './types.ts';
import type { QueryListResult } from '../types.ts'

const context = "/cloud-coding-comment";
// 获取一页评论
export const getCommentList = (page: number, size: number, parentId: string) => {
    const url = `${context}/comment/list`;
    const method = "get";
    return request<QueryListResult<Comment>>(url, method, { page, size, parentId });
}


// 新增一个评论
export const addComment = (data: Comment) => {
    const url = `${context}/comment/new`;
    const method = "post";
    return request<Comment>(url, method, data);
}

// 删除一个评论
export const deleteComment = (id: string) => {
    const url = `${context}/comment/${id}`;
    const method = "delete";
    return request<boolean>(url, method);
}

export const getCount = (id: string) => {
    const url = `${context}/comment/count/${id}`;
    const method = "get";
    return request<number>(url, method);
}

