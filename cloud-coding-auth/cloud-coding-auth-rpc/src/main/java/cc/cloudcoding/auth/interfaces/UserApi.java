package cc.cloudcoding.auth.interfaces;

import cc.cloudcoding.auth.model.req.UserReq;
import cc.cloudcoding.auth.model.res.UserRes;
import cc.cloudcoding.base.model.CommonQuery;
import cc.cloudcoding.base.model.RestResponse;

import javax.validation.Valid;

public interface UserApi {

    RestResponse<UserRes> getUserInfo(String id);

    RestResponse<UserRes> registerUser(UserReq req);


    RestResponse list(@Valid CommonQuery groupQueryReq);

    RestResponse<UserRes> update(UserReq req);
}
