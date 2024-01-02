/*
 * @Description: 
 * @Version: 2.0
 * @Author: AICHEN
 * @Date: 2023-06-24 00:35:19
 * @LastEditors: AICHEN
 * @LastEditTime: 2023-11-02 02:18:49
 */
import request from '../index.ts';
import type { QueryListResult } from '../types.ts'
import { Course } from './types.ts';

const context = "/cloud-coding-course";
// 获得一些课程 list
export const getCourseList = (page: number, size: number, keyword: string) => {
    const url = `${context}/course/list`;
    const method = "get";
    return request<QueryListResult<Course>>(url, method, { page, size, keyword });
}

// 获得一个课程
export const getCourse = (id: string) => {
    const url = `${context}/course/${id}`;
    const method = "get";
    return request<Course>(url, method);
}
// 删除一个课程
export const deleteCourse = (id: string) => {
    const url = `${context}/course/${id}`;
    const method = "delete";
    return request<Course>(url, method);
}

// 更新一个课程
export const updateCourse = (data: Course) => {
    const url = `${context}/course/update`;
    const method = "put";
    return request<Course>(url, method, data);
}

// 添加一个课程
export const addCourse = (data: Course) => {
    const url = `${context}/course/new`;
    const method = "post";
    return request<Course>(url, method, data);
}