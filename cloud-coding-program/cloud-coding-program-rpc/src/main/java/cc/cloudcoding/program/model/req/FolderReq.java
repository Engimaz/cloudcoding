package cc.cloudcoding.program.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String projectId;
    private String id;
    private List<FolderReq> folders;
    private List<FileReq> files;
}
