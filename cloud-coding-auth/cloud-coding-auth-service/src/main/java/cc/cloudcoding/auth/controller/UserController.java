package cc.cloudcoding.auth.controller;

import cc.cloudcoding.auth.application.IUserApplication;
import cc.cloudcoding.auth.assembler.PageDTOToUserQueryResMapping;
import cc.cloudcoding.auth.assembler.UserDTOToUserResMapping;
import cc.cloudcoding.auth.assembler.UserReqToAddUserCommandMapping;
import cc.cloudcoding.auth.assembler.UserReqToUpdateUserCommandMapping;
import cc.cloudcoding.auth.interfaces.UserApi;
import cc.cloudcoding.auth.model.comand.AddUserCommand;
import cc.cloudcoding.auth.model.comand.UpdateUserCommand;
import cc.cloudcoding.auth.model.req.UserReq;
import cc.cloudcoding.auth.model.res.UserRes;
import cc.cloudcoding.auth.model.security.UserDTO;
import cc.cloudcoding.base.model.*;
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
    private UserReqToUpdateUserCommandMapping userReqToUpdateUserCommandMapping;
    @Autowired
    private PageDTOToUserQueryResMapping pageDTOToUserQueryResMapping;

    @GetMapping("info/{id}")
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
    public RestResponse<UserRes> update(@RequestBody @Valid UserReq req) {
        UpdateUserCommand updateUserCommand = userReqToUpdateUserCommandMapping.sourceToTarget(req);
        UserDTO userDTO = userApplication.updateUser(updateUserCommand);

        if (userDTO != null) {
            UserRes userRes = userDTOToUserResMapping.sourceToTarget(userDTO);
            return RestResponse.success(ResultCode.SUCCESS, userRes);
        } else {
            return RestResponse.fail(ResultCode.UPDATE_FAIL);
        }
    }
}
