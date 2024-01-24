package cc.cloudcoding.manager.rpc;

import cc.cloudcoding.base.model.PageParams;
import cc.cloudcoding.base.model.RestResponse;
import cc.cloudcoding.manager.model.req.AddUrlReq;
import cc.cloudcoding.manager.model.req.UpdateUrlReq;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/31 23:34
 */
public interface UrlApi {

    RestResponse listUrl(PageParams pageQueryUrlReq);

    @GetMapping("all")
    RestResponse all();

    RestResponse addUrl(AddUrlReq req);

    RestResponse updateUrl(UpdateUrlReq req);

    RestResponse deleteUrl(Long id);

    RestResponse queryById(Long id);
}
