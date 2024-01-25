export interface Resource{
    id: string;
    type: string;
    name: string;
    md5: string;
    host: string;
    bucket: string;
    path: string;
    order: number;
    userId: number;
    createTime: string; 
    size: number;
    state: number;
}