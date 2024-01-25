/*
 * @Description: 
 * @Version: 2.0
 * @Author: AICHEN
 * @Date: 2023-06-24 00:35:19
 * @LastEditors: AICHEN
 * @LastEditTime: 2023-10-16 22:35:32
 */
import request from '../index.ts';
import { Article, Topic } from './types.ts';
import type { QueryListResult } from '../types.ts'

const context = "/cloud-coding-forum";


// 创建一个文章
export const createArticle = (data: Article) => {
    const url = `${context}/article/new`;
    const method = "post";
    return request<Article>(url, method, data);
}

// 获得一个文章
export const getArticle = (id: string) => {
    const url = `${context}/article/${id}`;
    const method = "get";
    return request<Article>(url, method);
}

export const updateArticle = (data: Article) => {
    const url = `${context}/article/update`;
    const method = "put";
    return request<Article>(url, method, data);
}

// 申请一些文章
export const queryListArticle = (page: number, size: number, keyword: string, status: string) => {

    const url = `${context}/article/list`;
    const method = "get";
    return request<QueryListResult<Article>>(url, method, { page, size, keyword, status });
}

// 获得我的文章
export const queryMyArticle = (page: number, size: number, keyword: string, userId: string) => {

    const url = `${context}/article/list/${userId}`;
    const method = "get";
    return request<QueryListResult<Article>>(url, method, { page, size, keyword });
}

// 插入专栏
export const insertTopic = (data: Topic) => {
    const url = `${context}/topic/new`;
    const method = "post";
    return request<Topic>(url, method, data);
}

// 更新专栏
export const updateTopic = (data: Topic) => {
    const url = `${context}/topic/update`;
    const method = "put";
    return request<Topic>(url, method, data);
}

// 删除专栏
export const deleteTopic = (id: string, isDelete: boolean) => {

    const url = `${context}/topic/${id}/${isDelete}`;
    const method = "delete";
    return request<boolean>(url, method);
}

// 查询一个专栏
export const getTopic = (id: string) => {
    const url = `${context}/topic/${id}`;
    const method = "get";
    return request<Topic>(url, method);
}

// 查询我的专栏
export const queryMyTopic = (page: number, size: number, keyword: string, userId: string) => {

    const url = `${context}/topic/list/${userId}`;
    const method = "get";
    return request<QueryListResult<Topic>>(url, method, { page, size, keyword });
}