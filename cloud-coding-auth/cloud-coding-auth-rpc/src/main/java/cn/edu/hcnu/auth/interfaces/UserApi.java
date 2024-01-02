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

    @GetMapping("query")
    @Operation(summary = "查询一页用户信息")
    RestResponse list(@Valid CommonQuery groupQueryReq);

    RestResponse<UserRes> resetPassword(UserReq req);
}
