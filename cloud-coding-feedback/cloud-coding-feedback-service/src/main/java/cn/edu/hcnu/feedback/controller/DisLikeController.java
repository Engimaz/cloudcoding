package cn.edu.hcnu.feedback.controller;

import cn.edu.hcnu.base.model.RestResponse;
import cn.edu.hcnu.base.model.ResultCode;
import cn.edu.hcnu.feedback.application.IDisLikeApplication;
import cn.edu.hcnu.feedback.rpc.interfaces.DisLikeApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/29 1:53
 */

@RestController
@RequestMapping("/dislike")
public class DisLikeController implements DisLikeApi {

    @Autowired
    private IDisLikeApplication disLikeApplication;

    @Override
    @PostMapping("/new/{objectId}/{userId}")
    public RestResponse disLikeObject(@PathVariable("objectId") String objectId, @PathVariable("userId") String userId) {
        disLikeApplication.addDisLike(objectId, userId);
        return RestResponse.success(ResultCode.SUCCESS);
    }

    @Override
    @DeleteMapping("/delete/{objectId}/{userId}")
    public RestResponse canelDisLikeObject(@PathVariable("objectId") String objectId, @PathVariable("userId") String userId) {
        disLikeApplication.removeDisLike(objectId, userId);
        return RestResponse.success(ResultCode.SUCCESS);

    }

    @Override
    @GetMapping("/isDisLiked/{objectId}/{userId}")
    public RestResponse isDisLikedByUser(@PathVariable("objectId") String objectId, @PathVariable("userId") String userId) {
        return RestResponse.success(ResultCode.SUCCESS, disLikeApplication.isDisLikedByUser(objectId, userId));
    }

    @Override
    @GetMapping("/countDisLike/{objectId}")
    public RestResponse countDisLike(@PathVariable("objectId") String objectId) {
        return RestResponse.success(ResultCode.SUCCESS,disLikeApplication.countDisLike(objectId));
    }
}
