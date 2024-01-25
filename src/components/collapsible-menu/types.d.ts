import { ReactNode } from "react";

export interface MenuItem {
  label: ReactNode;
  id: string;
  icon?:ReactNode;
  type?: "branch" | "leaf"; // 树枝 节点
  path?: string,
  children?: MenuItem[];
}

