package cc.cloudcoding.program.rpc;

import cc.cloudcoding.base.model.RestResponse;
import cc.cloudcoding.program.model.req.FileReq;

public interface FileApi {
    RestResponse addFile(FileReq fileReq);
    RestResponse removeFile(String id);
    RestResponse queryFile(String id);
    RestResponse updateFile(FileReq fileReq);
}
