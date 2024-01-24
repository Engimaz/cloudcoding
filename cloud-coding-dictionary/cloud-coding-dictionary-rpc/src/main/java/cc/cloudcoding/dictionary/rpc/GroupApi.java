package cc.cloudcoding.dictionary.rpc;

import cc.cloudcoding.base.model.CommonQuery;
import cc.cloudcoding.base.model.RestResponse;
import cc.cloudcoding.dictionary.model.req.GroupReq;

public interface GroupApi {

    RestResponse listAllGroup(CommonQuery req );

    RestResponse getGroupById(Long id);

    RestResponse getGroupByName( String name);

    RestResponse addGroup(GroupReq data );

    RestResponse updateGroup(Long id, GroupReq data);

    RestResponse deleteGroup(Long id);

}
