package cn.edu.hcnu.auth.interfaces;

import cn.edu.hcnu.auth.model.req.UserReq;
import cn.edu.hcnu.auth.model.res.UserRes;
import cn.edu.hcnu.base.model.CommonQuery;
import cn.edu.hcnu.base.model.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;

public interface UserApi {

    RestResponse<UserRes> getUserInfo(String id);

    RestResponse<UserRes> registerUser(UserReq req);


    RestResponse list(@Valid CommonQuery groupQueryReq);

    RestResponse<UserRes> update(UserReq req);
}
