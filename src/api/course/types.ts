export interface Course {
  id: string;
  name: string;
  avatar: string;
  description: string;
  units: Array<Unit>;
}
export interface Unit {
  id: string;
  name: string;
  order: string;
  nodes: Array<Node>;
}
export interface Node {
  id: string;
  url: string;
  name: string;
  type: string;
  description: string;
  status: string; // 节点状态
  visibility: string; //是否可见
}
