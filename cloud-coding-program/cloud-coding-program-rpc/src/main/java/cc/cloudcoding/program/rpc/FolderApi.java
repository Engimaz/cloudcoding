package cc.cloudcoding.program.rpc;

import cc.cloudcoding.base.model.RestResponse;
import cc.cloudcoding.program.model.req.FolderReq;
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
