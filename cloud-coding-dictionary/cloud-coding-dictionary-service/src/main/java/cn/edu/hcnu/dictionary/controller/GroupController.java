package cn.edu.hcnu.dictionary.controller;

import cn.edu.hcnu.base.model.*;
import cn.edu.hcnu.dictionary.application.service.impl.GroupApplication;
import cn.edu.hcnu.dictionary.assembler.*;
import cn.edu.hcnu.dictionary.model.command.AddGroupCommand;
import cn.edu.hcnu.dictionary.model.command.UpdateGroupCommand;
import cn.edu.hcnu.dictionary.model.dto.GroupDTO;
import cn.edu.hcnu.dictionary.model.query.GroupPageQuery;
import cn.edu.hcnu.dictionary.model.req.GroupQueryReq;
import cn.edu.hcnu.dictionary.model.req.GroupReq;
import cn.edu.hcnu.dictionary.model.res.GroupRes;
import cn.edu.hcnu.dictionary.rpc.GroupApi;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/26 12:38
 */
@RequestMapping("/group")
@RestController
public class GroupController implements GroupApi {


    @Autowired
    private GroupApplication groupApplication;

    @Autowired
    private GroupDTOToResMapping groupDTOToResMapping;

    @Autowired
    private GroupReqToUpdateGroupCommandMapping groupReqToCommandMapping;

    @Autowired
    private GroupReqToAddGroupCommandMapping groupReqToAddGroupCommandMapping;

    @Autowired
    private GroupQueryReqToGroupPageQueryMapping groupQueryReqToGroupPageQueryMapping;

    @Autowired
    private PageDTOToGroupQueryResMapping pageDTOToGroupQueryResMapping;

    @Override
    @GetMapping("query")
    @Operation(summary = "查询一页组字典数据")
    public RestResponse listAllGroup(@Valid CommonQuery commonQuery) {
        PageDTO<GroupDTO, CommonQuery> query = groupApplication.query(commonQuery);
        PageRes<GroupRes, CommonQuery> groupResGroupPageQueryPageRes = pageDTOToGroupQueryResMapping.sourceToTarget(query);
        return RestResponse.success(ResultCode.SUCCESS, groupResGroupPageQueryPageRes);
    }

    @Override
    @GetMapping("/{id}")
    public RestResponse getGroupById(@PathVariable("id") Long id) {
        GroupDTO groupDTO = groupApplication.queryById(id);
        GroupRes groupRes = groupDTOToResMapping.sourceToTarget(groupDTO);
        return RestResponse.success(ResultCode.SUCCESS, groupRes);
    }
    @Override
    @GetMapping("/query/{name}")
    public RestResponse getGroupByName(@PathVariable("name") String name) {
        GroupDTO groupDTO = groupApplication.queryByName(name);
        GroupRes groupRes = groupDTOToResMapping.sourceToTarget(groupDTO);
        return RestResponse.success(ResultCode.SUCCESS, groupRes);
    }

    @Override
    @PostMapping("/new")
    public RestResponse addGroup(@RequestBody GroupReq data) {
        AddGroupCommand addGroupCommand = groupReqToAddGroupCommandMapping.sourceToTarget(data);
        GroupDTO groupDTO = groupApplication.addGroup(addGroupCommand);
        GroupRes groupRes = groupDTOToResMapping.sourceToTarget(groupDTO);
        return RestResponse.success(ResultCode.SUCCESS, groupRes);

    }

    @Override
    @PutMapping("/{id}")
    public RestResponse updateGroup(@PathVariable("id") Long id, @RequestBody GroupReq data) {
        UpdateGroupCommand updateGroupCommand = groupReqToCommandMapping.sourceToTarget(data);
        GroupDTO groupDTO = groupApplication.updateGroup(updateGroupCommand);
        GroupRes groupRes = groupDTOToResMapping.sourceToTarget(groupDTO);
        return RestResponse.success(ResultCode.SUCCESS, groupRes);
    }

    @Override
    @DeleteMapping("/{id}")
    public RestResponse deleteGroup(@PathVariable("id") Long id) {
        Boolean aBoolean = groupApplication.deleteGroup(id);
        if (Boolean.TRUE.equals(aBoolean)) {
            return RestResponse.success(ResultCode.SUCCESS, true);
        }
        return RestResponse.success(ResultCode.NOT_FOUND_ERROR);
    }
}
