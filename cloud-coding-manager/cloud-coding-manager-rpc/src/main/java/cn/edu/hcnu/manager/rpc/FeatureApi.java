package cn.edu.hcnu.manager.rpc;

import cn.edu.hcnu.base.model.PageParams;
import cn.edu.hcnu.base.model.RestResponse;
import cn.edu.hcnu.manager.model.req.FeatureReq;
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
