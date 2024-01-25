/*
 * @Description: 
 * @Version: 2.0
 * @Author: AICHEN
 * @Date: 2023-10-14 02:03:55
 * @LastEditors: AICHEN
 * @LastEditTime: 2023-10-14 17:26:16
 */
import { File } from '../file/types.ts'
export interface Folder {
    id: string,
    name: string,
    parentId: string,
    projectId: string,
    folders: Array<Folder>,
    files: Array<File>
}

