package cc.cloudcoding.dictionary.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.dictionary.model.query.GroupPageQuery;
import cc.cloudcoding.dictionary.model.req.GroupQueryReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupQueryReqToGroupPageQueryMapping extends IMapping<GroupQueryReq, GroupPageQuery> {
}
