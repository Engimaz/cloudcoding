/*
 * @Description:
 * @Version: 2.0
 * @Author: AICHEN
 * @Date: 2023-09-26 12:27:01
 * @LastEditors: AICHEN
 * @LastEditTime: 2023-09-26 12:27:15
 */
export interface Dictionary {
  id: string | null;
  label: string;
  value: string;
}
export interface DictionaryGroup {
  id: string | null ;
  name: string;
  description: string;
  list: Dictionary[];
}
