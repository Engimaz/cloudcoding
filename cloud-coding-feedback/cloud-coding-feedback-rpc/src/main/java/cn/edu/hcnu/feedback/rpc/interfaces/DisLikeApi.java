package cn.edu.hcnu.feedback.rpc.interfaces;

import cn.edu.hcnu.base.model.RestResponse;

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
