package cn.edu.hcnu.manager.controller;


import cn.edu.hcnu.base.assembler.PageParamsToCommandQueryMapping;
import cn.edu.hcnu.base.model.*;
import cn.edu.hcnu.manager.application.service.IUrlApplication;
import cn.edu.hcnu.manager.assembler.AddUrlReqToAddUrlCommandMapping;
import cn.edu.hcnu.base.assembler.PageDTOToPageResMapping;
import cn.edu.hcnu.manager.assembler.UpdateUrlReqToUpdateUrlCommandMapping;
import cn.edu.hcnu.manager.assembler.UrlDTOToUrlResMapping;
import cn.edu.hcnu.manager.model.command.AddUrlCommand;
import cn.edu.hcnu.manager.model.command.UpdateUrlCommand;
import cn.edu.hcnu.manager.model.dto.UrlDTO;
import cn.edu.hcnu.manager.model.req.AddUrlReq;
import cn.edu.hcnu.manager.model.req.UpdateUrlReq;
import cn.edu.hcnu.manager.model.res.UrlRes;
import cn.edu.hcnu.manager.rpc.UrlApi;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/2 23:38
 */

@RestController
@RequestMapping("/url")

public class UrlController implements UrlApi {

    private final IUrlApplication urlApplication;

    private final AddUrlReqToAddUrlCommandMapping addUrlReqToAddUrlCommandMapping;

    private final UrlDTOToUrlResMapping urlDTOToUrlResMapping;

    private final PageParamsToCommandQueryMapping pageParamsToCommandQueryMapping;

    private final PageDTOToPageResMapping pageDTOToPageVOMapping;

    private final UpdateUrlReqToUpdateUrlCommandMapping updateUrlReqToUpdateUrlCommandMapping;

    public UrlController(IUrlApplication urlApplication, AddUrlReqToAddUrlCommandMapping addUrlReqToAddUrlCommandMapping, UrlDTOToUrlResMapping urlDTOToUrlResMapping, PageParamsToCommandQueryMapping pageParamsToCommandQueryMapping, PageDTOToPageResMapping pageDTOToPageVOMapping, UpdateUrlReqToUpdateUrlCommandMapping updateUrlReqToUpdateUrlCommandMapping) {
        this.urlApplication = urlApplication;
        this.addUrlReqToAddUrlCommandMapping = addUrlReqToAddUrlCommandMapping;
        this.urlDTOToUrlResMapping = urlDTOToUrlResMapping;
        this.pageParamsToCommandQueryMapping = pageParamsToCommandQueryMapping;
        this.pageDTOToPageVOMapping = pageDTOToPageVOMapping;
        this.updateUrlReqToUpdateUrlCommandMapping = updateUrlReqToUpdateUrlCommandMapping;
    }


    /**
     * @param req 查询请求数据
     * @description: 分页查询接口
     * @return: cn.edu.hcnu.base.model.RestResponse
     * @author: Administrator
     * @time: 2023/9/25 12:24
     */
    @Override
    @GetMapping("list")
    public RestResponse listUrl(PageParams req) {
        CommonQuery commonQuery = pageParamsToCommandQueryMapping.sourceToTarget(req);
        PageDTO<UrlDTO, CommonQuery> list = urlApplication.listUrl(commonQuery);
        if (null == list) {
            return RestResponse.fail(ResultCode.QUERY_ERROR);
        }
        PageRes<UrlRes, CommonQuery> urlVO = pageDTOToPageVOMapping.sourceToTarget(list, urlDTOToUrlResMapping);
        return RestResponse.success(ResultCode.SUCCESS, urlVO);
    }

    @Override
    @GetMapping("all")
    public RestResponse all() {
        PageDTO<UrlDTO, CommonQuery> list = urlApplication.all();
        if (null == list) {
            return RestResponse.fail(ResultCode.QUERY_ERROR);
        }
        PageRes<UrlRes, CommonQuery> urlVO = pageDTOToPageVOMapping.sourceToTarget(list, urlDTOToUrlResMapping);
        return RestResponse.success(ResultCode.SUCCESS, urlVO);
    }

    @Override
    @PostMapping("new")
    public RestResponse<UrlRes> addUrl(@RequestBody AddUrlReq req) {
        AddUrlCommand addUrlCommand = addUrlReqToAddUrlCommandMapping.sourceToTarget(req);
        UrlDTO urlDTO = urlApplication.addUrl(addUrlCommand);
        if (null == urlDTO) {
            RestResponse.fail(ResultCode.ADD_URL_ERROR);
        }
        UrlRes urlVO = urlDTOToUrlResMapping.sourceToTarget(urlDTO);
        return RestResponse.success(ResultCode.SUCCESS, urlVO);
    }

    @Override
    @PutMapping("update")
    public RestResponse updateUrl(@RequestBody UpdateUrlReq req) {
        UpdateUrlCommand updateUrlCommand = updateUrlReqToUpdateUrlCommandMapping.sourceToTarget(req);
        UrlDTO urlDTO = urlApplication.updateUrl(updateUrlCommand);
        if (null == urlDTO) {
            RestResponse.fail(ResultCode.UPDATE_URL_ERROR);
        }
        UrlRes urlVO = urlDTOToUrlResMapping.sourceToTarget(urlDTO);
        return RestResponse.success(ResultCode.SUCCESS, urlVO);

    }

    @Override
    @DeleteMapping("{id}")
    public RestResponse deleteUrl(@PathVariable("id") Long id) {
        Boolean aBoolean = urlApplication.deleteById(id);
        if (!aBoolean) {
            return RestResponse.fail(Boolean.FALSE, ResultCode.DELETE_URL_ERROR);
        }
        return RestResponse.success(ResultCode.SUCCESS, Boolean.TRUE);
    }

    @Override
    @GetMapping("{id}")
    public RestResponse queryById(@PathVariable("id") Long id) {
        return null;
    }
}
