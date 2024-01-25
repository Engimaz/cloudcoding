/*
 * @Description: 
 * @Version: 2.0
 * @Author: AICHEN
 * @Date: 2023-06-01 16:05:21
 * @LastEditors: AICHEN
 * @LastEditTime: 2023-06-01 16:05:40
 */
export default (): string => {
    return Number(Math.random().toString().substr(3, 5) + Date.now()).toString(36)
}