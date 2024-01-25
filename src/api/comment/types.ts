

export interface Comment {
    id: string,
    userId: string,
    parentId: string,
    replyId: string,
    content: string,
    createTime: string,
    children?: Array<Comment>
}