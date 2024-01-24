package cc.cloudcoding.feedback.controller;

import cc.cloudcoding.base.model.RestResponse;
import cc.cloudcoding.base.model.ResultCode;
import cc.cloudcoding.feedback.application.ILikeApplication;
import cc.cloudcoding.feedback.rpc.interfaces.LikeApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/29 1:53
 */

@RestController
@RequestMapping("/like")
public class LikeController implements LikeApi {

    @Autowired
    private ILikeApplication likeApplication;

    @PostMapping("/new/{objectId}/{userId}")
    public RestResponse likeObject(@PathVariable("objectId") String objectId, @PathVariable("userId") String userId) {
        likeApplication.addLike(objectId, userId);
        return RestResponse.success(ResultCode.SUCCESS);
    }

    @DeleteMapping("/delete/{objectId}/{userId}")
    @Override
    public RestResponse canellikeObject(@PathVariable("objectId") String objectId, @PathVariable("userId") String userId) {
        likeApplication.removeLike(objectId, userId);
        return RestResponse.success(ResultCode.SUCCESS);

    }


    @GetMapping("/isLiked/{objectId}/{userId}")
    public RestResponse isLikedByUser(@PathVariable("objectId") String objectId, @PathVariable("userId") String userId) {
        return RestResponse.success(ResultCode.SUCCESS, likeApplication.isLikedByUser(objectId, userId));

    }

    @Override
    @GetMapping("/countLike/{objectId}")
    public RestResponse countLike(@PathVariable("objectId") String objectId) {
        return RestResponse.success(ResultCode.SUCCESS,likeApplication.countLike(objectId));
    }
}
