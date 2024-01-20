export interface Project {
  id?: number | string;
  name: string;
  description: string;
  avatar: string;
  language: string;
  template: string;
  sdk: string;
  policy: string;// 项目策略 公开还是私有
  relations: Array<ProjectUser>;
}

export interface ProjectUser {
  userId: string;
  role: string;
}

export interface ExecutionInfo {
  errorData: string;
  outputData: string;
}
