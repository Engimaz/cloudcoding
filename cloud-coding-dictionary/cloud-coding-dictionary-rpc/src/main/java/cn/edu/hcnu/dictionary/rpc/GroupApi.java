package cn.edu.hcnu.dictionary.rpc;

import cn.edu.hcnu.base.model.CommonQuery;
import cn.edu.hcnu.base.model.RestResponse;
import cn.edu.hcnu.dictionary.model.req.GroupQueryReq;
import cn.edu.hcnu.dictionary.model.req.GroupReq;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface GroupApi {

    RestResponse listAllGroup(CommonQuery req );

    RestResponse getGroupById(Long id);

    RestResponse getGroupByName( String name);

    RestResponse addGroup(GroupReq data );

    RestResponse updateGroup(Long id, GroupReq data);

    RestResponse deleteGroup(Long id);

}
