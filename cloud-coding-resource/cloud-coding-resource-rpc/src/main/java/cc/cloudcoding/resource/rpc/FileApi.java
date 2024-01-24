package cc.cloudcoding.resource.rpc;

import cc.cloudcoding.base.model.PageParams;
import cc.cloudcoding.base.model.RestResponse;
import cc.cloudcoding.resource.model.req.MergeResourceParamsReq;
import cc.cloudcoding.resource.model.req.QueryResourceParamsReq;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface FileApi {

    RestResponse list(PageParams pageParams, String userId, QueryResourceParamsReq queryMediaParamsDto);


    RestResponse upload(MultipartFile upload, String userId, String md5, String chunkMd5) throws IOException;

    RestResponse<Boolean> checkFile(String fileMd5);


    RestResponse mergeChunks(MergeResourceParamsReq req) throws UnsupportedEncodingException;
}
