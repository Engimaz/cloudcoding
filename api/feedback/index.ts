import request from '../index.ts';

const context = "/cloud-coding-feedback";

export const addCollect = (objectId: string, userId: string) => {
    const url = `${context}/collect/new/${objectId}/${userId}`;
    const method = "POST";
    return request<void>(url, method);
};

export const removeCollect = (objectId: string, userId: string) => {
    const url = `${context}/collect/delete/${objectId}/${userId}`;
    const method = "DELETE";
    return request<void>(url, method);
};

export const isCollectByUser = (objectId: string, userId: string) => {
    const url = `${context}/collect/isCollected/${objectId}/${userId}`;
    const method = "GET";
    return request<boolean>(url, method);
};

export const countCollect = (objectId: string) => {
    const url = `${context}/collect/countCollect/${objectId}`;
    const method = "GET";
    return request<number>(url, method);
};

export const addLike = (objectId: string, userId: string) => {
    const url = `${context}/like/new/${objectId}/${userId}`;
    const method = "POST";
    return request<void>(url, method);
};

export const removeLike = (objectId: string, userId: string) => {
    const url = `${context}/like/delete/${objectId}/${userId}`;
    const method = "DELETE";
    return request<void>(url, method);
};

export const isLikeByUser = (objectId: string, userId: string) => {
    const url = `${context}/like/isLiked/${objectId}/${userId}`;
    const method = "GET";
    return request<boolean>(url, method);
};

export const countLike = (objectId: string) => {
    const url = `${context}/like/countLike/${objectId}`;
    const method = "GET";
    return request<number>(url, method);
};

//
export const addDisLike = (objectId: string, userId: string) => {
    const url = `${context}/dislike/new/${objectId}/${userId}`;
    const method = "POST";
    return request<void>(url, method);
};

export const removeDisLike = (objectId: string, userId: string) => {
    const url = `${context}/dislike/delete/${objectId}/${userId}`;
    const method = "DELETE";
    return request<void>(url, method);
};

export const isDisLikeByUser = (objectId: string, userId: string) => {
    const url = `${context}/dislike/isDisLiked/${objectId}/${userId}`;
    const method = "GET";
    return request<boolean>(url, method);
};

export const countDisLike = (objectId: string | number) => {
    const url = `${context}/dislike/countDisLike/${objectId}`;
    const method = "GET";
    return request<number>(url, method);
};


