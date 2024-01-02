package cn.edu.hcnu.feedback.rpc.interfaces;

import cn.edu.hcnu.base.model.RestResponse;

public interface LikeApi {
    // 添加赞
    RestResponse likeObject(String objectId, String userId);
    // 取消赞
    RestResponse canellikeObject(String objectId, String userId);
    // 是否赞过
    RestResponse isLikedByUser( String objectId,  String userId);
    // 这个id 有多少个赞
    RestResponse countLike(String objectId);
}
