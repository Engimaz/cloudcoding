/*
 * @Description:
 * @Version: 2.0
 * @Author: AICHEN
 * @Date: 2023-06-24 00:35:19
 * @LastEditors: AICHEN
 * @LastEditTime: 2023-10-30 00:23:23
 */
import request from "../index.ts";
import type {
  FeatureVO,
  Feature,
  Organization,
  OrganizationRecord,
  OrganizationRecordAggregate,
  OrganizationVO,
  Url,
  UrlVO,
} from "./types.ts";
import type { QueryListResult } from "../types.ts";

const context = "/cloud-coding-manager";
// 获得url list
export const getUrlList = (page: number, size: number, keyword: string) => {
  const url = `${context}/url/list`;
  const method = "get";
  return request<QueryListResult<UrlVO>>(url, method, { page, size, keyword });
};

export const getAllUrl = () => {
  const url = `${context}/url/all`;
  const method = "get";
  return request<QueryListResult<UrlVO>>(url, method);
};

// 添加一个url
export const insertUrl = (data: Url) => {
  const url = `${context}/url/new`;
  const method = "post";
  return request<UrlVO>(url, method, data);
};
// 更新一个url
export const updateUrl = (data: Url) => {
  const url = `${context}/url/update`;
  const method = "put";
  return request<UrlVO>(url, method, data);
};

// 删除一个url
export const deleteUrl = (id: number | string) => {
  const url = `${context}/url/${id}`;
  const method = "delete";
  return request<boolean>(url, method);
};

export const getOrganizationList = (
  page: number,
  size: number,
  keyword: string,
  status?: string
) => {
  let url;
  if (status) {
    url = `${context}/organization/list/${status}`;
  } else {
    url = `${context}/organization/list`;
  }
  const method = "get";
  return request<QueryListResult<OrganizationVO>>(url, method, {
    page,
    size,
    keyword,
  });
};
// 新增一个组织
export const insertOrganization = (data: Organization) => {
  const url = `${context}/organization/new`;
  const method = "post";
  return request<OrganizationVO>(url, method, data);
};
// 更新一个组织
export const updateOrganization = (data: Organization) => {
  const url = `${context}/organization/update`;
  const method = "put";
  return request<OrganizationVO>(url, method, data);
};
// 添加组织
export const addOrganization = (data: Organization) => {
  const url = `${context}/organization/new`;
  const method = "post";
  return request<OrganizationVO>(url, method, data);
};

// 查询一个功能
export const getFeatureList = (page: number, size: number, keyword: string) => {
  const url = `${context}/feature/list`;
  const method = "get";
  return request<QueryListResult<FeatureVO>>(url, method, {
    page,
    size,
    keyword,
  });
};

export const getAllFeature = () => {
  const url = `${context}/feature/all`;
  const method = "get";
  return request<QueryListResult<FeatureVO>>(url, method);
};


// 删除一个功能
export const deleteFeature = (id: string) => {
  const url = `${context}/feature/${id}`;
  const method = "delete";
  return request<boolean>(url, method);
};
// 新增功能
export const insertFeature = (data: Feature) => {
  const url = `${context}/feature/new`;
  const method = "post";
  return request<FeatureVO>(url, method, data);
};

// 更新功能
export const updateFeature = (data: Feature) => {
  const url = `${context}/feature/update`;
  const method = "put";
  return request<FeatureVO>(url, method, data);
};
// 查询我所在的组织
export const getMyOrganization = (userId: string) => {
  const url = `${context}/organization/own/${userId}`;
  const method = "get";
  return request<Array<OrganizationVO>>(url, method);
};
// 查询一个组织
export const getOrganizationById = (id: string) => {
  const url = `${context}/organization/${id}`;
  const method = "get";
  return request<OrganizationVO>(url, method);
};

// 删除一个组织
export const deleteOrganizationById = (id: string) => {
  const url = `${context}/organization/${id}`;
  const method = "delete";
  return request<void>(url, method);
};

// 新增一个组织申请记录
export const addOrganizationRecord = (data: OrganizationRecord) => {
  const url = `${context}/organization/record/new`;
  const method = "post";
  return request<OrganizationRecord>(url, method, data);
};
// 查询我的申请记录
export const getMyOrganizationRecord = (userId: string) => {
  const url = `${context}/organization/record/list/user/${userId}`;
  const method = "get";
  return request<Array<OrganizationRecord>>(url, method);
};

// 查询组织的申请记录
export const getOrganizationRecordByOrganization = (id: string) => {
  const url = `${context}/organization/record/list/organization/${id}`;
  const method = "get";
  return request<OrganizationRecordAggregate>(url, method);
};
export const deleteOrganizationRecordById = (id: string) => {
  const url = `${context}/organization/record/${id}`;
  const method = "delete";
  return request<void>(url, method);
};
