package cn.edu.hcnu.manager.controller;

import cn.edu.hcnu.base.assembler.PageDTOToPageResMapping;
import cn.edu.hcnu.base.assembler.PageParamsToCommandQueryMapping;
import cn.edu.hcnu.base.model.*;
import cn.edu.hcnu.manager.application.service.IOrganizationApplication;
import cn.edu.hcnu.manager.application.service.IOrganizationRecordApplication;
import cn.edu.hcnu.manager.assembler.*;
import cn.edu.hcnu.manager.model.aggr.OrganizationRecordAggregate;
import cn.edu.hcnu.manager.model.command.AddOrganizationCommand;
import cn.edu.hcnu.manager.model.command.AddOrganizationRecordCommand;
import cn.edu.hcnu.manager.model.command.UpdateOrganizationCommand;
import cn.edu.hcnu.manager.model.command.UpdateOrganizationRecordCommand;
import cn.edu.hcnu.manager.model.dto.OrganizationDTO;
import cn.edu.hcnu.manager.model.dto.OrganizationRecordDTO;
import cn.edu.hcnu.manager.model.req.OrganizationRecordReq;
import cn.edu.hcnu.manager.model.req.OrganizationReq;
import cn.edu.hcnu.manager.model.res.OrganizationRecordRes;
import cn.edu.hcnu.manager.model.res.OrganizationRes;
import cn.edu.hcnu.manager.rpc.OrganizationApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/13 16:32
 */

@RestController
@RequestMapping("/organization")
public class OrganizationController implements OrganizationApi {


    @Autowired
    private AddOrganizationReqToAddOrganizationCommandMapping addOrganizationReqToAddOrganizationCommandMapping;

    @Autowired
    private IOrganizationApplication organizationApplication;

    @Autowired
    private OrganizationDTOToOrganizationResMapping organizationDTOToOrganizationResMapping;

    @Autowired
    private PageParamsToCommandQueryMapping pageParamsToCommandQueryMapping;

    @Autowired
    private OrganizationReqToUpdateOrganizationCommandMapping organizationReqToUpdateOrganizationCommandMapping;

    @Override
    @PostMapping("/new")
    public RestResponse addOrganization(@RequestBody OrganizationReq req) {
        AddOrganizationCommand addOrganizationCommand = addOrganizationReqToAddOrganizationCommandMapping.sourceToTarget(req);
        OrganizationDTO organizationDTO = organizationApplication.addOrganization(addOrganizationCommand);
        if (null == organizationDTO) {
            RestResponse.fail(ResultCode.ADD_URL_ERROR);
        }
        OrganizationRes organizationRes = organizationDTOToOrganizationResMapping.sourceToTarget(organizationDTO);
        return RestResponse.success(ResultCode.SUCCESS, organizationRes);
    }

    @Override
    @PutMapping("update")
    public RestResponse updateOrganization(@RequestBody OrganizationReq req) {
        UpdateOrganizationCommand updateOrganizationCommand = organizationReqToUpdateOrganizationCommandMapping.sourceToTarget(req);
        OrganizationDTO organizationDTO = organizationApplication.updateOrganization(updateOrganizationCommand);
        if (null == organizationDTO) {
            RestResponse.fail(ResultCode.UPDATE_ORGANIZATION_ERROR);
        }
        OrganizationRes organizationRes = organizationDTOToOrganizationResMapping.sourceToTarget(organizationDTO);
        return RestResponse.success(ResultCode.SUCCESS, organizationRes);
    }

    @Override
    @DeleteMapping("{id}")
    public RestResponse deleteOrganization(@PathVariable("id") Long id) {
        Boolean aBoolean = organizationApplication.deleteById(id);
        if (aBoolean == true) {
            return RestResponse.success(ResultCode.SUCCESS);
        }
        return RestResponse.fail(ResultCode.DELETE_ORGANIZATION_ERROR);

    }

    @Override
    @GetMapping("{id}")
    public RestResponse queryById(@PathVariable("id") Long id) {
        OrganizationDTO organizationDTO = organizationApplication.queryById(id);
        if (null != organizationDTO) {
            return RestResponse.success(ResultCode.SUCCESS, organizationDTOToOrganizationResMapping.sourceToTarget(organizationDTO));
        }
        return RestResponse.fail(ResultCode.QUERY_ERROR);
    }


    @Autowired
    private PageDTOToPageResMapping pageDTOToPageResMapping;

    @Override
    @GetMapping("list/{status}")
    public RestResponse list(PageParams req, @PathVariable("status") String status) {
        CommonQuery commonQuery = pageParamsToCommandQueryMapping.sourceToTarget(req);
        PageDTO<OrganizationDTO, CommonQuery> list = organizationApplication.list(commonQuery, status);
        if (null == list) {
            return RestResponse.fail(ResultCode.QUERY_ERROR);
        }
        PageRes pageRes = pageDTOToPageResMapping.sourceToTarget(list, organizationDTOToOrganizationResMapping);

        return RestResponse.success(ResultCode.SUCCESS, pageRes);
    }

