/*
 * @Description: 
 * @Version: 2.0
 * @Author: AICHEN
 * @Date: 2023-10-15 23:43:20
 * @LastEditors: AICHEN
 * @LastEditTime: 2023-10-31 01:15:38
 */
export interface Article {
    id: string,
    title: string,
    content: string,
    userId: string,
    topicId: string,
    avatar: string,
    status: string,
    label: string,
    createTime: string,
    updateTime: string
}

export interface Topic {
    id: string | null,
    userId: string,
    name: string,
    label: string,
    description: string,
    type: string,
    articles: Array<Topic>

}