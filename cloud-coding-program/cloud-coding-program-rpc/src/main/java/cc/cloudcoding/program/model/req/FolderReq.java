package cc.cloudcoding.program.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/12 0:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolderReq {
    private String name;
    private String parentId;
    private String programId;
    private String id;
}
