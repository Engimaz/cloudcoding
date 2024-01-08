/*
 * @Description:
 * @Version: 2.0
 * @Author: AICHEN
 * @Date: 2023-08-12 23:37:07
 * @LastEditors: AICHEN
 * @LastEditTime: 2023-10-26 01:10:58
 */

import { User } from "../auth/types.ts";

export interface Url {
  id: number | string | null;
  name: string;
  description: string;
  value: string;
  status: string;
  scope: string;
}

export interface UrlVO extends Url {
  createTime: string;
  updateTime: string;
}

export interface Feature {
  id?: string | null;
  name: string;
  status: string;
  value: string;
  description: string;
  urls: Array<string>; // url id
}
export interface FeatureVO extends Feature {
  createTime: string;
  updateTime: string;
}

export interface Position {
  id?: string;
  name: string;
  status: string;
  value: string;
  organizationId?: string;
}

export interface UserPosition {
  userId: string;
  position: string;
}

export interface UserPositionVO {
  userId: string;
  position: {
    code: string,
    name: string
  };
}

export interface Organization {
  id: string | null;
  name: string;
  avatar: string; // 组织头像
  img: string; // 宣传图
  description: string; //组织介绍
  location: string; // 组织地址 省区级
  address: string; //详细地址
  type: string; //组织类型
  status: string; //组织状态
  positions: Array<Position>;
  features: Array<Feature>;
  userPositions: Array<UserPosition>;
}

export interface OrganizationVO extends Organization {
  createTime: string;
  updateTime: string;
}

export interface FeatureUrlVO {
  id?: string;
  featureId?: string;
  urlId: string;
}

export interface OrganizationRecord {
  id: string;
  userId: string;
  organizationId: string;
  content: string;
}

export interface OrganizationRecordAggregate {
  records: Array<OrganizationRecord>;
  users: Array<User>;
}
