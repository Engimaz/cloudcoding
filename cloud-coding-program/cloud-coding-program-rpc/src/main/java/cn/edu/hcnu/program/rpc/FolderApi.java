package cn.edu.hcnu.program.rpc;

import cn.edu.hcnu.base.model.RestResponse;
import cn.edu.hcnu.program.model.req.FolderReq;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface FolderApi {

    RestResponse addFolder(FolderReq folder);

    RestResponse removeFolder(String id);

    RestResponse queryFolder(String id);

    @GetMapping("top/{id}")
    RestResponse queryTopFolder(@PathVariable String id);

    RestResponse updateFolder(FolderReq folderReq);
}
