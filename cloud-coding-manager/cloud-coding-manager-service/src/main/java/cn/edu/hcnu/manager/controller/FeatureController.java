package cn.edu.hcnu.manager.controller;

import cn.edu.hcnu.base.assembler.PageParamsToCommandQueryMapping;
import cn.edu.hcnu.base.model.*;
import cn.edu.hcnu.manager.application.service.IFeatureApplication;
import cn.edu.hcnu.manager.assembler.AddFeatureReqToAddFeatureCommandMapping;
import cn.edu.hcnu.manager.assembler.FeatureDTOToResMapping;
import cn.edu.hcnu.base.assembler.PageDTOToPageResMapping;
import cn.edu.hcnu.manager.assembler.UpdateFeatureReqToUpdateFeatureCommandMapping;
import cn.edu.hcnu.manager.model.command.AddFeatureCommand;
import cn.edu.hcnu.manager.model.command.UpdateFeatureCommand;
import cn.edu.hcnu.manager.model.dto.FeatureDTO;
import cn.edu.hcnu.manager.model.req.FeatureReq;
import cn.edu.hcnu.manager.model.res.UrlRes;
import cn.edu.hcnu.manager.rpc.FeatureApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 15:08
 */

@RestController
@RequestMapping("/feature")
public class FeatureController implements FeatureApi {


    @Autowired
    private IFeatureApplication featureApplication;

    @Autowired
    private AddFeatureReqToAddFeatureCommandMapping addFeatureReqToAddFeatureCommandMapping;
    @Autowired
    private UpdateFeatureReqToUpdateFeatureCommandMapping updateFeatureReqToUpdateFeatureCommandMapping;
    @Autowired
    private FeatureDTOToResMapping featureDTOToResMapping;

    @Override
    @PostMapping("/new")
    public RestResponse addFeature(@RequestBody FeatureReq req) {
        AddFeatureCommand addFeatureCommand = addFeatureReqToAddFeatureCommandMapping.sourceToTarget(req);
        FeatureDTO featureDTO = featureApplication.addFeature(addFeatureCommand);
        if (featureDTO == null) {
            return RestResponse.fail(ResultCode.ADD_FEATURE_ERROR);
        }
        return RestResponse.success(ResultCode.SUCCESS, featureDTOToResMapping.sourceToTarget(featureDTO));
    }

    @Override
    @PutMapping("update")
    public RestResponse updateFeature(@RequestBody FeatureReq req) {
        UpdateFeatureCommand updateFeatureCommand = updateFeatureReqToUpdateFeatureCommandMapping.sourceToTarget(req);
        FeatureDTO featureDTO = featureApplication.updateFeature(updateFeatureCommand);
        if (featureDTO == null) {
            return RestResponse.fail(ResultCode.UPDATE_FEATURE_ERROR);
        }
        return RestResponse.success(ResultCode.SUCCESS, featureDTOToResMapping.sourceToTarget(featureDTO));
    }

    @Override
    @DeleteMapping("{id}")
    public RestResponse deleteFeature(@PathVariable("id") @Min(1) Long id) {
        Boolean aBoolean = featureApplication.deleteById(id);
        if (!aBoolean) {
            return RestResponse.fail(ResultCode.DELETE_FEATURE_ERROR);
        }
        return RestResponse.success(ResultCode.SUCCESS);
    }

    @Override
    @GetMapping("{id}")
    public RestResponse queryById(@PathVariable("id") @Min(1) Long id) {
        FeatureDTO featureDTO = featureApplication.queryById(id);
        if (featureDTO == null) {
            return RestResponse.fail(ResultCode.QUERY_FEATURE_ERROR);
        }
        return RestResponse.success(ResultCode.SUCCESS, featureDTOToResMapping.sourceToTarget(featureDTO));
    }

    @Autowired
    private PageParamsToCommandQueryMapping pageParamsToCommandQueryMapping;
    @Autowired
    private PageDTOToPageResMapping pageDTOToPageVOMapping;

    @Override
    @GetMapping("list")
    public RestResponse list(PageParams req) {
        CommonQuery commonQuery = pageParamsToCommandQueryMapping.sourceToTarget(req);
        PageDTO<FeatureDTO, CommonQuery> list = featureApplication.list(commonQuery);
        if (null == list) {
            return RestResponse.fail(ResultCode.QUERY_ERROR);
        }
        PageRes<UrlRes, CommonQuery> urlVO = pageDTOToPageVOMapping.sourceToTarget(list, featureDTOToResMapping);
        return RestResponse.success(ResultCode.SUCCESS, urlVO);
    }
    @Override
    @GetMapping("all")
    public RestResponse all() {
        PageDTO<FeatureDTO, CommonQuery> list = featureApplication.all();
        if (null == list) {
            return RestResponse.fail(ResultCode.QUERY_ERROR);
        }
        PageRes<UrlRes, CommonQuery> urlVO = pageDTOToPageVOMapping.sourceToTarget(list, featureDTOToResMapping);
        return RestResponse.success(ResultCode.SUCCESS, urlVO);
    }
}
