package cc.cloudcoding.manager.rpc;

import cc.cloudcoding.base.model.PageParams;
import cc.cloudcoding.base.model.RestResponse;
import cc.cloudcoding.manager.model.req.OrganizationReq;

public interface OrganizationApi {
    RestResponse addOrganization(OrganizationReq req);

    RestResponse updateOrganization(OrganizationReq req);

    RestResponse deleteOrganization(Long id);

    RestResponse queryById(Long id);

    RestResponse list(PageParams req,String status);


    RestResponse quertMyOrganization(String id);
}
