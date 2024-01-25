import { PanelResizeHandle } from "react-resizable-panels";

import styles from "./resize.module.css";

export default function ResizeHandle({
  className = "border-2 border-teal-100",
  id
}: {
  className?: string;
  id?: string;
}) {
  return (
    <PanelResizeHandle
      className={[styles.ResizeHandleOuter, className].join(" ")}
      id={id}
    >
      <div className={styles.ResizeHandleInner}>

      </div>
    </PanelResizeHandle>
  );
}