    @GetMapping("list")
    public RestResponse list1(PageParams req) {
        CommonQuery commonQuery = pageParamsToCommandQueryMapping.sourceToTarget(req);
        PageDTO<OrganizationDTO, CommonQuery> list = organizationApplication.list(commonQuery, null);
        if (null == list) {
            return RestResponse.fail(ResultCode.QUERY_ERROR);
        }
        PageRes pageRes = pageDTOToPageResMapping.sourceToTarget(list, organizationDTOToOrganizationResMapping);

        return RestResponse.success(ResultCode.SUCCESS, pageRes);
    }

    @Override
    @GetMapping("/own/{id}")
    public RestResponse quertMyOrganization(@PathVariable("id") String id) {
        List<OrganizationDTO> organizationDTO = organizationApplication.queryByUserId(Long.parseLong(id));
        if (null != organizationDTO) {
            return RestResponse.success(ResultCode.SUCCESS, organizationDTOToOrganizationResMapping.sourceToTarget(organizationDTO));
        }
        return RestResponse.fail(ResultCode.QUERY_ERROR);
    }

    @Autowired
    private IOrganizationRecordApplication organizationRecordApplication;

    @Autowired
    private OrganizationRecordReqToAddOrganizationRecordCommandMapping organizationRecordReqToAddOrganizationRecordCommandMapping;

    @Autowired
    private OrganizationRecordReqToUpdateOrganizationRecordCommandMapping organizationRecordReqToUpdateOrganizationRecordCommandMapping;
    @Autowired
    private OrganizationRecordDTOToOrganizationRecordResMapping organizationRecordDTOToOrganizationRecordResMapping;

    @PostMapping("/record/new")
    public RestResponse createRecord(@RequestBody OrganizationRecordReq record) {
        AddOrganizationRecordCommand addOrganizationCommand = organizationRecordReqToAddOrganizationRecordCommandMapping.sourceToTarget(record);
        OrganizationRecordDTO dto = organizationRecordApplication.save(addOrganizationCommand);
        if (dto != null) {
            return RestResponse.success(ResultCode.SUCCESS, organizationRecordDTOToOrganizationRecordResMapping.sourceToTarget(dto));
        }
        return RestResponse.success(ResultCode.SAVE_ERROR);

    }

    @PutMapping("/record/update")
    public RestResponse updateRecord(@RequestBody OrganizationRecordReq record) {
        UpdateOrganizationRecordCommand updateOrganizationRecordCommand = organizationRecordReqToUpdateOrganizationRecordCommandMapping.sourceToTarget(record);
        OrganizationRecordDTO dto = organizationRecordApplication.update(updateOrganizationRecordCommand);
        if (dto != null) {
            return RestResponse.success(ResultCode.SUCCESS, organizationRecordDTOToOrganizationRecordResMapping.sourceToTarget(dto));
        }
        return RestResponse.success(ResultCode.UPDATE_ERROR);
    }

    @DeleteMapping("/record/{id}")
    public RestResponse deleteRecord(@PathVariable Long id) {
        boolean b = organizationRecordApplication.deleteById(id);
        if (b) {
            return RestResponse.success(ResultCode.SUCCESS);
        }
        return RestResponse.fail(ResultCode.DELETE_ERROR);
    }

    @GetMapping("/record/{id}")
    public RestResponse getRecord(@PathVariable Long id) {
        OrganizationRecordDTO dto = organizationRecordApplication.getById(id);
        if (dto != null) {
            return RestResponse.success(ResultCode.SUCCESS, organizationRecordDTOToOrganizationRecordResMapping.sourceToTarget(dto));
        }
        return RestResponse.success(ResultCode.QUERY_ERROR);
    }


    @GetMapping("/record/list")
    public RestResponse listUrl(PageParams req) {
        CommonQuery commonQuery = pageParamsToCommandQueryMapping.sourceToTarget(req);
        PageDTO<OrganizationRecordDTO, CommonQuery> list = organizationRecordApplication.list(commonQuery);
        if (null == list) {
            return RestResponse.fail(ResultCode.QUERY_ERROR);
        }
        PageRes<OrganizationRecordRes, CommonQuery> urlVO = pageDTOToPageResMapping.sourceToTarget(list, organizationDTOToOrganizationResMapping);
        return RestResponse.success(ResultCode.SUCCESS, urlVO);
    }

    @GetMapping("/record/list/user/{userid}")
    public RestResponse listByUserId(@PathVariable("userid") Long userid) {
        List<OrganizationRecordDTO> list = organizationRecordApplication.list(userid);
        if (null == list) {
            return RestResponse.fail(ResultCode.QUERY_ERROR);
        }
        return RestResponse.success(ResultCode.SUCCESS, organizationRecordDTOToOrganizationRecordResMapping.sourceToTarget(list));
    }

    @GetMapping("/record/list/organization/{organizationId}")
    public RestResponse listByOrganization(@PathVariable("organizationId") Long organizationId) {
        OrganizationRecordAggregate aggregate = organizationRecordApplication.listByOrganizationId(organizationId);
        if (null == aggregate) {
            return RestResponse.fail(ResultCode.QUERY_ERROR);
        }
        return RestResponse.success(ResultCode.SUCCESS, aggregate);
    }


}
