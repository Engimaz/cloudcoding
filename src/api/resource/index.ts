/*
 * @Description:
 * @Version: 2.0
 * @Author: AICHEN
 * @Date: 2023-11-02 00:37:40
 * @LastEditors: AICHEN
 * @LastEditTime: 2023-11-04 12:09:21
 */
import request from "../index.ts";
import type { Resource } from "./types.ts";
import computeMD5 from "@/features/compute-md5/index.ts";
const context = "/cloud-coding-resource";

// 添加一个项目
export const uploadResource = async (
  file: File | Blob,
  userId: string,
  _md5?: string,
  _chunkMd5?: string
) => {
  const url = `${context}/resource/upload/file`;
  const method = "post";
  let md5: string = _md5 || "";
  if (md5 !== "") {
    md5 = await computeMD5(file);
  }
  let chunkMd5 = _chunkMd5;
  if (!chunkMd5) {
    chunkMd5 = md5 as string;
  }
  const config = {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  };
  return request<Resource>(
    url,
    method,
    { userId, md5, file, chunkMd5 },
    config
  );
};

// 检查文件是否上传过
export const checkMd5 = (md5: string) => {
  const url = `${context}/resource/upload/file/check/${md5}`;
  const method = "get";
  return request<Resource>(url, method);
};

export const mergeResource = (
  md5: string,
  name: string,
  userId: string,
  type: string
) => {
  const url = `${context}/resource/upload/chunk/merge`;
  const method = "put";
  const config = {
    timeout: 800000,
  };
  return request<Resource>(url, method, { md5, name, userId, type }, config);
};
