package cn.edu.hcnu.manager.rpc;

import cn.edu.hcnu.base.model.PageParams;
import cn.edu.hcnu.base.model.RestResponse;
import cn.edu.hcnu.manager.model.req.AddUrlReq;
import cn.edu.hcnu.manager.model.req.UpdateUrlReq;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/31 23:34
 */
public interface UrlApi {

    RestResponse listUrl(PageParams pageQueryUrlReq);

    RestResponse addUrl(AddUrlReq req);

    RestResponse updateUrl(UpdateUrlReq req);

    RestResponse deleteUrl(Long id);

    RestResponse queryById(Long id);
}
