package cn.edu.hcnu.dictionary.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.dictionary.model.query.GroupPageQuery;
import cn.edu.hcnu.dictionary.model.req.GroupQueryReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupQueryReqToGroupPageQueryMapping extends IMapping<GroupQueryReq, GroupPageQuery> {
}
