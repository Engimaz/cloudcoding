/*
 * @Description: 
 * @Version: 2.0
 * @Author: AICHEN
 * @Date: 2023-06-24 00:35:19
 * @LastEditors: AICHEN
 * @LastEditTime: 2023-11-04 20:46:24
 */
import request from '../index.ts';
import type { DictionaryGroup } from './types.ts';
import type { QueryListResult } from '../types.ts'

const context = "/cloud-coding-dictionary";
// 获取一页字典 list
export const getGroupDictionaryList = (page: number, size: number, keyword: string) => {

    const url = `${context}/group/query`;
    const method = "get";
    return request<QueryListResult<DictionaryGroup>>(url, method, { page, size, keyword });
}

// 更新一组字典
export const updateGroupDictionary = (group: DictionaryGroup) => {
    const url = `${context}/group/${group.id}`;
    const method = "put";
    return request<DictionaryGroup>(url, method, group);
}

// 新增一组字典
export const insertGroupDictionary = (group: DictionaryGroup) => {
    const url = `${context}/group/new`;
    const method = "post";
    return request<DictionaryGroup>(url, method, group);
}

// 删除一组字典
export const deleteGroupDictionary = (id: string | number) => {
    const url = `${context}/group/${id}`;
    const method = "delete";
    return request<boolean>(url, method);
}

// 查询一组字典
export const queryGroupDictionary = (id: string) => {
    const url = `${context}/group/${id}`;
    const method = "get";
    return request<DictionaryGroup>(url, method);
}

// 查询一组字典通过名称
export const queryGroupDictionaryByName = (name: string) => {
    const url = `${context}/group/query/${name}`;
    const method = "get";
    return request<DictionaryGroup>(url, method);
}