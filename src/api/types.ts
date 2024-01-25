/*
 * @Description:
 * @Version: 2.0
 * @Author: AICHEN
 * @Date: 2022-11-04 21:36:33
 * @LastEditors: AICHEN
 * @LastEditTime: 2023-09-28 12:27:23
 */
// 泛型函数，接口，类
export interface ApiResponse<T> {
	// 基本上响应数据里的message都是string类型
	info: string;
	// 但是data的类型是变化的，所以我们不能写死，需要传给axios
	result: T;
	// 响应码
	code: number ;
}
export interface CommonQuery {
	page: number;
	size: number;
	keyword: string;
}
export interface QueryListResult<T> {
	count: number;
	commonQuery:CommonQuery
	list: T[];
}