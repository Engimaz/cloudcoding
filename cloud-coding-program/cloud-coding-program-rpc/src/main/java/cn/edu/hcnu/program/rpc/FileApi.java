package cn.edu.hcnu.program.rpc;

import cn.edu.hcnu.base.model.RestResponse;
import cn.edu.hcnu.program.model.req.FileReq;

public interface FileApi {
    RestResponse addFile(FileReq fileReq);
    RestResponse removeFile(String id);
    RestResponse queryFile(String id);
    RestResponse updateFile(FileReq fileReq);
}
