package cc.cloudcoding.feedback.rpc.interfaces;

import cc.cloudcoding.base.model.RestResponse;

public interface CollectApi {
    // 添加收藏
    RestResponse collectObject(String objectId, String userId);
    // 取消收藏
    RestResponse canelCollectObject(String objectId, String userId);
    // 是否在收藏
    RestResponse isCollectdByUser( String objectId,  String userId);

    // 这个id 有多少个收藏
    RestResponse countCollect(String objectId);
}
