/*
 * @Description: 
 * @Version: 2.0
 * @Author: AICHEN
 * @Date: 2023-06-24 00:35:19
 * @LastEditors: AICHEN
 * @LastEditTime: 2023-09-26 18:18:53
 */
import request from '../index.ts';
import type { Captcha } from './types.ts';

const context = "/cloud-coding-captcha";
// 发送邮箱验证码
export const getEmailCode = (captcha: Captcha) => {
    const url = `${context}/email/send`;
    const method = "post";
    return request<string>(url, method, captcha);
}
// 获得电话验证码
export const getPhoneCode = (captcha: Captcha) => {
    const url = `${context}/email/send`;
    const method = "post";
    return request<string>(url, method, captcha);
}


