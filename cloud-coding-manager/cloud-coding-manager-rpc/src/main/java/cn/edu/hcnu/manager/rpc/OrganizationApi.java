package cn.edu.hcnu.manager.rpc;

import cn.edu.hcnu.base.model.PageParams;
import cn.edu.hcnu.base.model.RestResponse;
import cn.edu.hcnu.manager.model.req.OrganizationReq;

public interface OrganizationApi {
    RestResponse addOrganization(OrganizationReq req);

    RestResponse updateOrganization(OrganizationReq req);

    RestResponse deleteOrganization(Long id);

    RestResponse queryById(Long id);

    RestResponse list(PageParams req,String status);


    RestResponse quertMyOrganization(String id);
}
