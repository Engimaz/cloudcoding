package cc.cloudcoding.feedback.rpc.interfaces;

import cc.cloudcoding.base.model.RestResponse;

public interface DisLikeApi {
    // 添加贬
    RestResponse disLikeObject(String objectId, String userId);
    // 取消贬
    RestResponse canelDisLikeObject(String objectId, String userId);
    // 是否贬过
    RestResponse isDisLikedByUser( String objectId,  String userId);

    // 这个id 有多少个贬
    RestResponse countDisLike(String objectId);
}
