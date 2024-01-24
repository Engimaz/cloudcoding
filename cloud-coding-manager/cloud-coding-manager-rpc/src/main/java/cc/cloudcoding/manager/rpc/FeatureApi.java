package cc.cloudcoding.manager.rpc;

import cc.cloudcoding.base.model.PageParams;
import cc.cloudcoding.base.model.RestResponse;
import cc.cloudcoding.manager.model.req.FeatureReq;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 15:09
 */
public interface FeatureApi {

    RestResponse addFeature(FeatureReq req);

    RestResponse updateFeature(FeatureReq req);

    RestResponse deleteFeature(Long id);

    RestResponse queryById(Long id);

    RestResponse list(PageParams p);

    @GetMapping("all")
    RestResponse all();
}
