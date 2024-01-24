package cc.cloudcoding.feedback.controller;

import cc.cloudcoding.base.model.RestResponse;
import cc.cloudcoding.base.model.ResultCode;
import cc.cloudcoding.feedback.application.ICollectApplication;
import cc.cloudcoding.feedback.rpc.interfaces.CollectApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/29 1:53
 */

@RestController
@RequestMapping("/collect")
public class CollectController implements CollectApi {

    @Autowired
    private ICollectApplication collectApplication;

    @Override
    @PostMapping("/new/{objectId}/{userId}")
    public RestResponse collectObject(@PathVariable("objectId") String objectId, @PathVariable("userId") String userId) {
        collectApplication.addCollect(objectId, userId);
        return RestResponse.success(ResultCode.SUCCESS);
    }

    @Override
    @DeleteMapping("/delete/{objectId}/{userId}")
    public RestResponse canelCollectObject(@PathVariable("objectId") String objectId, @PathVariable("userId") String userId) {
        collectApplication.removeCollect(objectId, userId);
        return RestResponse.success(ResultCode.SUCCESS);

    }

    @Override
    @GetMapping("/isCollected/{objectId}/{userId}")
    public RestResponse isCollectdByUser(@PathVariable("objectId") String objectId, @PathVariable("userId") String userId) {
        return RestResponse.success(ResultCode.SUCCESS,collectApplication.isCollectdByUser(objectId, userId));

    }

    @Override
    @GetMapping("/countCollect/{objectId}")
    public RestResponse countCollect(@PathVariable("objectId") String objectId) {
        return RestResponse.success(ResultCode.SUCCESS,collectApplication.countCollect(objectId));

    }
}
