package cn.edu.hcnu.auth.controller;

import cn.edu.hcnu.auth.application.IUserApplication;
import cn.edu.hcnu.auth.assembler.PageDTOToUserQueryResMapping;
import cn.edu.hcnu.auth.assembler.UserDTOToUserResMapping;
import cn.edu.hcnu.auth.assembler.UserReqToAddUserCommandMapping;
import cn.edu.hcnu.auth.assembler.UserReqToResetPasswordCommandMapping;
import cn.edu.hcnu.auth.interfaces.UserApi;
import cn.edu.hcnu.auth.model.comand.AddUserCommand;
import cn.edu.hcnu.auth.model.comand.ResetPasswordCommand;
import cn.edu.hcnu.auth.model.req.UserReq;
import cn.edu.hcnu.auth.model.res.UserRes;
import cn.edu.hcnu.auth.model.security.UserDTO;
import cn.edu.hcnu.base.model.*;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;

@RestController
@RequestMapping("user")
@DubboService(group = "user")
public class UserController implements UserApi {
    @Autowired
    private IUserApplication userApplication;

    @Autowired
    private UserDTOToUserResMapping userDTOToUserResMapping;

    @Autowired
    private UserReqToAddUserCommandMapping userReqToAddUserCommandMapping;

    @Autowired
    private UserReqToResetPasswordCommandMapping userReqToResetPasswordCommandMapping;
    @Autowired
    private PageDTOToUserQueryResMapping pageDTOToUserQueryResMapping;
    @GetMapping("{id}")
    @PermitAll
    @Override
    public RestResponse<UserRes> getUserInfo(@PathVariable("id") String id) {
        UserDTO userById = userApplication.getUserById(id);
        UserRes userRes = userDTOToUserResMapping.sourceToTarget(userById);
        return RestResponse.success(ResultCode.SUCCESS, userRes);
    }

    @Override
    @PostMapping("new")
    public RestResponse<UserRes> registerUser(@RequestBody @Valid UserReq req) {

        AddUserCommand addUserCommand = userReqToAddUserCommandMapping.sourceToTarget(req);

        UserDTO userDTO = userApplication.addUser(addUserCommand);

        if (userDTO != null) {
            UserRes userRes = userDTOToUserResMapping.sourceToTarget(userDTO);
            return RestResponse.success(ResultCode.SUCCESS, userRes);
        } else {
            return RestResponse.fail(ResultCode.CHECK_FAIL);
        }


    }

    @Override
    @GetMapping("list")
    @Operation(summary = "查询一页用户信息")
    public RestResponse list(@Valid CommonQuery commonQuery) {
        PageDTO<UserDTO, CommonQuery> query = userApplication.query(commonQuery);
        PageRes<UserRes, CommonQuery> groupResGroupPageQueryPageRes = pageDTOToUserQueryResMapping.sourceToTarget(query);
        return RestResponse.success(ResultCode.SUCCESS, groupResGroupPageQueryPageRes);
    }

    @Override
    @PutMapping("update")
    public RestResponse<UserRes> resetPassword(@RequestBody @Valid UserReq req) {
        ResetPasswordCommand resetPasswordCommand = userReqToResetPasswordCommandMapping.sourceToTarget(req);
        UserDTO userDTO = userApplication.resetPassword(resetPasswordCommand);

        if (userDTO != null) {
            UserRes userRes = userDTOToUserResMapping.sourceToTarget(userDTO);
            return RestResponse.success(ResultCode.SUCCESS, userRes);
        } else {
            return RestResponse.fail(ResultCode.UPDATE_FAIL);
        }
    }
}
